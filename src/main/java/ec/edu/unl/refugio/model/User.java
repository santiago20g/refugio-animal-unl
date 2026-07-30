package ec.edu.unl.refugio.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Clase base abstracta que representa a cualquier usuario del sistema.
 * Contiene únicamente datos comunes. CERO lógica de consola.
 *
 * @MappedSuperclass: no es una entidad JPA por sí misma (no tiene tabla propia),
 * pero sus campos SÍ se heredan como columnas en las entidades hijas (Adopter, Employee).
 */
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String idCard;
    protected String fullName;
    protected String email;
    protected String phone;
    protected String address;

    /** Constructor vacío requerido por JPA. */
    protected User() {
    }

    public User(String idCard, String fullName, String email, String phone, String address) {
        this.idCard = idCard;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // --- Getters ---

    public Long getId()          { return id; }
    public String getIdCard()    { return idCard; }
    public String getFullName()  { return fullName; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }
    public String getAddress()   { return address; }
}
