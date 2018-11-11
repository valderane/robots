package evenements;

import carte.Incendie;
import io.DonneesSimulation;

/**
 * Evenement pour supprimer un incendie à une certaine date.
 * 
 * @author Equipe 23
 *
 */
public class EvenementSupprimerIncendie extends Evenement{
	
	private Incendie incendie;
	
	private DonneesSimulation donneesSimu;
	
	/** 
	 * Constructeur 
	 * 
	 * @param date Date de l'évenement.
	 * @param incendie Incendie à supprimer
	 * @param donneesSimu Données de simulation qui contiennent l'incendie.
	 */
	public EvenementSupprimerIncendie(long date, Incendie incendie, DonneesSimulation donneesSimu) {
		super(date);
		this.incendie = incendie;
		this.donneesSimu = donneesSimu;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {
		this.donneesSimu.supprimerIncendie(incendie);
	}

}
