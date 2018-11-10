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
import evenements.EvenementDeplacer;
import evenements.EvenementDeverser;
import evenements.EvenementRemplir;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import gui.GUISimulator;
import gui.Oval;
import gui.Plateau;
import gui.Rectangle;
import gui.Simulable;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.GestionnaireDeplacementRobot;
import robots.Drone;
import robots.Robot;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;
import strategies.ChefPompier;
import chemins.Chemin;
import chemins.Djikstra;

public class TestCarte {

	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("RENTRER NOM CARTE EN PARAMETRES");
		}
		else {
		try {
			System.err.println(args[0]);
			// lecture des données dans le fichier carteSujet.map
			//DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/carteSujet.map");
			// DonneesSimulation data = LecteurDonnees.creeDonnees("cartes/desertOfDeath-20x20");
			 DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);

			 //DonneesSimulation data =  LecteurDonnees.creeDonnees("cartes/spiralOfMadness-50x50.map");

			
			
			// crée la fenêtre graphique dans laquelle dessiner
			GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);

			// crée l'invader, en l'associant à la fenêtre graphique précédente
			Plateau plateau = new Plateau(gui, data, args[0]);

		
			/*
			 * }catch(AucunCheminPossible event) { System.err.println(event); }
			 */

		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
		}
	}

}
