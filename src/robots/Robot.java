package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public abstract class Robot{


    protected Case position;

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
    
    public abstract double getVitesse(NatureTerrain nat);

    public abstract void deverserEau(int vol);
    
    public abstract void remplirReservoir();

}
