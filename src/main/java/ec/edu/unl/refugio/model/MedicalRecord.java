package ec.edu.unl.refugio.model;

import java.time.LocalDate;

public class MedicalRecord {

    private String recordId;
    private float weightKg;
    private String specialConditions;
    private LocalDate lastCheckupDate;
    private VaccinationHistory vaccinationHistory;

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
     * Lanza excepciÃ³n de negocio si el valor es invÃ¡lido.
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
        return String.format("Registro [%s] | Peso: %.2f kg | Condiciones: %s | Ãšltimo chequeo: %s",
                recordId, weightKg, specialConditions, lastCheckupDate);
    }

    // --- Getters ---

    public String getRecordId()             { return recordId; }
    public float getWeightKg()              { return weightKg; }
    public String getSpecialConditions()    { return specialConditions; }
    public LocalDate getLastCheckupDate()   { return lastCheckupDate; }
    public VaccinationHistory getVaccinationHistory() { return vaccinationHistory; }
}
