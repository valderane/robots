import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.Incendie;
import evenements.Evenement;
import evenements.Evenement_deplacer;
import evenements.Simulateur;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;

public class TestCarte{

    public static void main(String[] args) {
    
        try {
        	
        	//lecture des données dans le fichier carteSujet.map
            DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/carteSujet.map");
                    
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Plateau2 plateau = new Plateau2(gui, data);
            
            HashMap<Case, Incendie> dico_test = new HashMap<Case, Incendie>();
            dico_test.put(data.getCarte().getCase(7, 0), data.getIncendies()[0]);
            dico_test.put(data.getCarte().getCase(7, 1), data.getIncendies()[1]);
            System.out.println( dico_test.get(data.getCarte().getCase(7, 0)) );
            
            Long dateun = (long) 500;
            Long datedeux = (long) 700;
            Long datetrois = (long) 1900;
        	robots.Robot mrobot = data.getRobots()[1];
//        	int i=0;
//        	for (robots.Robot rob: data.getRobots()){
//        		if (rob instanceof RobotARoues)
//        		{
//        			mrobot = rob;
//        			break;
//        		}
//        		
//        	}
            Evenement_deplacer evt = new Evenement_deplacer(dateun, mrobot, data.getCarte(), Direction.NORD);
            Evenement_deplacer evt2 = new Evenement_deplacer(datedeux, mrobot, data.getCarte(), Direction.NORD);
            Evenement_deplacer evt3 = new Evenement_deplacer(datetrois, mrobot, data.getCarte(), Direction.NORD);

            plateau.getSimulateur().ajouteEvenement(evt);
            plateau.getSimulateur().ajouteEvenement(evt2);
            plateau.getSimulateur().ajouteEvenement(evt3);


        
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }

}

