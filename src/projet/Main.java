package projet;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Bienvenue dans le programme de configuration des zones de recharge.");

		int numberOfCities = 0;

		// Handle InputMismatchException
		// Handle InputMismatchException and validate the input
		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.print("Veuillez entrer le nombre de villes (inférieur ou égale à 26) : ");
				numberOfCities = scanner.nextInt();

				// Validate the input
				if (numberOfCities > 0 && numberOfCities <= 26) {
					validInput = true;
				} else {
					System.out.println("Entree invalide. Veuillez entrer un nombre entre 1 et 26.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entree invalide. Veuillez entrer un nombre entier.");
				scanner.nextLine(); // Clear the input buffer
			}
		}
		scanner.nextLine(); // Consume the newline character

		Community community = new Community(numberOfCities);

		while (true) {
			System.out.println("\nMenu principal :");
			System.out.println("1. Ajouter une route");
			System.out.println("2. Terminer la configuration");

			int choice = 0;

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
				community.addRoute(cities[0], cities[1]);
			} else if (choice == 2) {
				break;
			} else {
				System.out.println("Choix non valide. Veuillez réessayer.");
			}
		}

		System.out.println("\nConfiguration initiale avec une zone de recharge dans chaque ville :");
		community.displayRechargeZones();

		while (true) {
			System.out.println("\nMenu de gestion des zones de recharge :");
			System.out.println("1. Ajouter une zone de recharge");
			System.out.println("2. Retirer une zone de recharge");
			System.out.println("3. Terminer");

			int choice = 0;

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
				community.removeRechargeZone(city);
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Choix non valide. Veuillez réessayer.");
			}

			System.out.println("\nConfiguration actuelle :");
			community.displayRechargeZones();
		}

		scanner.close();
		System.out.println("Merci d'utiliser le programme.");
		System.exit(0);
	}
}
