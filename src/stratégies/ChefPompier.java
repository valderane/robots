package stratégies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import carte.Incendie;
import exceptions.exceptions_chemins.AucunCheminPossible;
import robots.Robot;

/**
 * 
 * 
 * @author
 *
 */
public class ChefPompier {

	private final boolean NON_AFFECTE = false;

	private Robot[] robots;

	private HashMap<Robot, HashSet<Incendie>> incendiesNonAtteignables;
	
	private final int N = 5;
	
	private int iteration;

	/**
	 * Contient les incendies, et précise s'ils sont déjà affectés
	 */
	private HashMap<Incendie, Boolean> incendies;

	public ChefPompier(Robot[] robots, Incendie[] incendies) {
		this.iteration = 0;
		this.robots = robots;
		
		/* initialisation des incendies */
		this.incendies = new HashMap<Incendie, Boolean>();
		
		for (Incendie incendie : incendies)
			this.incendies.put(incendie, this.NON_AFFECTE);

		this.incendiesNonAtteignables = new HashMap<Robot, HashSet<Incendie>>();

		for (Robot r : this.robots) 
			this.incendiesNonAtteignables.put(r, new HashSet<Incendie>());
		this.assignerRobots();

	}
	
	public void itererPasSimu() {
		this.iteration++;
		if(this.iteration % this.N == 0)
			this.assignerRobots();
	}

	
	public void assignerRobots() {

		for (Incendie incendie : this.incendies.keySet().toArray(new Incendie[0])) {
			Boolean estAffecte = this.incendies.get(incendie);

			if (!estAffecte.booleanValue()) {
				for (Robot r : this.robots) {

					try {
						// si le robot est libre et qu'il peut atteindre l'incendie, on lui assigne
						// l'incendie
						if (!this.incendiesNonAtteignables.get(r).contains(incendie) && r.isLibre()) {
							r.deplacer(incendie.getPosition());
							r.setLibre(false);
							this.incendies.put(incendie, !this.NON_AFFECTE);
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
