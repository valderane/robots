package io;

import carte.Carte;
import carte.Incendie;
import robots.Robot;

import java.util.HashSet;


public class DonneesSimulation{

    private Carte carte;
    private HashSet<Robot> robots;
    private HashSet<Incendie> incendies;
    
    public DonneesSimulation(){
        this.robots = new HashSet<Robot>();
        this.incendies = new HashSet<Incendie>();

        //TODO
    }
    
    public Carte getCarte() {
    	return this.carte;
    }
    
    public void setCarte(Carte c) {
    	this.carte = c;
    }
    
    public HashSet<Robot> getRobots(){
    	return this.robots;
    }
    
    public void addRobot(Robot r) {
    	this.robots.add(r);
    }
    
    public HashSet<Incendie> getIncendies(){
    	return this.incendies;
    }
    
    public void addIncendie(Incendie incendie) {
    	this.incendies.add(incendie);
    }

}
