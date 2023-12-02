package projet;

import java.io.*;
import java.util.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Bienvenue dans le programme de configuration des zones de recharge.");
				/*
		 * int numberOfCities = 0;
		 * 
		 * // Handle InputMismatchException // Handle InputMismatchException and
		 * validate the input boolean validInput = false; while (!validInput) { try {
		 * System.out.
		 * print("Veuillez entrer le nombre de villes (inférieur ou égale à 26) : ");
		 * numberOfCities = scanner.nextInt();
		 * 
		 * // Validate the input if (numberOfCities > 0 && numberOfCities <= 26) {
		 * validInput = true; } else { System.out.
		 * println("Entree invalide. Veuillez entrer un nombre entre 1 et 26."); } }
		 * catch (InputMismatchException e) {
		 * System.out.println("Entree invalide. Veuillez entrer un nombre entier.");
		 * scanner.nextLine(); // Clear the input buffer } } scanner.nextLine(); //
		 * Consume the newline character Community community = new
		 * Community(numberOfCities);
		 */

		Community community = new Community();
		
		String fileName = "/workspaces/Projet_PAA/src/h.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				executeCommand(line, community);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		int choice = 0;
		String cityName;
/* 
		while (true) {
			System.out.println("\nMenu principal :");
			System.out.println("1. Ajouter une Ville");
			System.out.println("2. Terminer la configuration");

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
			} else if (choice == 2) {

				break;
			} else {
				System.out.println("Choix non valide !");
			}

		}

		choice = 0;

		while (true) {
			System.out.println("\nMenu principal :");
			System.out.println("1. Ajouter une route");
			System.out.println("2. Terminer la configuration");

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
			}
			if (choice == 2) {
				break;
			}
		}
*/

		System.out.println("\nConfiguration initiale avec une zone de recharge dans chaque ville :");

		optimizeChargingZones(community.cities, community.routes);
		community.displayRechargeZones();

		while (true) {
			System.out.println("\nMenu de gestion des zones de recharge :");
			System.out.println("1. Ajouter une zone de recharge");
			System.out.println("2. Retirer une zone de recharge");
			System.out.println("3. Terminer");

			choice = 0;

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
				System.out.print("Veuillez entrer la ville où ajouter une zone de recharge : ");
				String city = scanner.nextLine();
				community.addRechargeZone(city);
			} else if (choice == 2) {
				System.out.print("Veuillez entrer la ville où retirer une zone de recharge : ");
				String city = scanner.nextLine();
				community.deleteRechargeZone(city);
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Choix non valide. Veuillez réessayer.");
			}

			System.out.println("\nConfiguration actuelle :");
			community.displayRechargeZones();
		}

    
        // Afficher le résultat
        /*for (int i = 0; i < community.getCities().size(); i++){
			System.out.println("")
		}*/

		scanner.close();
		System.out.println("Merci d'utiliser le programme.");
		System.exit(0);
	}

	public static void optimizeChargingZones(List<Ville> cities, List<Route> roads) {
        for (Ville city : cities) {
            if (!city.hasZoneDeRecharge()) {
                Set<Ville> neighbors = findNeighbors(city, roads);
                if (!canChargeInNeighbor(city, neighbors)) {
                    city.setZoneDeRecharge(true);
                    for (Ville neighbor : neighbors) {
                        if (neighbor.hasZoneDeRecharge()) {
                            neighbor.setZoneDeRecharge(false); ;
                        }
                    }
                }
            }
        }
    }

    private static Set<Ville> findNeighbors(Ville city, List<Route> roads) {
        Set<Ville> neighbors = new HashSet<>();
        for (Route road : roads) {
            if (road.getCity1() == city) {
                neighbors.add(road.getCity2());
            } else if (road.getCity2() == city) {
                neighbors.add(road.getCity1());
            }
        }
        return neighbors;
    }

    private static boolean canChargeInNeighbor(Ville city, Set<Ville> neighbors) {
        for (Ville neighbor : neighbors) {
            if (neighbor.hasZoneDeRecharge()) {
                return true;
            }
        }
        return false;
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
					community.addRechargeZone(params[0]);
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
}