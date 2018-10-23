package evenements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import io.DonneesSimulation;
import evenements.Evenement;

public class Simulateur {

	private TreeMap<Long,ArrayList<Evenement>> evenements;
	private long dateSimulation;
	
	public Simulateur() {
		this.evenements = new TreeMap<Long, ArrayList<Evenement>>();
		this.dateSimulation = 0L;
	}
	
	
	public void ajouteEvenement(Evenement e) {
		long date = e.getDate();
		ArrayList<Evenement> evenementsPourCetteDate;
	
		//Si il y a déjà un événement à cette date
		if(this.evenements.containsKey(date)) {
			this.evenements.get(date).add(e);
		}
		else 
		{
			evenementsPourCetteDate  = new ArrayList<Evenement>();
			evenementsPourCetteDate.add(e);
			this.evenements.put(date, evenementsPourCetteDate);	
		}	
	}
	
	
	public void incrementeDate() {
		this.dateSimulation++;
	}
	
	public boolean simulationTerminee() {
		//vérifie si il reste des clés supérieures à la date de simulation actuelle
		if(this.evenements.higherKey(this.dateSimulation) == null)
			return true;
		return false;
	}
	

}
