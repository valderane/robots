package io;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import evenements.Simulateur;
import robots.Robot;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Contient toutes les données de simulation. (L'état des incendies, des robots)
 * @author Equipe 23
 *
 */
public class DonneesSimulation {

	private Carte carte;

	private HashMap<Case, HashSet<Robot>> robots;

	private HashMap<Case, HashSet<Incendie>> incendies;

	/**
	 * Constructeur
	 */
	public DonneesSimulation() {
		this.robots = new HashMap<Case, HashSet<Robot>>();
		this.incendies = new HashMap<Case, HashSet<Incendie>>();
	}


	public Carte getCarte() {
		return this.carte;
	}


	public void setCarte(Carte c) {
		this.carte = c;
	}

	
	/** 
	 * AJoute un robot aux données de simulation
	 * @param r Robot à ajouter
	 */
	public void ajouterRobot(Robot r) {
		Case caseRobot = r.getPosition();

		// Si il y a déjà un événement à cette date
		if (this.robots.containsKey(caseRobot)) {
			this.robots.get(caseRobot).add(r);
		} else {
			HashSet<Robot> robotsACetteCase = new HashSet<Robot>();
			robotsACetteCase.add(r);
			this.robots.put(caseRobot, robotsACetteCase);
		}
	}

	/**
	 * Renvoie les robot contenues à la case donnée
	 * @param c Case demandée
	 * @return tableaux contenant les robots. Si aucun robot n'est à cette case, un tableau vide est renvoyé.
	 */
	public Robot[] getRobots(Case c) {
		if (this.robots.containsKey(c))
			return this.robots.get(c).toArray(new Robot[0]);
		else
			return new Robot[0];
	}

	/**
	 * Renvoie tous les robots.
	 * @return Tableau de robots. Tableau vide si aucun robot n'a été ajouté.
	 */
	public Robot[] getRobots() {
		HashSet<Robot> resultat = new HashSet<Robot>();
		for (HashSet<Robot> setRobot : this.robots.values()) {
			resultat.addAll(setRobot);
		}
		return resultat.toArray(new Robot[0]);
	}

	/**
	 * Ajoute un incendie aux données de simulations
	 * @param incendie incendie à ajouter
	 */
	public void ajouterIncendie(Incendie incendie) {
		Case caseIncendie = incendie.getPosition();

		// Si il y a déjà un événement à cette date
		// System.out.println(caseIncendie);
		if (this.incendies.containsKey(caseIncendie)) {
			this.incendies.get(caseIncendie).add(incendie);
		} else {
			HashSet<Incendie> incendiesACetteCase = new HashSet<Incendie>();
			incendiesACetteCase.add(incendie);
			this.incendies.put(caseIncendie, incendiesACetteCase);
		}
	}

	/**
	 * Renvoie tous les incendies contenus sur une case
	 * @param c Case voulue
	 * @return Tableau d'incendies
	 */
	public Incendie[] getIncendies(Case c) {
		if (this.incendies.containsKey(c))
			return this.incendies.get(c).toArray(new Incendie[0]);
		else
			return new Incendie[0];
	}

	/**
	 * Renvoie tous les incendies
	 * @return Tableau contenant tous les incendies
	 */
	public Incendie[] getIncendies() {
		HashSet<Incendie> resultat = new HashSet<Incendie>();
		for (HashSet<Incendie> setIncendie : this.incendies.values()) {
			resultat.addAll(setIncendie);
		}
		return resultat.toArray(new Incendie[0]);
	}


	/**
	 * Supprime un incendie des données de simulation
	 * @param incendie incendie à enlever
	 * @throws IndexOutOfBoundsException lorque l'incendie n'appartient pas aux données de simulation.
	 */
	public void supprimerIncendie(Incendie incendie) throws IndexOutOfBoundsException {
		// System.out.println("Suppression de l'incendie");
		if (!this.incendies.containsKey(incendie.getPosition()))
			throw new IndexOutOfBoundsException("La case n'existe pas");

		this.incendies.get(incendie.getPosition()).remove(incendie);
	}

	/**
	 * Initialise le gestionnaire de déplacements de tous les robots.
	 * @param simulateur Simulateur
	 * @param pasSimulation Pas de simulation
	 */
	public void initialiserGestionnairesDeplacementsRobots(Simulateur simulateur, double pasSimulation) {
		for (Robot robot : this.getRobots())
			robot.initialiserGestionnaireDeplacement(simulateur, pasSimulation, this.carte);
	}

	/**
	 * Initialise le gestionnaire de réservoir de tous les robots.
	 * @param simulateur Simulateur
	 * @param pasSimulation Pas de simulation
	 */
	public void initialiserGestionnairesReservoirsRobots(Simulateur simulateur, long pasSimulation) {
		for (Robot robot : this.getRobots())
			robot.initialiserGestionnaireReservoir(simulateur, pasSimulation);
	}

}
