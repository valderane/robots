package evenements;


import carte.Incendie;
import exceptions.exceptions_remplissage.PlusDeau;
import io.DonneesSimulation;
import robots.Robot;

//Le robot deverse son eau pour éteindre l'incendie à la case courante
//le robot doit connaître le pas de temps du next.
//ie le robot vide une certaine qqté du reservoir en un temps donné.
//exemple: si le pas est une sec: on doit avoir:

//100 litres en 	5 sec 
// ?  litres en 	pas de temps.

//implementer deverser_eau(?) 

//-> l'evenement apelera la méthode déverser
// on deverse une certaine qqté fixe a chq dois que la methode est appelé (en fct du pas de temps)
// le nombre d'evenement a effectué dépendra du temps.
// next -> + 1sec -> 

//on passe un volume a vider. (selon le pas de temps)


//pb: si le next c'est de 1 sec en 1 sec.
/* si RobotA vide 10l en 0.5sec:
 * next1= il arrive case B
 * next2: 1sec plus tard. donc il a vidé pdt une sec. ie on doit faire vider 20l  */


//case 1: 1sec next1
//case 2: 2sec next2

//1sec3

//deverse une certaine qqté d'eau en fonction du pas de temps.


//1 choix: on passe une qqté d'eau a déverser:
// -avantage: 	  méthode commune a tous les robots.  (si volumme == 0 -> volume = 0)
// inconvénient:  les vérifications se font dans EVENEMENT qui en fonction du robot le fait.
// évenement doit connaître le pas ( lors de la création)

//2 choix: chaque robot vide son réservoir selon ca capacité -> dois connaître le pas de temps 


public class EvenementDeverser extends Evenement {
	private Robot robot;
		private DonneesSimulation data;
	
	//prochciane date = date_courante(simulateur) + pas_simulateur
	
	//date_evenement + pas 
	
	public EvenementDeverser(long dateEvenement, Robot robot, /*Carte carte*/ DonneesSimulation donneSimu ) {
		super(dateEvenement);
		this.robot = robot;
		this.data = donneSimu;
			}

	@Override
	public void execute(long prochaineDate) {
		long difference_temps = prochaineDate - this.date;
		int volumeAVider = (int)(robot.getCapaciteViderLitre() * difference_temps) / robot.getCapaciteViderSec();
		//System.out.println("vol_a_vider:" + volumeAVider+ " difference_temps "+ difference_temps);

		int volumeVide = robot.deverserEau(volumeAVider);
		/*Le robot n'a plus d'eau -> a prévenir*/
		if (volumeVide != volumeAVider)
			System.out.println("reservoir robot: " + robot.getReservoirEau() +  " plus d'eau");
		
		

		Incendie incendieEnCours = data.getIncendies(robot.getPosition())[0];
		try {
					incendieEnCours.setIntensite(incendieEnCours.getIntensite() - volumeVide);
					System.out.println("volume vidé: " + volumeVide +"a la date: "+ this.getDate());
					System.out.println("incendie apres_vidage: "+ incendieEnCours);
		
		}
		/*incendie n'a plus d'intensite*/
		catch (PlusDeau e) {
			System.out.println(e);
			data.supprimerIncendie(robot.getPosition(), incendieEnCours);
		
		
			
		}
		/*on baisse intensité incendie #TODO*/
	}
}
