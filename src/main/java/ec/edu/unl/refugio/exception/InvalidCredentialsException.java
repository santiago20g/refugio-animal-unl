package ec.edu.unl.refugio.exception;


public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Credenciales incorrectas. Acceso denegado al sistema.");
    }
}
