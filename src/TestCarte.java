import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.zip.DataFormatException;
import java.awt.Color;
import java.awt.Robot;

import carte.Carte;
import carte.Case;
import carte.NatureTerrain;
import io.LecteurDonnees;
import robots.Drone;
import io.DonneesSimulation;

import gui.GUISimulator;
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
        
    }
    
    private void dessinerCases(Carte carte) {
    	  int nbLigne = carte.getNbLignes();
          int nbCol = carte.getNbColonnes();
          
          Color color;
          Case c;
          
          for(int lig = 0 ; lig < nbLigne ; lig++) {
          	y = lig * this.TAILLE_CASE;
          	
          	for(int col = 0 ; col < nbCol ; col++) {
          		x = col * TAILLE_CASE;
          		c = carte.getCase(lig, col);
          		
                  switch (c.getNature()) {
                      case EAU:
                      	color = Color.BLUE;
                          break;
                      case FORET:
                      	color = Color.GREEN;
                          break;
                      case ROCHE:
                      	color = Color.GRAY;
                          break;
                      case TERRAIN_LIBRE:
                      	color = Color.RED;
                          break;
                      case HABITAT:
                      	color = Color.YELLOW;
                          break;
                          
                      default: 
                      	color = Color.BLACK;
                          break;
                  }
          		
                  gui.addGraphicalElement(new Rectangle(x, y, color, color, TAILLE_CASE));
          	}
          }   	
    }
    
    private void dessinerRobots(HashSet<robots.Robot> hashSet) {
      
        Color color;
        Case c;
        
        for (robots.Robot robot : hashSet) {
        	 if(robot instanceof Drone)
        		System.out.println("Drooooone");  
        	 //TODO
        }
  }
}
