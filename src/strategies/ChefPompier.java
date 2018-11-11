package strategies;

import java.util.HashMap;
import java.util.HashSet;
import carte.Incendie;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import io.DonneesSimulation;
import robots.Robot;

/**
 * Le chef des pompiers a pour mission de diriger les robots. Tous les N pas de temps, ce dernier donne des ordres afin de  diriger les robots vers les incendies.
 * 
 * @author Equipe 23
 *
 */
public class ChefPompier {

	/**
	 * Evite de recalculer les chemins vers des incendies inatteignables.
	 */
	private HashMap<Robot, HashSet<Incendie>> incendiesNonAtteignables;

	/**
	 * Tous les N itérations, le chef pompier exécute des ordres
	 */
	private final int N = 5;

	/**
	 * Itération actuelle
	 */
	private int iteration;

	private DonneesSimulation donneesSimu;

	private Simulateur simulateur;

	/**
	 * Constructeur 
	 * @param data données de simulations auquelles les robots et incendies appartiennent
	 * @param simulateur Simulateur d'événements du plateau
	 */
	public ChefPompier(DonneesSimulation data, Simulateur simulateur) {
		this.donneesSimu = data;
		this.simulateur = simulateur;
		this.iteration = 0;

		this.incendiesNonAtteignables = new HashMap<Robot, HashSet<Incendie>>();

		for (Robot r : this.donneesSimu.getRobots())
			this.incendiesNonAtteignables.put(r, new HashSet<Incendie>());
		this.assignerRobots();

	}

	/**
	 * Incrémente l'itération du chef pompier.
	 */
	public void itererPasSimu() {
		this.iteration++;
		if (this.iteration % this.N == 0)
			this.assignerRobots();
	}

	/**
	 * Assigne les robots aux incendies non assignés.
	 */
	public void assignerRobots() {

		for (Incendie incendie : this.donneesSimu.getIncendies()) {

			if (incendie.isLibre()) {
				for (Robot r : this.donneesSimu.getRobots()) {

					try {
						// si le robot est libre et qu'il peut atteindre l'incendie, on lui assigne
						// l'incendie
						if (!this.incendiesNonAtteignables.get(r).contains(incendie) && r.isLibre()) {
							r.eteindreIncendie(incendie, this.donneesSimu, this.simulateur.getDateSimulation());
							r.setLibre(false);
							incendie.setLibre(false);
							break;
						}
					} catch (AucunCheminPossible e) {
						this.incendiesNonAtteignables.get(r).add(incendie);
					}
				}
			}
		}
	}

}
