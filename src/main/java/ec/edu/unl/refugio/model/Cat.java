package ec.edu.unl.refugio.model;

/**
 * Representa un gato en el refugio.
 * Extiende Animal e implementa el cuidado especÃ­fico de la especie.
 */
public class Cat extends Animal {

    private boolean independent;

    public Cat(String animalId, String name, String breed,
               int ageMonths, AdoptionStatus status, String sex, boolean independent) {
        super(animalId, name, breed, ageMonths, status, sex);
        this.independent = independent;
    }

    /**
     * Retorna el cuidado especÃ­fico como String.
     */
    @Override
    public String getSpecificCare() {
        return "Requiere limpieza frecuente del arenero. CarÃ¡cter independiente: " + independent;
    }

    // --- Getter ---
    public boolean isIndependent() { return independent; }
}
