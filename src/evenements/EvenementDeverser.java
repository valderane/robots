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
	
	public EvenementDeverser(long date_evenement, Robot robot, /*Carte carte*/ DonneesSimulation donne_simu ) {
		super(date_evenement);
		this.robot = robot;
		this.data = donne_simu;
			}

	@Override
	public void execute(long prochaine_date) {
		long difference_temps = prochaine_date - this.date;
		int volume_a_vider = (int)(robot.getCapaciteViderLitre() * difference_temps) / robot.getCapaciteViderSec();
		//System.out.println("vol_a_vider:" + volume_a_vider+ " difference_temps "+ difference_temps);

		int volume_vide = robot.deverserEau(volume_a_vider);
		/*Le robot n'a plus d'eau -> a prévenir*/
		if (volume_vide != volume_a_vider)
			System.out.println("reservoir robot: " + robot.getReservoir_eau() +  " plus d'eau");
		
		

		Incendie incendie_en_cours = data.getIncendies(robot.getPosition())[0];
		try {
					incendie_en_cours.setIntensite(incendie_en_cours.getIntensite() - volume_vide);
					System.out.println("volume vidé: " + volume_vide +"a la date: "+ this.getDate());
					System.out.println("incendie apres_vidage: "+ incendie_en_cours);
		
		}
		/*incendie n'a plus d'intensite*/
		catch (PlusDeau e) {
			System.out.println(e);
			data.supprimerIncendie(robot.getPosition(), incendie_en_cours);
		
		
			
		}
		/*on baisse intensité incendie #TODO*/
	}
}
