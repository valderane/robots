package exceptions.exceptions_deplacement;

/**
 * Exception représentant une erreur de déplacement.
 * 
 * @author Equipe 23
 *
 */
public abstract class ErreurDeplacement extends Exception {
	
	/**
	 * @param s Message d'erreur
	 */
    public ErreurDeplacement(String s) {
        super(s);
    }
}