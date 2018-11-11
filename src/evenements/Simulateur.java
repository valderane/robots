package evenements;

import java.util.ArrayList;
import java.util.TreeMap;
import evenements.Evenement;

/**
 * Permet de simuler le temps en mémorisant une date courante de simulation, et
 * d'ajouter des événements à certaines dates.
 * 
 * @author Equipe 23
 *
 */
public class Simulateur {

	private TreeMap<Long, ArrayList<Evenement>> evenements;
	private long dateSimulation;

	/**
	 * Constructeur. Initialise la date de simulation à 0.
	 */
	public Simulateur() {
		this.evenements = new TreeMap<Long, ArrayList<Evenement>>();
		this.dateSimulation = 0L;
	}

	/**
	 * Ajoute un événement au simulateur.
	 * 
	 * @param e Évévenement à ajouter.
	 */
	public void ajouteEvenement(Evenement e) {
		// System.out.println("ajout Evenement : "+e);
		long date = e.getDate();
		ArrayList<Evenement> evenementsPourCetteDate;

		// Si il y a déjà un événement à cette date
		if (this.evenements.containsKey(date)) {
			this.evenements.get(date).add(e);
		} else {
			evenementsPourCetteDate = new ArrayList<Evenement>();
			evenementsPourCetteDate.add(e);
			this.evenements.put(date, evenementsPourCetteDate);
		}
	}

	/**
	 * Renvoie l'ensemble des événements triés par date. Cela permet d'obtenir les
	 * événements dans leur ordre naturel d'exécution.
	 * 
	 * @return Ensemble des événements triés par date.
	 */
	public TreeMap<Long, ArrayList<Evenement>> getEvenements() {
		return evenements;
	}

	public long getDateSimulation() {
		return dateSimulation;
	}

	public void setDateSimulation(long dateSimulation) {
		this.dateSimulation = dateSimulation;
	}

}
