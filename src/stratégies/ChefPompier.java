package stratégies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import carte.Incendie;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import io.DonneesSimulation;
import robots.Robot;

/**
 * 
 * 
 * @author
 *
 */
public class ChefPompier {

	private HashMap<Robot, HashSet<Incendie>> incendiesNonAtteignables;
	
	private final int N = 5;
	
	private int iteration;
	
	private DonneesSimulation donneesSimu;
	
	private Simulateur simulateur;

	/**
	 * Contient les incendies, et précise s'ils sont déjà affectés
	 */
	private HashMap<Incendie, Boolean> incendies;

	public ChefPompier(DonneesSimulation data, Simulateur simulateur) {
		this.donneesSimu = data;
		this.simulateur = simulateur;
		this.iteration = 0;

		this.incendiesNonAtteignables = new HashMap<Robot, HashSet<Incendie>>();

		for (Robot r : this.donneesSimu.getRobots()) 
			this.incendiesNonAtteignables.put(r, new HashSet<Incendie>());
		this.assignerRobots();

	}
	
	public void itererPasSimu() {
		this.iteration++;
		if(this.iteration % this.N == 0)
			this.assignerRobots();
	}

	
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
