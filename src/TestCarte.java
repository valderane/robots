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
import evenements.Evenement_deverser;
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
import chemins.Djikstra;

public class TestCarte{

    public static void main(String[] args) {
    
        try {
        	
        	//lecture des données dans le fichier carteSujet.map
            DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/carteSujet.map");
         // DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/desertOfDeath-20x20.map");
         // DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/spiralOfMadness-50x50.map");

                    
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Plateau2 plateau = new Plateau2(gui, data);
            
            
            Djikstra testPlusCourtChemin = new Djikstra(data.getCarte(), data.getRobots()[0]);
            
            
            Long dateun = (long) 50;
            Long datedeux = (long) 50;
            Long datetrois = (long) 150;
            Long datequatre= (long) 200;
            Long datecinq= (long) 320;


            
        	robots.Robot mrobot = data.getRobots(data.getCarte().getCase(6,5))[0];
        	

        	/*Evt deplacer*/
            Evenement_deplacer evt = new Evenement_deplacer(dateun, mrobot, data.getCarte(), Direction.NORD);
            Evenement_deplacer evt2 = new Evenement_deplacer(datedeux, mrobot, data.getCarte(), Direction.NORD);
            Evenement_deplacer evt3 = new Evenement_deplacer(datetrois, mrobot, data.getCarte(), Direction.NORD);

            /*Evt intervention*/
             Evenement_deverser evt_deverserun = new Evenement_deverser(datequatre, mrobot, data);
             Evenement_deverser evt_deverserdeux = new Evenement_deverser(datequatre, mrobot, data);
             Evenement_deverser evt_deversertrois = new Evenement_deverser(datequatre, mrobot, data);
             Evenement_deverser evt_deverserquatre = new Evenement_deverser(datecinq, mrobot, data);
             Evenement_deverser evt_deversercinq = new Evenement_deverser(datecinq, mrobot, data);



           // Evenement_deverser evt_deverserdeux = new Evenement_deverser(datequatre, mrobot, data);

        
            
            plateau.getSimulateur().ajouteEvenement(evt);
            plateau.getSimulateur().ajouteEvenement(evt2);
            plateau.getSimulateur().ajouteEvenement(evt3);
            
            plateau.getSimulateur().ajouteEvenement(evt_deverserun);
            plateau.getSimulateur().ajouteEvenement(evt_deverserdeux);

            plateau.getSimulateur().ajouteEvenement(evt_deversertrois);

            plateau.getSimulateur().ajouteEvenement(evt_deverserquatre);
            plateau.getSimulateur().ajouteEvenement(evt_deversercinq);


            //plateau.getSimulateur().ajouteEvenement(evt_deverserdeux);


        
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }

}

