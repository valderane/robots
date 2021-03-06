package io;

import carte.NatureTerrain;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;

import java.awt.Color;
import java.awt.Robot;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import carte.Carte;
import carte.Incendie;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées, ou transférées dans des données de simulation.

 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees)
            throws FileNotFoundException, DataFormatException {
            System.out.println("\n == Lecture du fichier" + fichierDonnees);
            LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
            lecteur.lireCarte();
            lecteur.lireIncendies();
            lecteur.lireRobots();
            scanner.close();
            System.out.println("\n == Lecture terminee");
    }

    /**
     * Lit le contenu d'un fichier de donnée et stocke les informations
     * dans un objet DonneesSimulation.
     *
     * @param fichierDonnees nom du fichier à lire
     * @return //TODO
     *
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees)
            throws FileNotFoundException, DataFormatException {
            System.out.println("\n == Lecture du fichier" + fichierDonnees);
            LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);

            DonneesSimulation donnees = new DonneesSimulation();
            lecteur.ecrireCarte(donnees);
            lecteur.ecrireIncendies(donnees);
            lecteur.ecrireRobots(donnees);
            scanner.close();
            System.out.println("\n == Lecture terminee");

            return donnees;
    } 




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
            throws FileNotFoundException {
            scanner = new Scanner(new File(fichierDonnees));
            scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }


    /**
     * Lit et stocke les donnees de la carte dans une class DonneesSimulation.
     * @throws ExceptionFormatDonnees
     */
    private void ecrireCarte(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
        	
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            
            Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);
            donnees.setCarte(carte);
           
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    ecrireCase(lig, col, donnees);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }


    /**
     * Lit et affiche les donnees d'une case.
     */
    private void lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

      
    }

    /**
     * Lit et stocke les donnees d'une case dans une classe DonneesSimulation.
     */
    private void ecrireCase(int lig, int col, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
        	Carte carte = donnees.getCarte();
        	
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            
            carte.setCase(lig, col, nature);
            
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        //System.out.println();
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            //System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }

    /**
     * Lit et stocke les donnees des incendies dans une class DonneesSimulation.
     */
    private void ecrireIncendies(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
           // System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                ecrireIncendie(i, donnees);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    private void ecrireIncendie(int i, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
       // System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            Incendie incendie = new Incendie(intensite, donnees.getCarte().getCase(lig, col));    
            donnees.ajouterIncendie(incendie);
            //System.out.println("position = (" + lig + "," + col
            //        + ");\t intensite = " + intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            //System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et stocke les donnees des robots dans une class DonneesSimulation.
     */
    private void ecrireRobots(DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();

           // System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                ecrireRobot(i, donnees);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /**
     * Lit et stocke les donnees du robot suivant dans une class DonneesSimulation.
     * @param i
     */
    private void ecrireRobot(int numRobot, DonneesSimulation donnees) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int vitesse = -1;
            String type = scanner.next();
            
            Carte carte = donnees.getCarte();
            
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            if (s != null) {
            	vitesse = Integer.parseInt(s);
            }
            verifieLigneTerminee();
            try {          
            switch (type) {     
                case "DRONE":
                	Drone drone = new Drone(carte.getCase(lig, col));
                	if(vitesse != -1)
                		drone.setVitesse(vitesse);
                	donnees.ajouterRobot(drone);
                    break;
                case "ROUES":
                	RobotARoues roues = new RobotARoues(carte.getCase(lig, col));
                	if(vitesse != -1)
                		roues.setVitesse(vitesse);
                	donnees.ajouterRobot(roues);
                	break;
                case "PATTES":
                	RobotAPattes pattes = new RobotAPattes(carte.getCase(lig, col));
                    donnees.ajouterRobot(pattes);
                	break;
                case "CHENILLES":
                	RobotAChenilles chenille = new RobotAChenilles(carte.getCase(lig, col));
                	if(vitesse != -1)
                		chenille.setVitesse(vitesse);
                    donnees.ajouterRobot(chenille);
                	break;
                    
                default: 
                    break;
            }
            }catch (DataFormatException e) {
            	System.err.println(e);
            }
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
