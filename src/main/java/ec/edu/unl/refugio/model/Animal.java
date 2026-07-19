package ec.edu.unl.refugio.model;


public abstract class Animal {

    protected String animalId;
    protected String name;
    protected String breed;
    protected int ageMonths;
    protected AdoptionStatus status;
    protected String sex;

    public Animal(String animalId, String name, String breed,
                  int ageMonths, AdoptionStatus status, String sex) {
        this.animalId  = animalId;
        this.name      = name;
        this.breed     = breed;
        this.ageMonths = ageMonths;
        this.status    = status;
        this.sex       = sex;
    }

    /**
     * Actualiza el estado del animal en el refugio.
     */
    public void updateStatus(AdoptionStatus newStatus) {
        this.status = newStatus;
    }

    /**
     * Retorna un String con el cuidado especÃ­fico de la especie.
     * Contrato polimÃ³rfico: cada subclase lo implementa.
     */
    public abstract String getSpecificCare();

    // --- Getters ---

    public String getAnimalId()      { return animalId; }
    public String getName()          { return name; }
    public String getBreed()         { return breed; }
    public int getAgeMonths()        { return ageMonths; }
    public AdoptionStatus getStatus(){ return status; }
    public String getSex()           { return sex; }

    /**
     * RepresentaciÃ³n textual del animal para reportes.
     * Retorna un String â€” la vista decide cÃ³mo imprimirlo.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s (%s) | Sexo: %s | Edad: %d meses | Estado: %s",
                animalId, name, breed, sex, ageMonths, status);
    }
}
