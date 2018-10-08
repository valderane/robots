import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Carte;
import io.LecteurDonnees;
import io.DonneesSimulation;

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
        
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }

}
