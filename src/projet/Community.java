package projet;

import java.util.ArrayList;

public class Community {
	private ArrayList<Ville> cities;
	private ArrayList<Route> routes;

	public Community() {
		cities = new ArrayList<>();
		routes = new ArrayList<>();
	}

	public void addVille(String name) {
		Ville newVille = new Ville(name);
		if (cities.contains(newVille)) {
			System.out.println("Cette ville existe déjà.");
		} else {
			cities.add(newVille);
		}
	}

	public void addRoute(String city1, String city2) {
		Ville ville1 = findVille(city1);
		Ville ville2 = findVille(city2);

		if (ville1 != null && ville2 != null) {
			for (Route route : routes) {
				if ((route.getCity1().equals(ville1) && route.getCity2().equals(ville2))
						|| (route.getCity1().equals(ville2) && route.getCity2().equals(ville1))) {
					System.out.println("Cette route existe déjà.");
					return;
				}
			}
			routes.add(new Route(ville1, ville2));
			System.out.println("Route ajoutée entre les villes " + ville1.getName() + " et " + ville2.getName() + ".");
		} else {
			System.out.println("Les villes spécifiées dans la route ne sont pas valides.");
		}
	}

	public void addRechargeZone(String city) {
		Ville ville = findVille(city);
		if (ville != null) {
			if (!ville.hasZoneDeRecharge()) {
				ville.setZoneDeRecharge(true);
				System.out.println("Zone de recharge ajoutée à la ville " + city + ".");
			} else {
				System.out.println("Cette ville a déjà une zone de recharge.");
			}
		} else {
			System.out.println("La ville n'existe pas.");
		}
	}

	public void deleteRechargeZone(String city) {
		Ville ville = findVille(city);
		if(isAccessibilityPreserved(ville)) {
			if (ville != null) {
				if (ville.hasZoneDeRecharge()) {
					ville.setZoneDeRecharge(false);
					System.out.println("Zone de recharge retirée de la ville " + city + ".");
				} else {
					System.out.println("Cette ville n'a pas une zone de recharge.");
				}
			} else {
				System.out.println("La ville n'existe pas.");
			}
		} else  {
			System.out.println("impossible de supprimer cette ville");
		}
		
	}
	
	private boolean isAccessibilityPreserved(Ville removedCity) {
	    for (Route route : routes) {
	        Ville city1 = route.getCity1();
	        Ville city2 = route.getCity2();

	        if ((city1.hasZoneDeRecharge() && !city2.hasZoneDeRecharge())
	                || (!city1.hasZoneDeRecharge() && city2.hasZoneDeRecharge())) {
	            if (findVille(removedCity.getName()).hasZoneDeRecharge()) {
	                return false; // Violation found, removal of the zone would break accessibility
	            }
	        }
	    }
	    return true; // No violation found, removal is allowed
	}

	public void displayRechargeZones() {
		System.out.println("Villes avec des zones de recharge :");
		for (Ville ville : cities) {
			if (ville.hasZoneDeRecharge()) {
				System.out.print(ville.getName() + " ");
			}
		}
		System.out.println();
	}

	private Ville findVille(String name) {
		for (Ville ville : cities) {
			if (ville.getName().equals(name)) {
				return ville;
			}
		}
		return null;
	}
}
