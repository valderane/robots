package evenements;


import carte.Incendie;
import exceptions.exceptions_remplissage.PlusDeau;
import exceptions.exceptions_remplissage.PlusDeauEtReservoirVide;
import exceptions.exceptions_remplissage.ReservoirVide;
import io.DonneesSimulation;
import robots.Robot;



/**
 * @author emmanuel
 *Calcule la quantité d'eau à déverser en fonction du robot et du laps de temps entre la date de l'évenement et la
 *date du prochain next.
 */
public class EvenementDeverser extends Evenement {
	
	/**
	 * 
	 */
	private Robot robot;
	/**
	 * 
	 */
	private DonneesSimulation data;
	
	private long prochaineDate;
	/**
	 * @param dateEvenement
	 * @param robot
	 * @param donneSimu
	 * @param prochaineDate date du prochain next
	 */
	public EvenementDeverser(long dateEvenement, Robot robot, DonneesSimulation donneSimu, long prochaineDate ) {
		super(dateEvenement);
		this.robot = robot;
		this.data = donneSimu;
		this.prochaineDate = prochaineDate;
			}

	@Override
	public int execute(long prochaineDatee) {
		
		//détermine les exceptions levés. (1,2,3) = (ReservoirVide, PlusDeau, PlusDeauEtReservoirVide)
		int flag = 0;
		//en secondes
		double laps_temps = prochaineDate - this.date;
		int volumeAVider = (int)(robot.getCapaciteViderLitre() * laps_temps) / robot.getCapaciteViderSec();
		
		int volumeVide = robot.deverserEau(volumeAVider);
		/*Le robot n'a plus d'eau -> a prévenir. et on récupère la qqté d'eau versé*/
		if (volumeVide != volumeAVider)
			System.out.println("reservoir robot: " + robot.getReservoirEau() +  " VIDE ");
			flag = 1;
		

		Incendie incendieEnCours = data.getIncendies(robot.getPosition())[0];
		try {
					incendieEnCours.setIntensite(incendieEnCours.getIntensite() - volumeVide);
					//System.out.println("volume vidé: " + volumeVide +"a la date: "+ this.getDate());
					//System.out.println("incendie apres_vidage: "+ incendieEnCours);
		
		}
		/*incendie n'a plus d'intensite*/
		catch (PlusDeau e) {
			System.out.println(e);
			data.supprimerIncendie(robot.getPosition(), incendieEnCours);
			/*Reservoir vide + Plus d'eau*/
			if (flag == 1) {
				flag = 3;
			}
			else {
				flag = 2;
			}
		//Renvoie pas d'exception si flag = 0	

		}
		return flag;
		}
		/*on baisse intensité incendie #TODO*/
	}

