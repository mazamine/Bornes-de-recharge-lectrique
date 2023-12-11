package projet;

// Libraries
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		// pour le scanner
		Community community = new Community();

		// l'emplacement du fichier (pttr je vais le changer en .env )
		String fileName = "/workspaces/Projet_PAA/src/h.txt";

		// Il test si il y a un problème avec le fichier .txt
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			// si il ne trouve aucun problème, il l'execute ligne par ligne
			while ((line = br.readLine()) != null) {
				executeCommand(line, community);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fichier non trouver!\nVeuillez entrer les villes manuellement");

			// Sinon il demande d'entrer les données manuellement
			inputCities(community, scanner);
		}

		System.out.println("Bienvenue dans le programme de configuration des zones de recharge.");
		System.out.println("souhaitez vous modifier ce programme? y/n");
		if(scanner.nextLine().equals("y")){
			inputCities(community, scanner);
		}

		System.out.println("Comment voulez vous résoudre le problème?");
		System.out.println("1. résoudre manuellement;");
		System.out.println("2. résoudre automatiquement;");


		System.out.println("\nConfiguration initiale avec une zone de recharge dans chaque ville :");
        community.displayRechargeZones();

		System.out.println("\nRésolution naïve avec l'algorithme  d'approximation(naïf) :");
		community.naiveApproximation(100);

        System.out.println("\nRésolution automatique avec l'algorithme d'approximation (un peu moins naïf ) :");
        community.approximateSolution(4); // Vous pouvez ajuster le nombre d'itérations selon vos besoins

        // Affichage du résultat
        System.out.println("\nConfiguration finale :");
        community.displayRechargeZones();

		// c'est pour les modifier manuellement
		inputZonesDeRecharge(community, scanner);
		
		scanner.close();
		
		//////////////////////////////////////////////////////////////////
		System.out.println("Voulez vous sauvgarder cette solution?y/n");
		//////////////////////////////////////////////////////////////////

		System.out.println("Merci d'utiliser le programme.");
		System.exit(0);
	}

	private static void inputZonesDeRecharge(Community community, Scanner scanner){
		int choice = 0;
		while (true) {
			// Menu
			System.out.println("\nMenu de gestion des zones de recharge :");
			System.out.println("1. Ajouter une zone de recharge");
			System.out.println("2. Retirer une zone de recharge");
			System.out.println("3. Terminer");

			// Handle InputMismatchException
			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
			} catch (InputMismatchException e) {
				System.out.println("Choix non valide. Veuillez entrer un nombre entier.");
				scanner.nextLine(); // Clear the input buffer
				continue; // Continue the loop to allow the user to enter a valid choice
			}

			// J'ai utilisé if et pas switch pour pouvoir utiliser break pour sortir de la boucle
			if (choice == 1) {
				System.out.print("Veuillez entrer la ville où ajouter une zone de recharge : ");
				String city = scanner.nextLine();
				community.addZoneDeRecharge(city);
			} else if (choice == 2) {
				System.out.print("Veuillez entrer la ville où retirer une zone de recharge : ");
				String city = scanner.nextLine();
				community.deleteZoneDeRecharge(city);
			} else if (choice == 3) {
					if (!community.isAccessibilityPreserved()) {
						System.err.println("Erreur : le Systèm de Zones de recharge de ces ville n'est pas correcte");
						System.out.println("Voulez vous arreter comme meme le programme?\n1. oui\n2. non");
						choice = scanner.nextInt();
						if (choice == 1) {
							break;
						} else if (choice == 2) {
							inputCities(community, scanner);
							inputZonesDeRecharge(community, scanner);
							return;
						}
					} else {
						break;
					}
				
				break;
			} else {
				System.out.println("Choix non valide. Veuillez réessayer.");
			}

			System.out.println("\nConfiguration actuelle :");
			community.displayRechargeZones();
		}

	}

	private static void executeCommand(String command, Community community) {
		// Analyser la commande et extraire le nom de la fonction et les paramètres
		String[] parts = command.split("\\(");
		if (parts.length >= 2) {
			String functionName = parts[0].trim();
			String[] params = parts[1].replaceAll("\\)", "").split(",");
			for (int i = 0; i < params.length; i++) {
				params[i] = params[i].trim();
			}

			// Exécuter la fonction en fonction du nom et des paramètres
			switch (functionName) {
			case "route":
				if (params.length == 2) {
					community.addRoute(params[0], params[1]);
				} else {
					System.out.println("Erreur : La fonction 'route' nécessite deux paramètres.");
				}
				break;
			case "ville":
				if (params.length == 1) {
					community.addVille(params[0]);
				} else {
					System.out.println("Erreur : La fonction 'ville' nécessite un paramètre.");
				}
				break;
			case "recharge":
				if (params.length == 1) {
					community.addZoneDeRecharge(params[0]);
				} else {
					System.out.println("Erreur : La fonction 'zone' nécessite un paramètre.");
				}
				break;
			// Add more cases for other functions if needed
			default:
				System.out.println("Erreur : Fonction inconnue - " + functionName);
			}
		} else {
			System.out.println("Erreur : Syntaxe de commande incorrecte - " + command);
		}
	}

	private static void inputCities(Community community, Scanner scanner) {

		int choice;
		String cityName;

		while (true) {
			System.out.println("\nMenu principal :");
			System.out.println("1. Ajouter une Ville");
			System.out.println("2. supprimer une ville");
			System.out.println("3. Terminer la configuration");

			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
			} catch (InputMismatchException e) {
				System.out.println("Entree invalide. Veuillez entrer un nombre entier.");
				scanner.nextLine(); // Clear the input buffer
				continue;
			}
			if (choice == 1) {
				System.out.print("Entrez son nom : ");
				cityName = scanner.nextLine();
				community.addVille(cityName);
			}else if (choice == 2) {
				System.out.print("Entrez son nom : ");
				cityName = scanner.nextLine();
				community.removeVille(cityName);
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Choix non valide !");
			}

		}

		choice = 0;

		while (true) {
			System.out.println("\nMenu principal :");
			System.out.println("1. Ajouter une route");
			System.out.println("2. Supprimer une route");
			System.out.println("3. Terminer la configuration");

			// Handle InputMismatchException
			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
			} catch (InputMismatchException e) {
				System.out.println("Choix non valide. Veuillez entrer un nombre entier.");
				scanner.nextLine(); // Clear the input buffer
				continue; // Continue the loop to allow the user to enter a valid choice
			}

			if (choice == 1) {
				System.out.print("Veuillez entrer les villes entre lesquelles ajouter une route (par exemple, A B) : ");
				String input = scanner.nextLine();
				String[] cities = input.split(" ");
				if (cities.length == 2 && !cities[0].equals(cities[1])) {
					community.addRoute(cities[0], cities[1]);
				} else {
					System.out.println("Entrée invalide. Veuillez entrer deux villes différentes.");
				}
			} else if (choice == 2) {
				System.out.print("Veuillez entrer les villes entre lesquelles supprimer une route (par exemple, A B) : ");
				String input = scanner.nextLine();
				String[] cities = input.split(" ");
				if (cities.length == 2 && !cities[0].equals(cities[1])) {
					community.removeRoute(cities[0], cities[1]);
				} else {
					System.out.println("Entrée invalide. Veuillez entrer deux villes différentes.");
				}
			} else if (choice == 3) {
				break;
			}
		}
	}
}