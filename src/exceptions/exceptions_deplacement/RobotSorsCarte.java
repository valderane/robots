package exceptions.exceptions_deplacement;

/**
 * Exception lorsqu'un robot sort de la carte.
 * 
 * @author Equipe 23
 *
 */
public class RobotSorsCarte extends ErreurDeplacement  {
	
	/**
	 * @param s Message d'erreur
	 */
    public RobotSorsCarte(String s) {
        super(s);
    }
}