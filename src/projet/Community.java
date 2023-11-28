package projet;

import java.util.ArrayList;

public class Community {
	private ArrayList<String> cities;
	private ArrayList<Route> routes;
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
	    if (cities.contains(city1) && cities.contains(city2)) {
	        routes.add(new Route(city1, city2));
	        System.out.println("Route ajoutée entre les villes " + city1 + " et " + city2 + ".");
	    } else {
	        System.out.println("Les villes spécifiées dans la route ne sont pas valides.");
	    }
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
	    for (Route route : routes) {
	        String city1 = route.getCity1();
	        String city2 = route.getCity2();

	        if ((rechargeZones.contains(city1) && !rechargeZones.contains(city2))
	                || (!rechargeZones.contains(city1) && rechargeZones.contains(city2))) {
	            if (rechargeZones.contains(removedCity)) {
	                return false; // Violation found, removal of the zone would break accessibility
	            }
	        }
	    }
	    return true; // No violation found, removal is allowed
	}


}
