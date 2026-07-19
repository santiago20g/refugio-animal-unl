package ec.edu.unl.refugio.model;

/**
 * Representa a un empleado del refugio.
 * La verificaciÃ³n de credenciales retorna un booleano â€” NO imprime nada.
 */
public class Employee extends User {

    private String employeeId;
    private String position;
    private String loginUser;
    private String password;

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
     * @return true si el usuario y contraseÃ±a coinciden, false en caso contrario.
     */
    public boolean checkCredentials(String user, String pass) {
        return this.loginUser.equals(user) && this.password.equals(pass);
    }

    // --- Getters ---

    public String getEmployeeId() { return employeeId; }
    public String getPosition()   { return position; }
    public String getLoginUser()  { return loginUser; }
}
