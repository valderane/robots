package evenements;
import java.awt.Color;

import java.util.ArrayList;
import carte.Carte;
import carte.Case;
import carte.Incendie;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;
import io.DonneesSimulation;
import robots.Robot;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;

/**
 * Représente le plateau de jeu
 * 
 * @author 
 *
 */
public class Plateau2 implements Simulable{

    /** L'interface graphique associée */
    private GUISimulator gui;	

    private DonneesSimulation donneesSimu;
    
    private Simulateur simulateur;
    
    /** Constantes **/
    
    private final int TAILLE_CASE = 50;
    
    private final Color COULEUR_ERREUR = Color.PINK;  
    
    private final Color COULEUR_DRONE = Color.decode("#99ff99");  
    private final Color COULEUR_ROBOT_A_ROUES = Color.decode("#cc66ff");
    private final Color COULEUR_ROBOT_A_CHENILLES = Color.decode("#33cccc");
    private final Color COULEUR_ROBOT_A_PATTES = Color.decode("#ccc000");
    
    private final Color COULEUR_TERRAIN_LIBRE = Color.decode("#ffffff");
    private final Color COULEUR_HABITAT = Color.decode("#ffff99");
    private final Color COULEUR_ROCHE = Color.decode("#996633");
    private final Color COULEUR_FORET = Color.decode("#009933");
    private final Color COULEUR_EAU = Color.decode("#0099ff");
    private final Color COULEUR_BORDURE = Color.BLACK;
    
    private final Color COULEUR_INCENDIE = Color.decode("#ff0000");
    
    // 1 seconde
    private int pas_simu_sec;
    
    /**
     * Crée un Invader et le dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur de l'invader
     */
    public Plateau2(GUISimulator gui, DonneesSimulation donneesSimu) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donneesSimu = donneesSimu;
        this.simulateur = new Simulateur();
        //=1000ms
        this.pas_simu_sec= 1;
;        draw();
    }
    
    @Override
    public void next() {
    	//System.out.println("DICO EVT: "+ this.simulateur.getEvenements());
    	//voir comment optimiser
    	//supprimer les clefs à chaque fois pour ne pas tout reparcourir? 
        Long date_courante = this.simulateur.getDateSimulation();
    	Long prochaine_date = this.simulateur.getDateSimulation() + pas_simu_sec;
    	//System.out.println(date_courante + "  " + prochaine_date);

    	for(Long ddate :this.simulateur.getEvenements().keySet())
        {
        	if (ddate >= date_courante){
        		if( ddate < prochaine_date){
        			ArrayList<Evenement> evt_a_executer = this.simulateur.getEvenements().get(ddate);
        			for (Evenement evt : evt_a_executer)
        			{
        				evt.execute(prochaine_date);
        				System.out.println("execute_evt");
        			}
        			
        		} //on a dépassé date courante.
        			else {
        				break;
        			}
        	}
        }
        draw();
    	this.simulateur.setDateSimulation(prochaine_date);
    }
    //TODO 
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
          int x, y;
          
          Color color;
          Case c;
          
          for(int lig = 0 ; lig < nbLigne ; lig++) {
          	y = lig * this.TAILLE_CASE + TAILLE_CASE/2;
          	
          	for(int col = 0 ; col < nbCol ; col++) {
          		x = col * TAILLE_CASE + TAILLE_CASE/2;
          		c = carte.getCase(lig, col);
          		
                  switch (c.getNature()) {
                      case EAU:
                      	color = this.COULEUR_EAU;
                          break;
                      case FORET:
                      	color = this.COULEUR_FORET;
                          break;
                      case ROCHE:
                      	color = this.COULEUR_ROCHE;
                          break;
                      case TERRAIN_LIBRE:
                      	color = this.COULEUR_TERRAIN_LIBRE;
                          break;
                      case HABITAT:
                      	color = this.COULEUR_HABITAT;
                          break;
                          
                      default: 
                      	color = this.COULEUR_ERREUR;
                          break;
                  }
          		
                  //x et y sont le centre du rectangle
                  gui.addGraphicalElement(new Rectangle(x, y, this.COULEUR_BORDURE, color, TAILLE_CASE));
          	}
          }   	
    }
    
    private void dessinerRobots(Robot[] robots) {
      
        Color color;
        int x, y;
        
        for (robots.Robot robot : robots) {
        	 if(robot instanceof Drone)
        		 color = this.COULEUR_DRONE;
        	 else if(robot instanceof RobotAChenilles)
        		 color = this.COULEUR_ROBOT_A_CHENILLES;
        	 else if(robot instanceof RobotAPattes)
        		 color = this.COULEUR_ROBOT_A_PATTES;
        	 else if(robot instanceof RobotARoues)
        		 color = this.COULEUR_ROBOT_A_ROUES;
        	 else
        		 color = this.COULEUR_ERREUR;
        	 
        	 x = robot.getPosition().getColonne() * TAILLE_CASE + TAILLE_CASE/2;
        	 y = robot.getPosition().getLigne() * TAILLE_CASE + TAILLE_CASE/2;

        	 gui.addGraphicalElement(new Oval(x, y, color, color, TAILLE_CASE/2));

        }     
  }
    
    private void dessinerIncendies(Incendie[] incendies) {
        
    	int x, y;
        
        for (Incendie incendie : incendies) {
        	      	 
        	 x = incendie.getPosition().getColonne() * TAILLE_CASE + TAILLE_CASE/2;
        	 y = incendie.getPosition().getLigne() * TAILLE_CASE + TAILLE_CASE/2;

        	 gui.addGraphicalElement(new Oval(x, y, this.COULEUR_INCENDIE, this.COULEUR_INCENDIE, TAILLE_CASE/4));
        }     
  }

	public Simulateur getSimulateur() {
		return simulateur;
	}

	public int getTAILLE_CASE() {
		return TAILLE_CASE;
	}

	public int getPas_simu_sec() {
		return pas_simu_sec;
	}
}
