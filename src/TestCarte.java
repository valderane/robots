import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import gui.GUISimulator;
import gui.Plateau;
import io.DonneesSimulation;
import io.LecteurDonnees;

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
			Plateau plateau = new Plateau(gui, data, args[0]);


		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
		}
	}

}
