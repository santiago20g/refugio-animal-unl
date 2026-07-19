package ec.edu.unl.refugio.model;

import java.time.LocalDate;

/**
 * Expediente de adopciÃ³n que vincula un Animal con un Adopter.
 * Contiene los datos del proceso. No imprime nada â€” devuelve Strings.
 */
public class AdoptionFile {

    private String fileNumber;
    private LocalDate adoptionDate;
    private String approvalStatus;
    private String interviewNotes;
    private Animal animal;       // RelaciÃ³n corregida: el expediente conoce al animal
    private Adopter adopter;     // RelaciÃ³n corregida: el expediente conoce al adoptante

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
     * Valida si el expediente cumple los requisitos mÃ­nimos para ser aprobado.
     * Regla de negocio: las notas de entrevista no pueden estar vacÃ­as.
     */
    public boolean validateRequirements() {
        return interviewNotes != null && !interviewNotes.isBlank();
    }

    /**
     * Genera el contenido textual del contrato de adopciÃ³n.
     * Retorna un String â€” la vista decide dÃ³nde mostrarlo o guardarlo.
     */
    public String buildContractContent() {
        return String.format(
            "===== CONTRATO DE ADOPCIÃ“N #%s =====\n" +
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

    public String getFileNumber()     { return fileNumber; }
    public LocalDate getAdoptionDate(){ return adoptionDate; }
    public String getApprovalStatus() { return approvalStatus; }
    public String getInterviewNotes() { return interviewNotes; }
    public Animal getAnimal()         { return animal; }
    public Adopter getAdopter()       { return adopter; }
}
