package io;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import evenements.Evenement;
import evenements.Simulateur;
import robots.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.zip.DataFormatException;


/**
 * @author 
 *
 */
public class DonneesSimulation{

    private Carte carte;
    
	/**
	 * 
	 */
	private HashMap<Case,HashSet<Robot>> robots;

	/**
	 * 
	 */
	private HashMap<Case, HashSet<Incendie>> incendies;
    
    /**
     * 
     */
    public DonneesSimulation(){
    	this.robots = new HashMap<Case, HashSet<Robot>>();
    	this.incendies = new HashMap<Case, HashSet<Incendie>>();
    }

    /**
     * @return
     */
    public Carte getCarte() {
    	return this.carte;
    }
    
    /**
     * @param c
     */
    public void setCarte(Carte c) {
    	this.carte = c;
    }
    
    /**
     * @param r
     */
    public void ajouterRobot(Robot r) {
    	Case caseRobot = r.getPosition();
	
		//Si il y a déjà un événement à cette date
		if(this.robots.containsKey(caseRobot)) {
			this.robots.get(caseRobot).add(r);
		}
		else 
		{
			HashSet<Robot> robotsACetteCase = new HashSet<Robot>();
			robotsACetteCase.add(r);
			this.robots.put(caseRobot, robotsACetteCase);	
		}	
	}
    
    /**
     * @param c
     * @return
     */
    public Robot[] getRobots(Case c){
    	if(this.robots.containsKey(c))
    		return this.robots.get(c).toArray(new Robot[0]);
    	else
    		return new Robot[0];
    }
    
    /**
     * @return
     */
    public Robot[] getRobots() {
    	HashSet<Robot> resultat = new HashSet<Robot>();
    	for (HashSet<Robot> setRobot : this.robots.values()) {
    		resultat.addAll(setRobot);
    	}
    	return resultat.toArray(new Robot[0]);
    }
    
    
    /**
     * @param incendie
     */
    public void ajouterIncendie(Incendie incendie) {
    	Case caseIncendie = incendie.getPosition();
	
		//Si il y a déjà un événement à cette date
    	//System.out.println(caseIncendie);
		if(this.incendies.containsKey(caseIncendie)) {
			this.incendies.get(caseIncendie).add(incendie);
		}
		else 
		{
			HashSet<Incendie> incendiesACetteCase = new HashSet<Incendie>();
			incendiesACetteCase.add(incendie);
			this.incendies.put(caseIncendie, incendiesACetteCase);	
		}	
	}
    
    
    /**
     * @param c
     * @return
     */
    public Incendie[] getIncendies(Case c){
    	if(this.incendies.containsKey(c))
    		return this.incendies.get(c).toArray(new Incendie[0]);
    	else
    		return new Incendie[0];
    }
    
    /**
     * @return
     */
    public Incendie[] getIncendies() {
    	HashSet<Incendie> resultat = new HashSet<Incendie>();
    	for (HashSet<Incendie> setIncendie : this.incendies.values()) {
    		resultat.addAll(setIncendie);
    	}
    	return resultat.toArray(new Incendie[0]);
    }
    
    /**
     * @param c
     * @param incendie
     * @throws IndexOutOfBoundsException
     */
    public void supprimerIncendie(Incendie incendie) throws IndexOutOfBoundsException{
    	//System.out.println("Suppression de l'incendie");
    	if(!this.incendies.containsKey(incendie.getPosition()))
    		throw new IndexOutOfBoundsException("La case n'existe pas");
    	
    	this.incendies.get(incendie.getPosition()).remove(incendie);
    }
    
    
    public void initialiserGestionnairesDeplacementsRobots(Simulateur simulateur, double pasSimulation) {
    	for(Robot robot : this.getRobots())
    		robot.initialiserGestionnaireDeplacement(simulateur, pasSimulation, this.carte);
    }
    
    public void initialiserGestionnairesVidagesRobots(Simulateur simulateur, long pasSimulation) {
    	for(Robot robot : this.getRobots())
    		robot.initialiserGestionnaireVidage(simulateur, pasSimulation);
    }
    


}
