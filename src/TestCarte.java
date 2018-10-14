import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.zip.DataFormatException;
import java.awt.Color;
import java.awt.Robot;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import carte.NatureTerrain;
import io.LecteurDonnees;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;
import io.DonneesSimulation;

import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

public class TestCarte{

    public static void main(String[] args) {
        int nlig = 5;
        int ncol = 6;
        int tailleCases = 1000;
        Carte c = new Carte(nlig, ncol, tailleCases);
        
        try {
            DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/carteSujet.map");
            
            Carte carte = data.getCarte();
            System.out.println("Carte : nlignes = "+carte.getNbLignes()+", ncol = "
            		+ " "+carte.getNbColonnes()+", taille de cases = "+carte.getTailleCases());
            System.out.println(carte.getCase(2, 3));
            
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Plateau plateau = new Plateau(gui, data);
        
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }

}

class Plateau implements Simulable{

    /** L'interface graphique associée */
    private GUISimulator gui;	

    /** Abcisse courante de l'invader (bord gauche) */
    private int x;

    /** Ordonnée courante de l'invader (bord supérieur) */
    private int y;
    
    private final int TAILLE_CASE = 100;

    private DonneesSimulation donneesSimu;
    
    /** Constantes **/
    public static final Color COULEUR_ERREUR = Color.PINK;  
    
    public static final Color COULEUR_DRONE = Color.decode("#99ff99");  
    public static final Color COULEUR_ROBOT_A_ROUES = Color.decode("#cc66ff");
    public static final Color COULEUR_ROBOT_A_CHENILLES = Color.decode("#33cccc");
    public static final Color COULEUR_ROBOT_A_PATTES = Color.decode("#ccc000");
    
    public static final Color COULEUR_TERRAIN_LIBRE = Color.decode("#ffffff");
    public static final Color COULEUR_HABITAT = Color.decode("#ffff99");
    public static final Color COULEUR_ROCHE = Color.decode("#996633");
    public static final Color COULEUR_FORET = Color.decode("#009933");
    public static final Color COULEUR_EAU = Color.decode("#0099ff");
    public static final Color COULEUR_BORDURE = Color.BLACK;
    
    public static final Color COULEUR_INCENDIE = Color.decode("#ff0000");




    /**
     * Crée un Invader et le dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur de l'invader
     */
    public Plateau(GUISimulator gui, DonneesSimulation donneesSimu) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donneesSimu = donneesSimu;
        
        draw();
    }


    @Override
    public void next() {
    	//TODO
    }

    @Override
    public void restart() {
        draw();
    }


    /**
     * Dessine le plateau
     */
    private void draw() {
        gui.reset();	// clear the window
        
        Carte carte = this.donneesSimu.getCarte();
        
        this.dessinerCases(carte);
        this.dessinerRobots(this.donneesSimu.getRobots());
        this.dessinerIncendies(this.donneesSimu.getIncendies());
        
    }
    
    private void dessinerCases(Carte carte) {
    	  int nbLigne = carte.getNbLignes();
          int nbCol = carte.getNbColonnes();
          
          Color color;
          Case c;
          
          for(int lig = 0 ; lig < nbLigne ; lig++) {
          	y = lig * this.TAILLE_CASE + TAILLE_CASE/2;
          	
          	for(int col = 0 ; col < nbCol ; col++) {
          		x = col * TAILLE_CASE + TAILLE_CASE/2;
          		c = carte.getCase(lig, col);
          		
                  switch (c.getNature()) {
                      case EAU:
                      	color = Plateau.COULEUR_EAU;
                          break;
                      case FORET:
                      	color = Plateau.COULEUR_FORET;
                          break;
                      case ROCHE:
                      	color = Plateau.COULEUR_ROCHE;
                          break;
                      case TERRAIN_LIBRE:
                      	color = Plateau.COULEUR_TERRAIN_LIBRE;
                          break;
                      case HABITAT:
                      	color = Plateau.COULEUR_HABITAT;
                          break;
                          
                      default: 
                      	color = Plateau.COULEUR_ERREUR;
                          break;
                  }
          		
                  //x et y sont le centre du rectangle
                  gui.addGraphicalElement(new Rectangle(x, y, Plateau.COULEUR_BORDURE, color, TAILLE_CASE));
          	}
          }   	
    }
    
    private void dessinerRobots(HashSet<robots.Robot> robots) {
      
        Color color;
        Case c;
        int x, y;
        
        for (robots.Robot robot : robots) {
        	 if(robot instanceof Drone)
        		 color = Plateau.COULEUR_DRONE;
        	 else if(robot instanceof RobotAChenilles)
        		 color = Plateau.COULEUR_ROBOT_A_CHENILLES;
        	 else if(robot instanceof RobotAPattes)
        		 color = Plateau.COULEUR_ROBOT_A_PATTES;
        	 else if(robot instanceof RobotARoues)
        		 color = Plateau.COULEUR_ROBOT_A_ROUES;
        	 else
        		 color = Plateau.COULEUR_ERREUR;
        	 
        	 x = robot.getPosition().getColonne() * TAILLE_CASE + TAILLE_CASE/2;
        	 y = robot.getPosition().getLigne() * TAILLE_CASE + TAILLE_CASE/2;

        	 gui.addGraphicalElement(new Oval(x, y, color, color, TAILLE_CASE/2));

        }     
  }
    
    private void dessinerIncendies(HashSet<Incendie> incendies) {
        
    	int x, y;
        
        for (Incendie incendie : incendies) {
        	      	 
        	 x = incendie.getPosition().getColonne() * TAILLE_CASE + TAILLE_CASE/2;
        	 y = incendie.getPosition().getLigne() * TAILLE_CASE + TAILLE_CASE/2;

        	 gui.addGraphicalElement(new Oval(x, y, Plateau.COULEUR_INCENDIE, Plateau.COULEUR_INCENDIE, TAILLE_CASE/4));
        }     
  }
}
