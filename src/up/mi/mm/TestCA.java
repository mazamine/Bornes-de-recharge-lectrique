package up.mi.mm.projet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestCA {
	
	public static void main(String [] args) {
		int nbVilles;
		int reponse;
		Scanner scanner=new Scanner(System.in);
		
		try {
			nbVilles = lectureEntier(scanner,"Saisir le nombre de villes: ");
			CA communaute = new CA(nbVilles);
			communaute.remplirMatrice();
			communaute.RemplirBornesDeRecharge();
			do { 
				menu1();
				reponse = lectureEntier(scanner,"Votre choix: ");
				switch(reponse) {
				case 1:
					try {
						int ville1,ville2;
						ville1=lectureEntier(scanner, "Le numero de la ville source: ");
						ville2=lectureEntier(scanner, "Le numero de la ville cible: ");
						communaute.ajouterArete(ville1, ville2);
						break;
					} catch (InvalidInputException e ) {
						System.out.println(e.getMessage());
					}
				case 2: 
					break;
				default: 
					System.out.println("Votre choix "+ reponse+ " n'est pas valide");
				}
			}while (reponse != 2);
			
			do {
				menu2();
				reponse = lectureEntier(scanner,"Votre choix: ");
				switch(reponse) {
				case 1:
					try {
						int ville = lectureEntier(scanner,"Le numéro de la ville est: ");
						communaute.ajouterBorneDeRecharge(ville);
						break;
					} catch (InvalidInputException e ) {
						System.out.println(e.getMessage());
					}
				case 2:
					try {
						int ville1 = lectureEntier(scanner,"Le numéro de la ville est: ");
						communaute.supprimerBorneRecharge(ville1);
						break;
					} catch (InvalidInputException e ) {
						System.out.println(e.getMessage());
					}
				case 3: 
					break;
				default: 
					System.out.println("Votre choix "+ reponse+ " n'est pas valide");
				}
			}while(reponse != 3);	
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		}
	}

		
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

	
	private static void menu1() {
		System.out.println("Tapez: ");
		System.out.println("1: Ajouter une route ");
		System.out.println("2: Fin");
	}
	
	private static void menu2() {
		System.out.println("Tapez: ");
		System.out.println("1: Ajouter une zone de recharge ");
		System.out.println("2: Retirer une zone de recharge ");	
		System.out.println("3: Fin");
	}
		
}
