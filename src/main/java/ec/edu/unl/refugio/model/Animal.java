package ec.edu.unl.refugio.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "animal")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "species")
public abstract class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "animal_code", unique = true, nullable = false)
    protected String animalId;

    protected String name;
    protected String breed;

    @Column(name = "age")
    protected int ageMonths;

    @Enumerated(EnumType.STRING)
    protected AdoptionStatus status;

    protected String sex;

    /** Constructor vacío requerido por JPA. */
    protected Animal() {
    }

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
     * Retorna un String con el cuidado específico de la especie.
     * Contrato polimórfico: cada subclase lo implementa.
     */
    public abstract String getSpecificCare();

    // --- Getters ---

    public Long getId()              { return id; }
    public String getAnimalId()      { return animalId; }
    public String getName()          { return name; }
    public String getBreed()         { return breed; }
    public int getAgeMonths()        { return ageMonths; }
    public AdoptionStatus getStatus(){ return status; }
    public String getSex()           { return sex; }

    /**
     * Representación textual del animal para reportes.
     * Retorna un String — la vista decide cómo imprimirlo.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s (%s) | Sexo: %s | Edad: %d meses | Estado: %s",
                animalId, name, breed, sex, ageMonths, status);
    }

    @jakarta.persistence.Transient
    public String getAnimalType() {
        if (this instanceof Dog) {
            return "Perro";
        } else if (this instanceof Cat) {
            return "Gato";
        }
        return "Desconocido";
    }

    // Este es el antídoto para el error de la caché
    @jakarta.persistence.Transient
    public String getSpecies() {
        if (this instanceof Dog) return "DOG";
        if (this instanceof Cat) return "CAT";
        return this.getClass().getSimpleName().toUpperCase();
    }
}
