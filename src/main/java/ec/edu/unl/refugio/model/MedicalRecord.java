package ec.edu.unl.refugio.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recordId;
    private float weightKg;
    private String specialConditions;
    private LocalDate lastCheckupDate;

    @Embedded
    private VaccinationHistory vaccinationHistory;

    // Relación corregida: el registro médico ahora sí conoce a su animal,
    // lo cual permite que MedicalRecordDAO.findByAnimal(animal) funcione.
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    /** Constructor vacío requerido por JPA. */
    protected MedicalRecord() {
    }

    public MedicalRecord(String recordId, float weightKg,
                         String specialConditions, LocalDate lastCheckupDate) {
        this.recordId           = recordId;
        this.weightKg           = weightKg;
        this.specialConditions  = specialConditions;
        this.lastCheckupDate    = lastCheckupDate;
        this.vaccinationHistory = new VaccinationHistory();
    }

    /**
     * Actualiza el peso del animal.
     * Lanza excepción de negocio si el valor es inválido.
     */
    public void updateWeight(float newWeight) {
        if (newWeight <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a cero.");
        }
        this.weightKg = newWeight;
    }

    /**
     * Retorna un resumen como String para que la vista lo muestre.
     */
    public String getSummary() {
        return String.format("Registro [%s] | Peso: %.2f kg | Condiciones: %s | Último chequeo: %s",
                recordId, weightKg, specialConditions, lastCheckupDate);
    }

    // --- Getters / Setters ---

    public Long getId()                     { return id; }
    public String getRecordId()             { return recordId; }
    public float getWeightKg()              { return weightKg; }
    public String getSpecialConditions()    { return specialConditions; }
    public LocalDate getLastCheckupDate()   { return lastCheckupDate; }
    public VaccinationHistory getVaccinationHistory() { return vaccinationHistory; }
    public Animal getAnimal()               { return animal; }
    public void setAnimal(Animal animal)    { this.animal = animal; }
}
