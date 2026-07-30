package ec.edu.unl.refugio.business.service;

import ec.edu.unl.refugio.business.core.AdoptionFileDAO;
import ec.edu.unl.refugio.business.core.AnimalDAO;
import ec.edu.unl.refugio.business.core.MedicalRecordDAO;
import ec.edu.unl.refugio.business.people.AdopterDAO;
import ec.edu.unl.refugio.business.security.AuthService;
import ec.edu.unl.refugio.exception.AnimalNotAvailableException;
import ec.edu.unl.refugio.exception.AnimalNotFoundException;
import ec.edu.unl.refugio.exception.InvalidCredentialsException;
import ec.edu.unl.refugio.model.Adopter;
import ec.edu.unl.refugio.model.AdoptionFile;
import ec.edu.unl.refugio.model.AdoptionStatus;
import ec.edu.unl.refugio.model.Animal;
import ec.edu.unl.refugio.model.Employee;
import ec.edu.unl.refugio.model.MedicalRecord;
import ec.edu.unl.refugio.model.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless // Usamos @Stateless para un EJB que es ideal como Facade transaccional.
public class ShelterService {

    @Inject
    private AnimalDAO animalDAO; // Inyectamos nuestro nuevo DAO

    @Inject
    private AdopterDAO adopterDAO; // Inyectamos el DAO para adoptantes

    @Inject
    private MedicalRecordDAO medicalRecordDAO;

    @Inject
    private AdoptionFileDAO adoptionFileDAO;

    @Inject
    private AuthService authService;

    /**
     * Constructor sin argumentos requerido por CDI/Weld.
     */
    public ShelterService() {
    }

    // -------------------------------------------------------------------------
    // GESTIÓN DE ANIMALES
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo animal guardándolo en la base de datos de Docker.
     */
    public void registerIntake(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("El animal no puede ser nulo.");
        }
        animalDAO.create(animal);
    }

    /**
     * Busca un animal por su ID en la base de datos.
     * @throws AnimalNotFoundException si el ID no existe.
     */
    public Animal findAnimalById(String animalId) throws AnimalNotFoundException {
        // La lógica de búsqueda ahora está en el DAO.
        // Usamos Optional para un manejo más limpio de resultados no encontrados.
        return animalDAO.findByAnimalId(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
    }

    /**
     * Filtra el inventario consultando la base de datos.
     */
    public List<Animal> searchByFilters(String species, String breed, String sex, Integer maxAgeMonths) {
        // Implementación básica que retorna todos por ahora o filtrados por SQL
        return getAllAnimals();
    }

    /**
     * Retorna todos los animales almacenados en la base de datos de Docker.
     */
    public List<Animal> getAllAnimals() {
        // Delegamos la llamada al DAO.
        return animalDAO.findAll();
    }

    // -------------------------------------------------------------------------
    // GESTIÓN MÉDICA
    // -------------------------------------------------------------------------

    public void registerVaccine(String animalId, String vaccineName) throws AnimalNotFoundException {
        Animal animal = findAnimalById(animalId);
        if (vaccineName == null || vaccineName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacío.");
        }
    
        // Buscamos si ya existe un registro médico para el animal
        // (findByAnimal devuelve Optional<MedicalRecord>, no MedicalRecord directo)
        MedicalRecord record = medicalRecordDAO.findByAnimal(animal).orElse(null);
    
        if (record == null) {
            // Si no existe, creamos uno nuevo y lo asociamos al animal
            record = new MedicalRecord("MR-" + animalId, 0, "Sin condiciones especiales", LocalDate.now());
            record.setAnimal(animal);
            medicalRecordDAO.create(record); // Lo guardamos para que tenga un ID
        }
    
        record.getVaccinationHistory().registerVaccine(vaccineName);
        medicalRecordDAO.update(record); // Actualizamos el registro con la nueva vacuna
    }

    // -------------------------------------------------------------------------
    // GESTIÓN DE ADOPCIONES
    // -------------------------------------------------------------------------

    public AdoptionFile processAdoption(String animalId, Adopter adopter, String interviewNotes)
            throws AnimalNotFoundException, AnimalNotAvailableException {

        Animal animal = findAnimalById(animalId);

        if (animal.getStatus() != AdoptionStatus.AVAILABLE) {
            throw new AnimalNotAvailableException(animalId, animal.getStatus().name());
        }

        animal.updateStatus(AdoptionStatus.ADOPTED);
        animalDAO.update(animal);

        String fileNumber = "CONT-" + animalId + "-" + LocalDate.now();

        // Si ya existe un adoptante con esa cédula lo reutilizamos;
        // si no, lo guardamos como nuevo. (La condición original comprobaba
        // si idCard estaba vacío, pero el formulario siempre lo llena, así
        // que el adoptante nunca se llegaba a persistir antes de crear el
        // AdoptionFile que lo referencia.)
        Adopter existingAdopter = adopterDAO.findByIdCard(adopter.getIdCard()).orElse(null);
        if (existingAdopter != null) {
            adopter = existingAdopter;
        } else {
            adopterDAO.create(adopter);
        }

        AdoptionFile file = new AdoptionFile(
                fileNumber,
                LocalDate.now(),
                "APROBADO",
                interviewNotes,
                animal,
                adopter
        );

        adoptionFileDAO.create(file);
        return file;
    }

    public String generateMonthlyReport() {
        List<Animal> animalInventory = getAllAnimals();
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE MENSUAL: REFUGIO ANIMAL UNL =====\n");
        sb.append(String.format("Total animales en BD: %d\n", animalInventory.size()));
        sb.append("------------------------------------------------------\n");

        if (animalInventory.isEmpty()) {
            sb.append("No hay animales registrados en el sistema.\n");
        } else {
            for (Animal a : animalInventory) {
                sb.append(a).append("\n");
                sb.append("   Cuidado: ").append(a.getSpecificCare()).append("\n");
            }
        }

        sb.append("======================================================");
        return sb.toString();
    }

    // --- Getter para el nombre del refugio ---
    public String getShelterName() { return "Refugio Animal UNL"; }

    // -------------------------------------------------------------------------
    // GESTIÓN DE AUTENTICACIÓN
    // -------------------------------------------------------------------------

    /**
     * Autentica un usuario y devuelve el Empleado asociado.
     */
    public Employee login(String username, String password) throws InvalidCredentialsException {
        User usuario = authService.login(username, password);
        return (Employee) usuario;
    }
}