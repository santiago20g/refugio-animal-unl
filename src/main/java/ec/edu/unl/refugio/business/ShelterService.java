package ec.edu.unl.refugio.business;

import ec.edu.unl.refugio.exception.AnimalNotAvailableException;
import ec.edu.unl.refugio.exception.AnimalNotFoundException;
import ec.edu.unl.refugio.exception.InvalidCredentialsException;
import ec.edu.unl.refugio.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShelterService {

    private String shelterName;
    private final List<Animal> animalInventory;
    private final List<AdoptionFile> adoptionHistory;
    private final List<Employee> employees;

    /**
     * Constructor sin argumentos requerido por CDI/Weld para poder
     * crear el proxy del bean @ApplicationScoped. No usar directamente.
     */
    protected ShelterService() {
        this.shelterName     = null;
        this.animalInventory = new ArrayList<>();
        this.adoptionHistory = new ArrayList<>();
        this.employees       = new ArrayList<>();
    }

    public ShelterService(String shelterName) {
        this.shelterName     = shelterName;
        this.animalInventory = new ArrayList<>();
        this.adoptionHistory = new ArrayList<>();
        this.employees       = new ArrayList<>();
        initDefaultEmployees();
    }

    // -------------------------------------------------------------------------
    // AUTENTICACIÃ“N
    // -------------------------------------------------------------------------

    /**
     * Autentica a un empleado con sus credenciales.
     * @throws InvalidCredentialsException si ningÃºn empleado coincide.
     */
    public Employee login(String user, String password) throws InvalidCredentialsException {
        for (Employee emp : employees) {
            if (emp.checkCredentials(user, password)) {
                return emp;
            }
        }
        throw new InvalidCredentialsException();
    }

    // -------------------------------------------------------------------------
    // GESTIÃ“N DE ANIMALES
    // -------------------------------------------------------------------------

    /**
     * Registra un nuevo animal en el inventario del refugio.
     * @param animal el animal a registrar.
     */
    public void registerIntake(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("El animal no puede ser nulo.");
        }
        animalInventory.add(animal);
    }

    /**
     * Busca un animal por su ID.
     * @throws AnimalNotFoundException si el ID no existe en el inventario.
     */
    public Animal findAnimalById(String animalId) throws AnimalNotFoundException {
        return animalInventory.stream()
                .filter(a -> a.getAnimalId().equalsIgnoreCase(animalId))
                .findFirst()
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
    }

    /**
     * Filtra el inventario por criterios opcionales.
     * Los parÃ¡metros null o vacÃ­os son ignorados (no filtran).
     */
    public List<Animal> searchByFilters(String species, String breed, String sex, Integer maxAgeMonths) {
        return animalInventory.stream()
                .filter(a -> species == null || species.isBlank()
                        || (species.equalsIgnoreCase("perro") && a instanceof Dog)
                        || (species.equalsIgnoreCase("gato")  && a instanceof Cat))
                .filter(a -> breed == null || breed.isBlank()
                        || a.getBreed().equalsIgnoreCase(breed))
                .filter(a -> sex == null || sex.isBlank()
                        || a.getSex().equalsIgnoreCase(sex))
                .filter(a -> maxAgeMonths == null || a.getAgeMonths() <= maxAgeMonths)
                .collect(Collectors.toList());
    }

    /**
     * Retorna una copia no modificable del inventario completo.
     */
    public List<Animal> getAllAnimals() {
        return List.copyOf(animalInventory);
    }

    // -------------------------------------------------------------------------
    // GESTIÃ“N MÃ‰DICA
    // -------------------------------------------------------------------------

    /**
     * Registra una vacuna en el historial mÃ©dico de un animal.
     * @throws AnimalNotFoundException si el animal no existe.
     */
    public void registerVaccine(String animalId, String vaccineName) throws AnimalNotFoundException {
        Animal animal = findAnimalById(animalId);
        // En un sistema real, MedicalRecord estarÃ­a asociado al Animal.
        // AquÃ­ mostramos cÃ³mo se comunica la capa de negocio con el modelo.
        if (vaccineName == null || vaccineName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacÃ­o.");
        }

        MedicalRecord record = new MedicalRecord(
                "MR-" + animalId, 0, "Sin condiciones especiales", LocalDate.now()
        );
        record.getVaccinationHistory().registerVaccine(vaccineName);
    }

    // -------------------------------------------------------------------------
    // GESTIÃ“N DE ADOPCIONES
    // -------------------------------------------------------------------------


    public AdoptionFile processAdoption(String animalId, Adopter adopter,
                                        String interviewNotes)
            throws AnimalNotFoundException, AnimalNotAvailableException {

        Animal animal = findAnimalById(animalId);

        if (animal.getStatus() != AdoptionStatus.AVAILABLE) {
            throw new AnimalNotAvailableException(animalId, animal.getStatus().name());
        }

        animal.updateStatus(AdoptionStatus.ADOPTED);

        String fileNumber = "CONT-" + animalId + "-" + LocalDate.now();
        AdoptionFile file = new AdoptionFile(
                fileNumber,
                LocalDate.now(),
                "APROBADO",
                interviewNotes,
                animal,
                adopter
        );

        adoptionHistory.add(file);
        return file;
    }

    public String generateMonthlyReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE MENSUAL: ").append(shelterName.toUpperCase()).append(" =====\n");
        sb.append(String.format("Total animales: %d | Adopciones este mes: %d\n",
                animalInventory.size(), adoptionHistory.size()));
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

    // -------------------------------------------------------------------------
    // DATOS INICIALES (simulando base de datos)
    // -------------------------------------------------------------------------

    private void initDefaultEmployees() {
        employees.add(new Employee(
                "1104567890", "Richard Santiago GuamÃ¡n", "rguaman@refugio.ec",
                "0991234567", "Loja, Ecuador",
                "EMP-001", "Administrador", "santiago", "123456"
        ));
    }

    // --- Getter para el nombre ---
    public String getShelterName() { return shelterName; }
}