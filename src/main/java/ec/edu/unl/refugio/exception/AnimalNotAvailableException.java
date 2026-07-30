package ec.edu.unl.refugio.exception;


public class AnimalNotAvailableException extends Exception {

    public AnimalNotAvailableException(String animalId, String currentStatus) {
        super(String.format(
            "El animal con ID '%s' no está disponible para adopción. Estado actual: %s.",
            animalId, currentStatus
        ));
    }
}
