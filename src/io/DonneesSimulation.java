package io;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import evenements.Evenement;
import robots.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.zip.DataFormatException;


public class DonneesSimulation{

    private Carte carte;
    
	private HashMap<Case,HashSet<Robot>> robots;

	private HashMap<Case, HashSet<Incendie>> incendies;
    
    public DonneesSimulation(){
    	this.robots = new HashMap<Case, HashSet<Robot>>();
    	this.incendies = new HashMap<Case, HashSet<Incendie>>();
    }

    public Carte getCarte() {
    	return this.carte;
    }
    
    public void setCarte(Carte c) {
    	this.carte = c;
    }
    
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
    
    public Robot[] getRobots(Case c){
    	if(this.robots.containsKey(c))
    		return this.robots.get(c).toArray(new Robot[0]);
    	else
    		return new Robot[0];
    }
    
    public Robot[] getRobots() {
    	HashSet<Robot> resultat = new HashSet<Robot>();
    	for (HashSet<Robot> setRobot : this.robots.values()) {
    		resultat.addAll(setRobot);
    	}
    	return resultat.toArray(new Robot[0]);
    }
    
    
    public void ajouterIncendie(Incendie incendie) {
    	Case caseIncendie = incendie.getPosition();
	
		//Si il y a déjà un événement à cette date
    	System.out.println(caseIncendie);
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
    public Incendie[] getIncendies(Case c){
    	if(this.incendies.containsKey(c))
    		return this.incendies.get(c).toArray(new Incendie[0]);
    	else
    		return new Incendie[0];
    }
    
    public Incendie[] getIncendies() {
    	HashSet<Incendie> resultat = new HashSet<Incendie>();
    	for (HashSet<Incendie> setIncendie : this.incendies.values()) {
    		resultat.addAll(setIncendie);
    	}
    	return resultat.toArray(new Incendie[0]);
    }
    
    public void supprimerIncendie(Case c, Incendie incendie) throws IndexOutOfBoundsException{
    	if(!this.incendies.containsKey(c))
    		throw new IndexOutOfBoundsException("La case n'existe pas");
    	
    	this.incendies.get(c).remove(incendie);
    }
    

}
