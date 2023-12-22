package projet;
import java.io.IOException;

//Cette classe représente une exception personnalisée pour les entrées invalides

public class InvalidInputException extends IOException {
	private static final long serialVersionUID = 1L;
	public InvalidInputException(String message) {
		super(message);
	}	
}
