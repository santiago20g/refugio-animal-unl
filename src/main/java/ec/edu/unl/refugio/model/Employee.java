package ec.edu.unl.refugio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Representa a un empleado del refugio.
 * La verificación de credenciales retorna un booleano — NO imprime nada.
 */
@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(name = "employee_id")
    private String employeeId;
    private String position;

    @Column(name = "username")
    private String loginUser;
    private String password;

    /** Constructor vacío requerido por JPA. */
    protected Employee() {
        super();
    }

    public Employee(String idCard, String fullName, String email, String phone, String address,
                    String employeeId, String position, String loginUser, String password) {
        super(idCard, fullName, email, phone, address);
        this.employeeId = employeeId;
        this.position   = position;
        this.loginUser  = loginUser;
        this.password   = password;
    }

    /**
     * Verifica las credenciales del empleado.
     * @return true si el usuario y contraseña coinciden, false en caso contrario.
     */
    public boolean checkCredentials(String user, String pass) {
        return this.loginUser.equals(user) && this.password.equals(pass);
    }

    // --- Getters ---

    public String getEmployeeId() { return employeeId; }
    public String getPosition()   { return position; }
    public String getLoginUser()  { return loginUser; }
}
