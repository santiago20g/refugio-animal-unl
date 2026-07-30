package ec.edu.unl.refugio.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa un gato en el refugio.
 * Extiende Animal e implementa el cuidado específico de la especie.
 */
@Entity
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    private boolean independent;

    /** Constructor vacío requerido por JPA. */
    protected Cat() {
        super();
    }

    public Cat(String animalId, String name, String breed,
               int ageMonths, AdoptionStatus status, String sex, boolean independent) {
        super(animalId, name, breed, ageMonths, status, sex);
        this.independent = independent;
    }

    /**
     * Retorna el cuidado específico como String.
     */
    @Override
    public String getSpecificCare() {
        return "Requiere limpieza frecuente del arenero. Carácter independiente: " + independent;
    }

    // --- Getter ---
    public boolean isIndependent() { return independent; }
}
