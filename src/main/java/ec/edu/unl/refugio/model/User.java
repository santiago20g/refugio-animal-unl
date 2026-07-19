package ec.edu.unl.refugio.model;

/**
 * Clase base abstracta que representa a cualquier usuario del sistema.
 * Contiene Ãºnicamente datos comunes. CERO lÃ³gica de consola.
 */
public abstract class User {

    protected String idCard;
    protected String fullName;
    protected String email;
    protected String phone;
    protected String address;

    public User(String idCard, String fullName, String email, String phone, String address) {
        this.idCard = idCard;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // --- Getters ---

    public String getIdCard()    { return idCard; }
    public String getFullName()  { return fullName; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }
    public String getAddress()   { return address; }
}
