import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Direction;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import gui.GUISimulator;
import gui.Plateau;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.Robot;

public class TestCarteOut {
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("RENTRER NOM CARTE EN PARAMETRES");
		}
		else {
		try {
			//System.err.println(args[0]);
			
			 DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);
			 Robot mrobot = data.getRobots(data.getCarte().getCase(3, 3))[0];
			 System.out.println(args[0]+" de taille: " + data.getCarte());
			 while (true) {
					 
						try {
							mrobot.deplacer(Direction.SUD, data.getCarte());
						} catch (RobotSorsCarte e) {
							 System.err.println( mrobot + " sort de la carte. Est en: " + mrobot.getPosition()  + " et essaye d'aller au SUD");
							 break;
						} catch (ProchaineCaseMauvaiseNature e) {
							 System.err.println(e);
							 break;
						}
			 }
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		}
		}
	}

}
