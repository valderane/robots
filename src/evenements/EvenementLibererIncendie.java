package evenements;

import carte.Incendie;

/**
 * Evenement qui libère un incendie à une certaine date. Un incendie est libre
 * lorsqu'aucun robot n'a pour objectif de l'éteindre
 * 
 * @author Equipe 23
 *
 */
public class EvenementLibererIncendie extends Evenement {

	private Incendie incendie;

	/**
	 * Constructeur 
	 * @param date Date de l'événement
	 * @param incendie Incendie à libérer
	 */
	public EvenementLibererIncendie(long date, Incendie incendie) {
		super(date);
		this.incendie = incendie;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {
		this.incendie.setLibre(true);
	}

}
