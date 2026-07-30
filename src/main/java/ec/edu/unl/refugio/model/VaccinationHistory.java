package ec.edu.unl.refugio.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class VaccinationHistory {

    private boolean upToDate;

    @ElementCollection
    @CollectionTable(name = "vaccination_history", joinColumns = @JoinColumn(name = "medical_record_id"))
    @Column(name = "vaccine_name")
    private List<String> vaccineList;

    public VaccinationHistory() {
        this.upToDate    = false;
        this.vaccineList = new ArrayList<>();
    }

    /**
     * Registra una nueva vacuna en el historial.
     * @param vaccineName nombre de la vacuna a agregar.
     */
    public void registerVaccine(String vaccineName) {
        if (vaccineName == null || vaccineName.isBlank()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacío.");
        }
        vaccineList.add(vaccineName.trim());
        this.upToDate = true;
    }

    /**
     * Retorna una vista no modificable de la lista de vacunas.
     */
    public List<String> getVaccineList() {
        return Collections.unmodifiableList(vaccineList);
    }

    /**
     * Retorna un resumen en String para que la vista lo imprima.
     */
    public String getSummary() {
        return String.format("Al día: %s | Vacunas: %s", upToDate, vaccineList);
    }

    public boolean isUpToDate() { return upToDate; }
}
