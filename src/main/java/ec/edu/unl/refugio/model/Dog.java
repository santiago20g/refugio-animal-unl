package ec.edu.unl.refugio.model;

/**
 * Representa un perro en el refugio.
 * Extiende Animal e implementa el cuidado especÃ­fico de la especie.
 */
public class Dog extends Animal {

    private String energyLevel;

    public Dog(String animalId, String name, String breed,
               int ageMonths, AdoptionStatus status, String sex, String energyLevel) {
        super(animalId, name, breed, ageMonths, status, sex);
        this.energyLevel = energyLevel;
    }

    /**
     * Retorna el cuidado especÃ­fico como String.
     * La vista es quien decide cÃ³mo mostrar este texto.
     */
    @Override
    public String getSpecificCare() {
        return "Necesita paseos diarios. Nivel de energÃ­a: " + energyLevel;
    }

    // --- Getter ---
    public String getEnergyLevel() { return energyLevel; }
}
