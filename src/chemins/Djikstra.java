package chemins;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

import carte.Carte;
import carte.Case;
import carte.Direction;
import exceptions.exceptionsChemins.AucunCheminPossible;
import robots.Robot;

/**
 * @author 
 *
 */
/**
 * @author jicquelv
 *
 */
/**
 * @author jicquelv
 *
 */
public class Djikstra extends AlgoPlusCourtChemin{
	
	/**
	 * Constante utilisée pour marquer les liens non parcourus
	 */
	private final int INFINI = -1;
	
	/**
	 * Distances minimale pour atteindre chaque case depuis la case de départ 
	 */
	protected HashMap<Case, Double> tempsParcours;
	
	/**
	 * Case précédente utilisée pour atteindre chaque case avec le plus court chemin
	 */
	protected HashMap<Case, Case> precedents;
	
	/**
	 * Contient les liens non utilisés entre les cases au sens d'un graphe de Djikstra.
	 * Chaque lien représente le temps de parcours moyen pour passer d'une case à l'autre
	 */
	protected HashMap<Case, Vector<Lien>> tempsParcoursEntreCasesAdjacentes;
	
	/**
	 * Cases visitées. Pour l'algorithme de Djikstra, seules ces cases peuvent être
	 * choisies comme point de départ d'une itération.
	 */
	protected HashSet<Case> casesVisitees; 
	
	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public Djikstra(Carte carte, Robot robot) {
		super(carte, robot);
		this.tempsParcoursEntreCasesAdjacentes = new HashMap<Case, Vector<Lien> >();
		this.casesVisitees = new HashSet<Case>();
		this.tempsParcours = new HashMap<Case, Double>();
		this.precedents = new HashMap<Case, Case>();
	}
	
	/**
	 * Initialise toutes les valeurs des cases à infini. La valeur de la case du robot est à 0
	 */
	private void initialiserTempsParcours() {
		for(int ligne = 0 ; ligne < this.carte.getNbLignes() ; ++ligne)
			for(int col = 0 ; col < this.carte.getNbColonnes() ; ++col) {
				Case caseActuelle = this.carte.getCase(ligne, col);
				this.tempsParcours.put(caseActuelle, Double.POSITIVE_INFINITY);
			}
		
		this.tempsParcours.put(this.robot.getPosition(), 0.0);
		this.casesVisitees.add(this.robot.getPosition());
	}
	
	/**
	 * @return 
	 */
	private void initialiserTempsParcoursEntreCasesAdjacentes() {
		Case caseActuelle, caseVoisine;
		for(int ligne = 0 ; ligne < this.carte.getNbLignes() ; ++ligne)
			for(int col = 0 ; col < this.carte.getNbColonnes() ; ++col) {
				caseActuelle = this.carte.getCase(ligne, col);
				
				//vérification des voisins pour chaque direction
				for(Direction dir : Direction.values()) {
					caseVoisine = this.carte.getVoisin(caseActuelle, dir);
					
					//Dans le cas où un voisin existe, on ajoute un lien
					if(caseVoisine != null) {
						try {
							this.ajouterLien(new Lien(caseActuelle, caseVoisine, dir,
								this.robot.getTempsParcours(caseActuelle.getNature(), caseVoisine.getNature(), this.carte.getTailleCases())) );
						//System.out.println("ajout case voisine : "+caseVoisine+" direction : "+dir);
						}catch(AucunCheminPossible event) {} //Aucun lien n'est créé si le robot ne peut pas passer d'une case à l'autre
					}
				}
			}
	}
	
	/**
	 * @param l
	 */
	private void ajouterLien(Lien l) {
		Vector<Lien> liens;
		
		//si des liens existent déjà depuis cette case, on ajoute le nouveau lien à la suite des liens existant
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
	 * @param l Lien à retirer
	 */
	private void retirerLien(Lien l) {
		this.tempsParcoursEntreCasesAdjacentes.get(l.getCaseDepart()).remove(l);
	}
	
	/**
	 * Affiche le temps parcouru pour passer d'une case à l'autre. Le parcours se fait toujours avec deux cases adjacentes
	 */
	public void afficherTempsParcours() {
		for(Case c : this.tempsParcoursEntreCasesAdjacentes.keySet()) {
			System.out.println("Case de départ "+c);
			for(Lien l : this.tempsParcoursEntreCasesAdjacentes.get(c) ) {
				System.out.println("Case arrivée : "+l.getCaseDestination()+", temps parcours : "+l.getPoids());
			}
		}
	}
	
	/**
	 * 
	 */
	private Lien choisirLienProchaineIteration() { 
		double tempsMin = Double.POSITIVE_INFINITY;
		Lien lienChoisi = null;
		for(Case c : this.casesVisitees) {
			for(Lien l : this.tempsParcoursEntreCasesAdjacentes.get(c)) {

				//on choisit le temps de parcours (lien + poids case départ) le plus faible
				if (tempsMin > this.tempsParcours.get(c) + l.getPoids()) {
					tempsMin = this.tempsParcours.get(c) + l.getPoids();
					lienChoisi = l;
				}
			}			
		}
		
		return lienChoisi;
	}
	
	
	/* (non-Javadoc)
	 * @see chemins.AlgoPlusCourtChemin#plusCourtChemin(carte.Case)
	 */
	@Override
	public Chemin plusCourtChemin(Case c) throws AucunCheminPossible {
		
		//initialise toutes les cases à infini
		this.initialiserTempsParcours();
		
		//calcule tous les temps de parcours en fonction des natures des cases.
		this.initialiserTempsParcoursEntreCasesAdjacentes();

		//On itère tant que la case destination n'a pas été atteinte
		while(!this.casesVisitees.contains(c)) {
			Case caseDepart, caseArrivee;
			Lien lien = this.choisirLienProchaineIteration();
			
			//si aucun lien n'a été trouvé, alors aucun chemin n'existe pour atteindre la case destination
			if(lien == null)
				throw new AucunCheminPossible("Aucun chemin n'a été trouvé pour la case "+c);
			
			this.retirerLien(lien);
			
			caseDepart = lien.getCaseDepart();
			caseArrivee = lien.getCaseDestination();
			
			//Ajout de la case d'arrivée du lien aux cases visitées. Peu importe si la case a déjà été rajoutée
			//lors d'une précédente itération.
			this.casesVisitees.add(caseArrivee);
			
			//changement de la valeur et du précédent de la case d'arrivée si la nouvelle valeur est inférieure
			if(this.tempsParcours.get(caseDepart) + lien.getPoids() < this.tempsParcours.get(caseArrivee)){
				this.tempsParcours.put(caseArrivee, this.tempsParcours.get(caseDepart) + lien.getPoids());
				this.precedents.put(caseArrivee, caseDepart);
			}			
		}
		
		return this.construireCheminOptimal(c);
	}
	
	/**
	 * @param caseDestination
	 */
	public void afficherCheminOptimal(Case caseDestination) {
		Case c = caseDestination;
		System.out.println("-------- CHEMIN OPTIMAL --------");
		while(this.precedents.get(c) != null)
		{
			System.out.println(c);
			c = this.precedents.get(c);			
		}
	}
	
	
	/** 
	 * Construit le chemin optimal de directions sous forme de pile. Le tableau des précédents doit
	 * être préalablement calculé.
	 * 
	 * @param caseDestination
	 * @return
	 */
	private Chemin construireCheminOptimal(Case caseDestination) {
		Case c = caseDestination;
		Chemin plusCourtChemin = new Chemin();
		
		//utile pour calculer la vitesse moyenne ainsi que le temps total
		int nombreCases = 1;
		
		double vitesseTotale = this.robot.getVitesse(c.getNature());
		double vitesseMoyenne;
		
		while(this.precedents.get(c) != null)
		{			
			for(Direction dir : Direction.values()) {
				if(this.carte.getVoisin(this.precedents.get(c), dir) == c) {
					
					//ajout de la vitesse de la case précédente
					vitesseTotale += this.robot.getVitesse(this.precedents.get(c).getNature());
					
					nombreCases +=1;
					plusCourtChemin.pushDirection(dir);
					break;
				}		
			}
			c = this.precedents.get(c);	
		}
		System.out.println("Nombre de cases : "+nombreCases);
		vitesseMoyenne = vitesseTotale/ (double) nombreCases * (1000.0 / 3600.0);
		plusCourtChemin.setVitesseMoyenne(vitesseMoyenne);
		plusCourtChemin.setTempsParcours((nombreCases * this.carte.getTailleCases() / vitesseMoyenne));
		
		return plusCourtChemin;
	}
	
}
