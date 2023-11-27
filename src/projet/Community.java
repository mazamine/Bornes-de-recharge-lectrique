package projet;

import java.util.ArrayList;

public class Community {
	private ArrayList<String> cities;
	private ArrayList<String[]> routes;
	private ArrayList<String> rechargeZones;

	public Community(int numberOfCities) {
		cities = new ArrayList<>(numberOfCities);
		routes = new ArrayList<>();
		rechargeZones = new ArrayList<>();

		for (int i = 0; i < numberOfCities; i++) {
			cities.add(String.valueOf((char) ('A' + i)));
			rechargeZones.add(String.valueOf((char) ('A' + i)));
		}
	}

	public void addRoute(String city1, String city2) {
		routes.add(new String[] { city1, city2 });
	}

	public void addRechargeZone(String city) {	
		if (!rechargeZones.contains(city)) {
			rechargeZones.add(city);
			System.out.println("Zone de recharge ajoutée à la ville " + city + ".");
		} else {
			System.out.println("Cette ville a déjà une zone de recharge.");
		}
	}

	public void removeRechargeZone(String city) {
		if (rechargeZones.contains(city) && isAccessibilityPreserved(city)) {
			rechargeZones.remove(city);
			System.out.println("Zone de recharge retirée de la ville " + city + ".");
		} else {
			System.out.println("Impossible de retirer la zone de recharge de cette ville.");
		}
	}

	public void displayRechargeZones() {
		System.out.println("Villes avec des zones de recharge :");
		for (String city : rechargeZones) {
			System.out.print(city + " ");
		}
		System.out.println();
	}

	private boolean isAccessibilityPreserved(String removedCity) {
		// Vérifier si la suppression de la zone de recharge de la ville violerait la
		// contrainte d'Accessibilité
		for (String[] route : routes) {
			String city1 = route[0];
			String city2 = route[1];

			if ((rechargeZones.contains(city1) || rechargeZones.contains(city2))
					&& !rechargeZones.contains(removedCity)) {
				return true;
			}
		}
		return false;
	}
}
