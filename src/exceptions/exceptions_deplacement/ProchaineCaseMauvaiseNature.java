package exceptions.exceptions_deplacement;

/**
 * Exception lorsqu'une case d'un futur déplacement n'est pas praticable
 * 
 * @author Equipe 23
 */
public class ProchaineCaseMauvaiseNature extends ErreurDeplacement  {
	
	/**
	 * @param s Message d'erreur
	 */
    public ProchaineCaseMauvaiseNature(String s) {
        super(s);
    }
}