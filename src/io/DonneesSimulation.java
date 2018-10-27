package io;

import carte.Carte;
import carte.Incendie;
import robots.Robot;

import java.util.HashSet;


public class DonneesSimulation{

    private Carte carte;
    private Robot[] robots;
    private Incendie[] incendies;
    
    public DonneesSimulation(){

    }

    public Carte getCarte() {
    	return this.carte;
    }
    
    public void setCarte(Carte c) {
    	this.carte = c;
    }
    
    void initialiserNombreRobots(int nbRobots) {
    	this.robots = new Robot[nbRobots];
    }
    
    public Robot[] getRobots(){
    	return this.robots;
    }
    
    public void setRobot(int numRobot, Robot r) {
    	this.robots[numRobot] = r;
    }
    
    void initialiserNombreIncendies(int nbIncendies) {
    	this.incendies = new Incendie[nbIncendies];
    }
    
    public Incendie[] getIncendies(){
    	return this.incendies;
    }
    
    public void setIncendie(int numIncendie, Incendie incendie) {
    	this.incendies[numIncendie] = incendie;
    }

}
