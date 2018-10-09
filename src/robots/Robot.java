package robots;

import carte.Case;
import carte.NatureTerrain;

public abstract class Robot{


    protected Case position;
    protected int vitesse;

    public Robot(Case c){
    		this.position = c;
    }

    public Case getPosition(){
    	return this.position;
    }

    public void setPosition(Case c){
    	this.position = c;
    	//TODO
    }
    public void setVitesse(int vitesse) {
    	this.vitesse = vitesse;
    }

    public abstract double getVitesse(NatureTerrain nat);

    public abstract void deverserEau(int vol);
    
    public abstract void remplirReservoir();

}
