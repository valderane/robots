package chemins;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import carte.Carte;
import carte.Case;
import carte.Direction;
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
	protected double[][] distances;
	
	/**
	 * Case précédente utilisée pour atteindre chaque case avec le plus court chemin
	 */
	protected Case[][] precedents;
	
	/**
	 * Contient les liens non utilisés entre les cases au sens d'un graphe de Djikstra.
	 * Chaque lien représente le temps de parcours moyen pour passer d'une case à l'autre
	 */
	protected HashMap<Case, Vector<Lien>> distancesEntreCases;
	
	/**
	 * Cases visitées. Pour l'algorithme de Djikstra, seules ces cases peuvent être
	 * choisies comme point de départ d'une itération.
	 */
	protected Case[] casesVisitees; 
	
	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public Djikstra(Carte carte, Robot robot) {
		super(carte, robot);
		this.distancesEntreCases = new HashMap<Case, Vector<Lien> >();
		
		this.distances = new double[this.carte.getNbLignes()][this.carte.getNbColonnes()];
		this.precedents = new Case[this.carte.getNbLignes()][this.carte.getNbColonnes()];
		
		this.initialiserDistancesEntreCases(this.carte.getCase(2, 2));
		
		//initialisation des distances à infini
		for(double[] ligne : this.distances)
			Arrays.fill(ligne, this.INFINI);
	}
	
	
	/**
	 * @param caseDepart Case d'arrivée
	 * @return 
	 */
	private void initialiserDistancesEntreCases(Case caseArrivee) {
		Case caseActuelle, caseVoisine;
		for(int ligne = 0 ; ligne < this.carte.getNbLignes() ; ++ligne)
			for(int col = 0 ; col < this.carte.getNbColonnes() ; ++col) {
				caseActuelle = this.carte.getCase(ligne, col);
				
				//vérification des voisins pour chaque direction
				for(Direction dir : Direction.values()) {
					caseVoisine = this.carte.getVoisin(caseActuelle, dir);
					
					//Dans le cas où un voisin existe, on ajoute un lien
					if(caseVoisine != null) {
						this.ajouterLien(new Lien(caseActuelle, caseVoisine, dir,
								this.robot.getTempsParcours(caseActuelle.getNature(), caseActuelle.getNature())) );
						System.out.println("ajout case voisine : "+caseVoisine+" direction : "+dir);
					}
				}
			}
	}
	
	private void ajouterLien(Lien l) {
		Vector<Lien> liens;
		
		//si des liens existent déjà depuis cette case, on ajoute le nouveau lien à la suite des liens existant
		if (this.distancesEntreCases.containsKey(l.getCaseDepart()))
			liens = this.distancesEntreCases.get(l.getCaseDepart());
		else {
			liens = new Vector<Lien>();
			this.distancesEntreCases.put(l.getCaseDepart(), liens);
		}
		liens.add(l);
	}
	
	
	/* (non-Javadoc)
	 * @see chemins.AlgoPlusCourtChemin#plusCourtChemin(carte.Case)
	 */
	@Override
	public Stack<Direction> plusCourtChemin(Case c) {
		
		return null;
	}
	
}
