package io;

import carte.Carte;
import robots.Robot;

import java.util.HashSet;


public class DonneesSimulation{

    private Carte carte;
    private HashSet<Robot> robots;
    //TODO
    
    public DonneesSimulation(){
        this.robots = new HashSet<Robot>();
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

}
