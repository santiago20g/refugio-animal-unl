package ec.edu.unl.refugio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Expediente de adopción que vincula un Animal con un Adopter.
 * Contiene los datos del proceso. No imprime nada — devuelve Strings.
 */
@Entity
@Table(name = "adoption_file")
public class AdoptionFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileNumber;
    private LocalDate adoptionDate;
    private String approvalStatus;
    private String interviewNotes;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;       // Relación corregida: el expediente conoce al animal

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;     // Relación corregida: el expediente conoce al adoptante

    /** Constructor vacío requerido por JPA. */
    protected AdoptionFile() {
    }

    public AdoptionFile(String fileNumber, LocalDate adoptionDate,
                        String approvalStatus, String interviewNotes,
                        Animal animal, Adopter adopter) {
        this.fileNumber      = fileNumber;
        this.adoptionDate    = adoptionDate;
        this.approvalStatus  = approvalStatus;
        this.interviewNotes  = interviewNotes;
        this.animal          = animal;
        this.adopter         = adopter;
    }

    /**
     * Valida si el expediente cumple los requisitos mínimos para ser aprobado.
     * Regla de negocio: las notas de entrevista no pueden estar vacías.
     */
    public boolean validateRequirements() {
        return interviewNotes != null && !interviewNotes.isBlank();
    }

    /**
     * Genera el contenido textual del contrato de adopción.
     * Retorna un String — la vista decide dónde mostrarlo o guardarlo.
     */
    public String buildContractContent() {
        return String.format(
            "===== CONTRATO DE ADOPCIÓN #%s =====\n" +
            "Fecha       : %s\n" +
            "Estado      : %s\n" +
            "Animal      : %s (%s)\n" +
            "Adoptante   : %s | CI: %s\n" +
            "Notas       : %s\n" +
            "=====================================",
            fileNumber, adoptionDate, approvalStatus,
            animal.getName(), animal.getBreed(),
            adopter.getFullName(), adopter.getIdCard(),
            interviewNotes
        );
    }

    // --- Getters ---

    public Long getId()               { return id; }
    public String getFileNumber()     { return fileNumber; }
    public LocalDate getAdoptionDate(){ return adoptionDate; }
    public String getApprovalStatus() { return approvalStatus; }
    public String getInterviewNotes() { return interviewNotes; }
    public Animal getAnimal()         { return animal; }
    public Adopter getAdopter()       { return adopter; }
}
