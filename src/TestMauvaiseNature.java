import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Direction;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.Robot;

public class TestMauvaiseNature {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("RENTRER NOM CARTE EN PARAMETRES");
		}
		else {
		try {
			System.err.println(args[0]);
			
			 DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);
			 Robot mrobot = data.getRobots(data.getCarte().getCase(6, 5))[0];
			 while (true) {
					 
						try {
							mrobot.deplacer(Direction.OUEST, data.getCarte());
						} catch (RobotSorsCarte e) {
							 System.err.println( mrobot + "sort de la carte.");
							 break;
						} catch (ProchaineCaseMauvaiseNature e) {
							 System.err.println(mrobot + "ne supporte pas cette nature de terrain");
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
