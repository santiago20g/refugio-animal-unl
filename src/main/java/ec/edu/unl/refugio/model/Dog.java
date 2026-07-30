package ec.edu.unl.refugio.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa un perro en el refugio.
 * Extiende Animal e implementa el cuidado específico de la especie.
 */
@Entity
@DiscriminatorValue("DOG")
public class Dog extends Animal {

    private String energyLevel;

    /** Constructor vacío requerido por JPA. */
    protected Dog() {
        super();
    }

    public Dog(String animalId, String name, String breed,
               int ageMonths, AdoptionStatus status, String sex, String energyLevel) {
        super(animalId, name, breed, ageMonths, status, sex);
        this.energyLevel = energyLevel;
    }

    /**
     * Retorna el cuidado específico como String.
     * La vista es quien decide cómo mostrar este texto.
     */
    @Override
    public String getSpecificCare() {
        return "Necesita paseos diarios. Nivel de energía: " + energyLevel;
    }

    // --- Getter ---
    public String getEnergyLevel() { return energyLevel; }
}
