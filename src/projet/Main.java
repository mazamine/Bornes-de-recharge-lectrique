package projet;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String [] args) {
		CA communaute = new CA();
		Scanner scanner=new Scanner(System.in);
		int reponse;
	    String cheminFichier;
		if (args.length != 1) {
			System.err.println("Veuillez spécifier le chemin du fichier en argument.");
			System.out.println("Entrez le chemain du fichier :");
			cheminFichier = scanner.nextLine();
	    }else{
			cheminFichier = args[0];
		}
	    File fichier = new File(cheminFichier);
	    try {
	    	communaute.lectureFichier(fichier);
	    } catch (IOException e) {
			System.out.println(e.getMessage());
		}
	  
	    do { 
			menu1();
			reponse = lectureEntier(scanner,"Votre choix: ");
			switch(reponse) {
			case 1: 
				do { 
					menu2();
					reponse = lectureEntier(scanner,"Votre choix: ");
					switch(reponse) {
					case 1:
						try {
							String villeSource,villeCible;
							villeSource=lectureString(scanner, "La ville source est: ");
							villeCible=lectureString(scanner, "La ville cible est: ");
							Route route = new Route(new Ville(villeSource),new Ville(villeCible));
							communaute.ajouterRoute(route);
							break;
						} catch (InvalidInputException e ) {
							System.out.println(e.getMessage());
						}
					case 2:
						break;
					default: 
						System.out.println("Votre choix "+ reponse+ " n'est pas valide");
						break;
					}
				}while (reponse != 2);
				
				do {
					if(!communaute.accessibiliteVerifiee()) {
						communaute.initBorneDeRecharge();
					}
					communaute.afficherVillesAvecRecharge();
					menu3();
					reponse = lectureEntier(scanner,"Votre choix: ");
					switch(reponse) {
					case 1:
						try {
							String ville = lectureString(scanner,"La ville est: ");
							communaute.ajouterBorneDeRecharge(new Ville(ville));
							break;
						} catch (InvalidInputException e ) {
							System.out.println(e.getMessage());
						}
					case 2:
						try {
							String ville = lectureString(scanner,"La ville est: ");
							communaute.supprimerBorneRecharge(new Ville(ville));
							break;
						} catch (InvalidInputException e ) {
							System.out.println(e.getMessage());
						}
					case 3: 
						break;
					default: 
						System.out.println("Votre choix "+ reponse+ " n'est pas valide");
						break;
					}
				}while(reponse != 3);
				break;
			case 2: 
				if(!communaute.accessibiliteVerifiee()) {
					communaute.initBorneDeRecharge();
				}
				communaute.optimiserBornes();
				System.out.println("avant l'amélioration");
				communaute.afficherVillesAvecRecharge();
				
				communaute.ameliorerOptimisation();
				System.out.println("après l'amélioration");
				communaute.afficherVillesAvecRecharge();
				break;
			case 3: 
				System.out.println("Saisir le chemin vers le fichier où la solution doit etre enregistrée: ");
				String chemin = scanner.next();
				try {
					communaute.ecritureDansFichier(chemin);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4: 
				break;
			default: 
				System.out.println("Votre choix "+ reponse+ " n'est pas valide");
				break;
			}
		} while (reponse !=4);
	 }		
				
	
/* cette méthode vérifie si ce qui a été saisi est bien un entier 
 * sinon lève une exception 
 * et demande à l'utilisateur de resaisir un entier 
 */
	private static int lectureEntier(Scanner scanner, String message) {
		int res = 0;
		boolean lectureValide = false;
		while(!lectureValide) {
			try {
				System.out.println(message);
				res=scanner.nextInt();
				lectureValide=true;
			} catch (InputMismatchException e) {
				System.out.println("Il faut taper un nombre entier");
				scanner.nextLine();				
			}
		}
		return res;
	}
/* cette méthode vérifie si ce qui a été saisi est bien un String 
 * sinon lève une exception 
 * et demande à l'utilisateur de resaisir une chaine de caractère 
 */
	private static String lectureString(Scanner scanner, String message) throws InputMismatchException {
		String res =" " ;
		boolean lectureValide = false;
		while(!lectureValide) {
			try {
				System.out.println(message);
				res=scanner.next();
				if (!res.matches("[a-zA-Z]+")) {
	                throw new InputMismatchException("Il faut taper une chaine de caractère contenant le nom de la ville");
	            }
	            lectureValide = true;
			} catch (InputMismatchException e) {
				System.out.println("Il faut taper une chaine de caractère contenant le nom de la ville");
				scanner.nextLine();				
			}
		}
		return res;
	}

	private static void menu1() {
		System.out.println("Tapez: ");
		System.out.println("1: Résoudre manuellement;");
		System.out.println("2: Résoudre automatiquement;");
		System.out.println("3: Sauvegarder;");
		System.out.println("4: Fin.");
	}
	private static void menu2() {
		System.out.println("Tapez: ");
		System.out.println("1: Ajouter une route ");
		System.out.println("2: Fin");
	}
	
	private static void menu3() {
		System.out.println("Tapez: ");
		System.out.println("1: Ajouter une zone de recharge ");
		System.out.println("2: Retirer une zone de recharge ");	
		System.out.println("3: Fin");
	}
	
}
