package ec.edu.unl.refugio.exception;


public class AnimalNotFoundException extends Exception {

    public AnimalNotFoundException(String animalId) {
        super(String.format("No se encontrÃ³ ningÃºn animal con ID '%s' en el sistema.", animalId));
    }
}
