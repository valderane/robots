package stratégies;

import java.util.HashMap;
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

	/**
	 * Contient les incendies, et précise s'ils sont déjà affectés
	 */
	private HashMap<Incendie, Boolean> incendies;

	public ChefPompier(Robot[] robots, Incendie[] incendies) {
		this.robots = robots;
		this.incendies = new HashMap<Incendie, Boolean>();

		for (Incendie incendie : incendies)
			this.incendies.put(incendie, this.NON_AFFECTE);
	}

	public void assignerRobots() {
		long startTime;
		long endTime;
		long duration;

		for (Incendie incendie : this.incendies.keySet().toArray(new Incendie[0])) {
			Boolean estAffecte = this.incendies.get(incendie);

			if (!estAffecte.booleanValue()) {
				for (Robot r : this.robots) {
					System.out.println(r);
					
					startTime = System.nanoTime();

					try {
						// si le robot est libre, on lui assigne l'incendie
						if (r.isLibre()) {
							r.deplacer(incendie.getPosition());
							r.setLibre(false);
							this.incendies.put(incendie, !this.NON_AFFECTE);
							break;
						}
					} catch (AucunCheminPossible e) {
						System.out.println(r + " incendie : " + incendie);
						System.out.println("robot n'a pas trouvé de chemin");
					}
					endTime = System.nanoTime();
					duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
					System.out.println(duration);
				}
			}
		}
	}

}
