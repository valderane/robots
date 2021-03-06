package chemins;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import carte.Carte;
import carte.Case;
import carte.Direction;
import exceptions.exceptions_chemins.AucunCheminPossible;
import robots.Robot;

/**
 * Algorithme de plus court chemin. Se base sur l'algorithme de Djikstra
 * 
 * @author Equipe 23
 *
 */
public class Djikstra extends AlgoPlusCourtChemin {

	/**
	 * Distances minimale pour atteindre chaque case depuis la case de départ
	 */
	private HashMap<Case, Double> tempsParcours;

	/**
	 * Case précédente utilisée pour atteindre chaque case avec le plus court chemin
	 */
	private HashMap<Case, Case> precedents;

	/**
	 * Contient les liens non utilisés entre les cases au sens d'un graphe de
	 * Djikstra. Chaque lien représente le temps de parcours moyen pour passer d'une
	 * case à l'autre
	 */
	private HashMap<Case, Vector<Lien>> tempsParcoursEntreCasesAdjacentes;

	/**
	 * Cases visitées. Pour l'algorithme de Djikstra, seules ces cases peuvent être
	 * choisies comme point de départ d'une itération.
	 */
	private HashSet<Case> casesVisitees;

	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public Djikstra(Carte carte, Robot robot) {
		super(carte, robot);
		this.initialiserStructureDeDonnees();
	}

	/**
	 * Initialise les structures de données de façon minimale
	 */
	private void initialiserStructureDeDonnees() {
		this.tempsParcoursEntreCasesAdjacentes = new HashMap<Case, Vector<Lien>>();
		this.casesVisitees = new HashSet<Case>();
		this.tempsParcours = new HashMap<Case, Double>();
		this.precedents = new HashMap<Case, Case>();
	}

	/**
	 * Initialise toutes les valeurs des cases à infini. La valeur de la case du
	 * robot est à 0
	 */
	private void initialiserTempsParcours(Case caseDeDepart) {
		for (int ligne = 0; ligne < this.getCarte().getNbLignes(); ++ligne)
			for (int col = 0; col < this.getCarte().getNbColonnes(); ++col) {
				Case caseActuelle = this.getCarte().getCase(ligne, col);
				this.tempsParcours.put(caseActuelle, Double.POSITIVE_INFINITY);
			}

		this.tempsParcours.put(caseDeDepart, 0.0);
		this.casesVisitees.add(caseDeDepart);
	}

	/**
	 * Initialise le temps de parcours entre cases adjacentes sous forme de liens pondérés.
	 */
	private void initialiserTempsParcoursEntreCasesAdjacentes() {
		Case caseActuelle, caseVoisine;
		for (int ligne = 0; ligne < this.getCarte().getNbLignes(); ++ligne)
			for (int col = 0; col < this.getCarte().getNbColonnes(); ++col) {
				caseActuelle = this.getCarte().getCase(ligne, col);

				// vérification des voisins pour chaque direction
				for (Direction dir : Direction.values()) {
					caseVoisine = this.getCarte().getVoisin(caseActuelle, dir);

					// Dans le cas où un voisin existe, on ajoute un lien
					if (caseVoisine != null) {
						try {
							this.ajouterLien(new Lien(caseActuelle, caseVoisine, dir,
									this.getRobot().getTempsParcours(caseActuelle.getNature(), caseVoisine.getNature(),
											this.getCarte().getTailleCases())));
							// System.out.println("ajout case voisine : "+caseVoisine+" direction : "+dir);
						} catch (AucunCheminPossible event) {
						} // Aucun lien n'est créé si le robot ne peut pas passer d'une case à l'autre
					}
				}
			}
	}

	/**
	 * Ajoute un lien au graphe de Djikstra
	 * @param l Lien à ajouter
	 */
	private void ajouterLien(Lien l) {
		Vector<Lien> liens;

		// si des liens existent déjà depuis cette case, on ajoute le nouveau lien à la
		// suite des liens existant
		if (this.tempsParcoursEntreCasesAdjacentes.containsKey(l.getCaseDepart()))
			liens = this.tempsParcoursEntreCasesAdjacentes.get(l.getCaseDepart());
		else {
			liens = new Vector<Lien>();
			this.tempsParcoursEntreCasesAdjacentes.put(l.getCaseDepart(), liens);
		}
		liens.add(l);
	}

	/**
	 * Retire un lien des liens disponibles
	 * 
	 * @param l Lien à retirer
	 */
	private void retirerLien(Lien l) {
		this.tempsParcoursEntreCasesAdjacentes.get(l.getCaseDepart()).remove(l);
	}

	/**
	 * Affiche le temps parcouru pour passer d'une case à l'autre. Le parcours se
	 * fait toujours avec deux cases adjacentes
	 */
	public void afficherTempsParcours() {
		for (Case c : this.tempsParcoursEntreCasesAdjacentes.keySet()) {
			// System.out.println("Case de départ " + c);
			for (Lien l : this.tempsParcoursEntreCasesAdjacentes.get(c)) {
				System.out.println("Case arrivée : " + l.getCaseDestination() + ", temps parcours : " + l.getPoids());
			}
		}
	}

	/**
	 * Choisit le lien de la prochaine itération, et supprime les liens inutiles.
	 * 
	 * @return Le lien de la prochaine itération.
	 */
	private Lien choisirLienProchaineIteration() {
		double tempsMin = Double.POSITIVE_INFINITY;
		Lien lienChoisi = null;
		for (Case c : this.casesVisitees) {
			for (Lien l : this.tempsParcoursEntreCasesAdjacentes.get(c).toArray(new Lien[0])) {
				if (this.tempsParcours.get(c) + l.getPoids() < this.tempsParcours.get(l.getCaseDestination())) {
					// on choisit le temps de parcours (lien + poids case départ) le plus faible
					if (tempsMin > this.tempsParcours.get(c) + l.getPoids()) {
						tempsMin = this.tempsParcours.get(c) + l.getPoids();
						lienChoisi = l;
					}
				}
				// Si le lien n'apporte pas de gain, on le retire
				else
					this.retirerLien(l);
			}
		}
		return lienChoisi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chemins.AlgoPlusCourtChemin#plusCourtChemin(carte.Case)
	 */
	@Override
	public Chemin plusCourtChemin(Case caseDeDepart, Case caseDestination) throws AucunCheminPossible {

		if (!this.getRobot().appartientTerrainRobot(caseDestination.getNature()))
			throw new AucunCheminPossible("Case destination est une case impraticable " + caseDestination);

		this.initialiserStructureDeDonnees();

		// initialise toutes les cases à infini
		this.initialiserTempsParcours(caseDeDepart);

		// calcule tous les temps de parcours en fonction des natures des cases.
		this.initialiserTempsParcoursEntreCasesAdjacentes();

		// On itère tant que la case destination n'a pas été atteinte
		while (!this.casesVisitees.contains(caseDestination)) {

			Case caseDepart, caseArrivee;
			Lien lien = this.choisirLienProchaineIteration();

			// si aucun lien n'a été trouvé, alors aucun chemin n'existe pour atteindre la
			// case destination
			if (lien == null)
				throw new AucunCheminPossible("Aucun chemin n'a été trouvé pour la case " + caseDestination);

			this.retirerLien(lien);

			caseDepart = lien.getCaseDepart();
			caseArrivee = lien.getCaseDestination();

			// Ajout de la case d'arrivée du lien aux cases visitées. Peu importe si la case
			// a déjà été rajoutée
			// lors d'une précédente itération.
			this.casesVisitees.add(caseArrivee);
			this.tempsParcours.put(caseArrivee, this.tempsParcours.get(caseDepart) + lien.getPoids());
			this.precedents.put(caseArrivee, caseDepart);

		}

		return this.construireCheminOptimal(caseDestination);
	}

	/**
	 * Affiche le chemin optimal sous forme de suite de cases
	 * 
	 * @param caseDestination
	 */
	public void afficherCheminOptimal(Case caseDestination) {
		Case c = caseDestination;
		System.out.println("-------- CHEMIN OPTIMAL --------");
		while (this.precedents.get(c) != null) {
			System.out.println(c);
			c = this.precedents.get(c);
		}
	}

	/**
	 * Construit le chemin optimal de directions sous forme de pile. Le tableau des
	 * précédents doit être préalablement calculé.
	 * 
	 * @param caseDestination case destination du chemin
	 * @return le chemin optimal
	 */
	private Chemin construireCheminOptimal(Case caseDestination) {

		Case c = caseDestination;
		// System.err.println("Case destination : "+caseDestination);
		Chemin plusCourtChemin = new Chemin();

		// utile pour calculer la vitesse moyenne ainsi que le temps total
		int nombreCases = 1;

		double vitesseTotale = this.getRobot().getVitesse(c.getNature());
		double vitesseMoyenne;

		while (this.precedents.get(c) != null) {
			for (Direction dir : Direction.values()) {
				if (this.getCarte().getVoisin(this.precedents.get(c), dir) == c) {

					// ajout de la vitesse de la case précédente
					vitesseTotale += this.getRobot().getVitesse(this.precedents.get(c).getNature());

					nombreCases += 1;
					plusCourtChemin.pushDirection(dir);
					break;
				}
			}
			c = this.precedents.get(c);
		}

		vitesseMoyenne = vitesseTotale / (double) nombreCases * (1000.0 / 3600.0);
		plusCourtChemin.setVitesseMoyenne(vitesseMoyenne);
		plusCourtChemin.setTempsParcours((nombreCases * this.getCarte().getTailleCases() / vitesseMoyenne));

		return plusCourtChemin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chemins.AlgoPlusCourtChemin#plusCourtCheminVersPointEau(carte.Case)
	 */
	@Override
	public Chemin plusCourtCheminVersPointEau(Case caseDeDepart) throws AucunCheminPossible {

		Case caseEauTrouvee = null;
		this.initialiserStructureDeDonnees();

		// initialise toutes les cases à infini
		this.initialiserTempsParcours(caseDeDepart);

		// calcule tous les temps de parcours en fonction des natures des cases.
		this.initialiserTempsParcoursEntreCasesAdjacentes();

		// On itère tant que la case destination n'a pas été atteinte
		while (caseEauTrouvee == null) {

			Case caseDepart, caseArrivee;
			Lien lien = this.choisirLienProchaineIteration();

			// si aucun lien n'a été trouvé, alors aucun chemin n'existe pour atteindre la
			// case destination
			if (lien == null)
				throw new AucunCheminPossible("Aucun chemin n'a été trouvé pour rejoindre une case d'eau");

			this.retirerLien(lien);

			caseDepart = lien.getCaseDepart();
			caseArrivee = lien.getCaseDestination();

			if (this.getRobot().estBienPlacePourRemplissage(caseArrivee, this.getCarte())) {
				caseEauTrouvee = caseArrivee;
			}

			// Ajout de la case d'arrivée du lien aux cases visitées. Peu importe si la case
			// a déjà été rajoutée
			// lors d'une précédente itération.
			this.casesVisitees.add(caseArrivee);
			this.tempsParcours.put(caseArrivee, this.tempsParcours.get(caseDepart) + lien.getPoids());
			this.precedents.put(caseArrivee, caseDepart);

		}

		return this.construireCheminOptimal(caseEauTrouvee);
	}

}
