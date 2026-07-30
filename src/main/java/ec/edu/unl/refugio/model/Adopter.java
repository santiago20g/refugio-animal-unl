package ec.edu.unl.refugio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "adopter")
public class Adopter extends User {

    private String speciesPreference;
    private boolean hasOtherPets;
    private float livingSpaceSqm;

    /** Constructor vacío requerido por JPA. */
    protected Adopter() {
        super();
    }

    public Adopter(String idCard, String fullName, String email, String phone, String address,
                   String speciesPreference, boolean hasOtherPets, float livingSpaceSqm) {
        super(idCard, fullName, email, phone, address);
        this.speciesPreference = speciesPreference;
        this.hasOtherPets      = hasOtherPets;
        this.livingSpaceSqm    = livingSpaceSqm;
    }

    // --- Getters ---

    public String getSpeciesPreference() { return speciesPreference; }
    public boolean isHasOtherPets()      { return hasOtherPets; }
    public float getLivingSpaceSqm()     { return livingSpaceSqm; }
}
