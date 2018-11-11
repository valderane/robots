package gui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import evenements.Evenement;
import evenements.Simulateur;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.Robot;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;
import strategies.ChefPompier;

/**
 * Représente le plateau de jeu
 * 
 * @author Equipe 23
 *
 */

public class Plateau implements Simulable {

	/** L'interface graphique associée */
	private GUISimulator gui;

	/**
	 * 
	 */
	private DonneesSimulation donneesSimu;

	/**
	 * 
	 */
	private Simulateur simulateur;

	/**
	 * 
	 */
	private ChefPompier chefPompier;

	/** Constantes **/

	private int tailleCasePlateau;

	private final Color COULEUR_ERREUR = Color.PINK;
	
	/**Nature Cases**/
	private final String IMAGE_EAU = "images/bleu.jpeg";
	private final String IMAGE_FORET = "images/tree.jpg";
	private final String IMAGE_ROCHE = "images/rocher.png";
	private final String IMAGE_TERRAIN_LIBRE = "images/beige.jpg";
	private final String IMAGE_HABITAT = "images/habitat.jpg";
	 
	/**ROBOTS:**/
	private final String IMAGE_DRONE = "images/drone.png";
	private final String IMAGE_CHENILLE = "images/robot_chenille.png";
	private final String IMAGE_PATTE = "images/robot_a_pattes.png";
	private final String IMAGE_ROUE = "images/robot_roues.png";
			

	private final Color COULEUR_INCENDIE = Color.decode("#ff0000");

	private long pasSimulationEnSecondes;
	
	private String nomCarte;
	
	private int animation = 0;


	/**
	 * @param gui
	 * @param donneesSimu
	 * @param nomCarte Carte affiché par l'interface graphique.
	 * Le plateau permet de lier les données à l'interface graphique qui représente le carte donnée en paramètre.
	 */
	/**
	 * @param gui
	 * @param donneesSimu
	 * @param nomCarte
	 */
	public Plateau(GUISimulator gui, DonneesSimulation donneesSimu, String nomCarte) {
		this.gui = gui;
		gui.setSimulable(this); // association a la gui!
		this.donneesSimu = donneesSimu;
		this.simulateur = new Simulateur();
		this.initialiserPasSimulation();
		this.initialiserTailleCasePlateau();
		this.nomCarte = nomCarte;
		
		draw();
		
		this.prechargerImages("images/");

		this.donneesSimu.initialiserGestionnairesDeplacementsRobots(this.simulateur, this.pasSimulationEnSecondes);
		this.donneesSimu.initialiserGestionnairesReservoirsRobots(this.simulateur, this.pasSimulationEnSecondes);

		this.chefPompier = new ChefPompier(this.donneesSimu, this.simulateur);

	}
	
	/** Précharge toutes les images contenues dans un dossier
	 * @param nomDossier Nom du dossier contenant toutes les images. Attention, le nom doit finir par "/"
	 * 
	 */
	private void prechargerImages(String nomDossier) {
		ImageElement img;
		File[] files = new File(nomDossier).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	img = new ImageElement(0, 0, nomDossier+file.getName(), 1 , 1, null);
				
				gui.addGraphicalElement(img);
		    }
		}
	}

	/**
	 * Initialise le pas de simulation tel que le robot le plus rapide fasse un
	 * déplacement à chaque pas
	 */
	private void initialiserPasSimulation() {

		double vitesseMaxRobot = -1;
		int tailleCaseEnMetres = this.donneesSimu.getCarte().getTailleCases();

		for (Robot robot : this.donneesSimu.getRobots())
			if (robot.getVitesseMaximale() > vitesseMaxRobot)
				vitesseMaxRobot = robot.getVitesseMaximale();

		/* transformation de la vitesse en m/s avec la division par 3.6 */
		this.pasSimulationEnSecondes = (int) (tailleCaseEnMetres / (vitesseMaxRobot / 3.6));
		
	}

	/**
	 * Initialise la taille d'une case afin que tout le plateau rentre
	 */
	private void initialiserTailleCasePlateau() {
		if (this.gui.getPanelHeight() < this.gui.getPanelWidth())
			this.tailleCasePlateau = this.gui.getPanelHeight() / this.donneesSimu.getCarte().getNbLignes();
		else
			this.tailleCasePlateau = this.gui.getPanelWidth() / this.donneesSimu.getCarte().getNbLignes();

	}
	/**
	 *méthode permetant d'avancer de le temps courant de pasSimulationEnSeconde, et d'éxecuter tous les évenements correspondants. (non éxécutés et de date antérieure). 
	 * 
	 * **/
	@Override
	public void next() {
		this.chefPompier.itererPasSimu();

		Long dateCourante = this.simulateur.getDateSimulation();
		Long prochaineDate = this.simulateur.getDateSimulation() + this.pasSimulationEnSecondes;

		for (Long ddate : this.simulateur.getEvenements().keySet()) {
			if (ddate >= dateCourante) {
				if (ddate < prochaineDate) {
					ArrayList<Evenement> evtAExecuter = this.simulateur.getEvenements().get(ddate);
					for (Evenement evt : evtAExecuter) {
						evt.execute();
					}

				} // on a dépassé date courante.
				else {
					break;
				}
			}
		}

		draw();
		this.simulateur.setDateSimulation(prochaineDate);
	}

	/**
	 * méthode permettant de recommencer la simulation.
	 * **/
	@Override
	public void restart() {
		try {
			this.donneesSimu =  LecteurDonnees.creeDonnees(this.nomCarte);
		} catch (FileNotFoundException | DataFormatException e) {
			e.printStackTrace();
		}
		this.simulateur = new Simulateur();
		this.initialiserPasSimulation();
		this.initialiserTailleCasePlateau();
		
		draw();


		this.donneesSimu.initialiserGestionnairesDeplacementsRobots(this.simulateur, this.pasSimulationEnSecondes);
		this.donneesSimu.initialiserGestionnairesReservoirsRobots(this.simulateur, this.pasSimulationEnSecondes);

		this.chefPompier = new ChefPompier(this.donneesSimu, this.simulateur);
	}

	/**
	 * Dessine le plateau
	 */
	private void draw() {
		gui.reset(); // clear the window

		Carte carte = this.donneesSimu.getCarte();

		this.dessinerCases(carte);
		this.dessinerRobots(this.donneesSimu.getRobots());
		this.dessinerIncendies(this.donneesSimu.getIncendies());

	}

	private void dessinerCases(Carte carte) {
		int nbLigne = carte.getNbLignes();
		int nbCol = carte.getNbColonnes();
		int x, y;
		
		ImageElement image;		

		Color color;
		Case c;

		for (int lig = 0; lig < nbLigne; lig++) {
			y = lig * this.tailleCasePlateau;

			for (int col = 0; col < nbCol; col++) {
				x = col * this.tailleCasePlateau ;
				c = carte.getCase(lig, col);

				switch (c.getNature()) {
				case EAU:
					image = new ImageElement(x, y, IMAGE_EAU, this.tailleCasePlateau,
							this.tailleCasePlateau, null);					
					break;
				case FORET:
					image = new ImageElement(x, y, IMAGE_FORET, this.tailleCasePlateau,
							this.tailleCasePlateau, null);		
					break;
				case ROCHE:
					image = new ImageElement(x, y, IMAGE_ROCHE, this.tailleCasePlateau,
							this.tailleCasePlateau, null);			
					break;
				case TERRAIN_LIBRE:
					image = new ImageElement(x, y, IMAGE_TERRAIN_LIBRE, this.tailleCasePlateau,
							this.tailleCasePlateau, null);			
					break;
				case HABITAT:
					image = new ImageElement(x, y, IMAGE_HABITAT, this.tailleCasePlateau,
							this.tailleCasePlateau, null);								break;

				default:
					image = null;
					break;
				}

				// x et y sont le centre du rectangle
				gui.addGraphicalElement(image);
			}
		}
	}

	private void dessinerRobots(Robot[] robots) {

		ImageElement image;
		int x, y;

		/*
		 * Ici, nous n'utilisons pas une méthode robot.getCouleur, car le robot n'a pas
		 * connaissance d'un plateau de jeu. On utilise de ce fait instanceof
		 */
		for (robots.Robot robot : robots) {
			x = robot.getPosition().getColonne() * this.tailleCasePlateau;
			y = robot.getPosition().getLigne() * this.tailleCasePlateau;
			int jauge;

			image = null;

			if (robot instanceof Drone)
				image = new ImageElement(x, y, IMAGE_DRONE, this.tailleCasePlateau, this.tailleCasePlateau,
						null);
			else if (robot instanceof RobotAChenilles)
				image = new ImageElement(x, y, IMAGE_CHENILLE, this.tailleCasePlateau,
						this.tailleCasePlateau, null);
			else if (robot instanceof RobotAPattes)
				image = new ImageElement(x, y, IMAGE_PATTE, this.tailleCasePlateau,
						this.tailleCasePlateau, null);
			else if (robot instanceof RobotARoues)
				image = new ImageElement(x, y, IMAGE_ROUE, this.tailleCasePlateau, this.tailleCasePlateau,
						null);
			else
				System.err.println("robot non identifié, et donc aucun affichage disponible");

			if (image != null) {
				gui.addGraphicalElement(image);
				
				jauge = robot.getReservoirEau() * (this.tailleCasePlateau - 20) / robot.getVolumeRemplissage(); ;
				gui.addGraphicalElement(new Rectangle(x + this.tailleCasePlateau/2, y, Color.WHITE, Color.BLUE, jauge, 5 ));
			}
				

		}
	}

	private void dessinerIncendies(Incendie[] incendies) {

		int x, y;
		ImageElement image;
		int jaugeFeu;
		
		int indiceImage = this.animation%5 + 1; //indice de l'image a afficher

		for (Incendie incendie : incendies) {

		
			jaugeFeu = incendie.getIntensite() * this.tailleCasePlateau / incendie.getIntensiteInit() ;
			x = incendie.getPosition().getColonne() * this.tailleCasePlateau + (this.tailleCasePlateau - jaugeFeu) / 2;
			y = incendie.getPosition().getLigne() * this.tailleCasePlateau + (this.tailleCasePlateau - jaugeFeu) / 2;
			image = new ImageElement(x, y, "images/feu"+indiceImage+".png", jaugeFeu , jaugeFeu,
					null);
			
			gui.addGraphicalElement(image); // dessine l'incendie			
			
		
		}
		
		this.animation++;
		if(this.animation >= 5) {
			this.animation = 0;
		}
		
	}

	public Simulateur getSimulateur() {
		return simulateur;
	}

	public long getPasSimulation() {
		return this.pasSimulationEnSecondes;
	}
}
