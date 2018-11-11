package exceptions.exceptions_chemins;

/**
 * Exception représentant la situation dans laquelle aucun chemin n'existe.
 * 
 * @author Equipe 23
 *
 */
public class AucunCheminPossible extends Exception {

	/**
	 * @param s Message d'erreur
	 */
	public AucunCheminPossible(String s) {
		super(s);
	}
}
