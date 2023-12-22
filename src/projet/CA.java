package projet;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;

public class CA {
	public List<Ville> villes;
	public List<Route> routes;
	public List<Ville> bornes;

	public CA(List<Ville> villes, List<Route> routes, List<Ville> bornes) {
		this.villes = villes;
		this.routes = routes;
		this.bornes = bornes;
	}

	public CA() {
		villes = new ArrayList<Ville>();
		routes = new ArrayList<Route>();
		bornes = new ArrayList<Ville>();
	}

	/**
	 * Cette méthode lit le contenu d'un fichier texte pour extraire des
	 * informations sur les villes, les routes et les bornes de recharge, et les
	 * stocke dans des listes.
	 * 
	 * @param fichier Le fichier à lire
	 * @throws IOException En cas d'erreur lors de la lecture du fichier
	 */

	public void lectureFichier(File fichier) throws IOException {
		try {
			FileReader fileReader = new FileReader(fichier);
			BufferedReader bfr = new BufferedReader(fileReader);
			String ligne;
			while ((ligne = bfr.readLine()) != null) {
				if (ligne.startsWith("ville")) {
					String nomVille = ligne.split("\\(")[1].split("\\)")[0];
					villes.add(new Ville(nomVille));
				} else if (ligne.startsWith("route")) {
					String infos[] = ligne.split("\\(")[1].split("\\)")[0].split(",");
					String nomVilleSource = infos[0];
					String nomVilleCible = infos[1];
					routes.add(new Route(new Ville(nomVilleSource), new Ville(nomVilleCible)));
				} else if (ligne.startsWith("recharge")) {
					String nomVille = ligne.split("\\(")[1].split("\\)")[0];
					bornes.add(new Ville(nomVille));
				}
			}
			bfr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
	}

	/**
	 * Cette méthode écrit les données des villes, routes et bornes de recharge dans
	 * un fichier texte.
	 * 
	 * @param cheminFichier Le chemin vers le fichier dans lequel écrire les données
	 * @throws IOException En cas d'erreur lors de l'écriture dans le fichier
	 */

	public void ecritureDansFichier(String cheminFichier) throws IOException {
		File fichier = new File(cheminFichier);

		if (fichier.exists()) {
			try (FileWriter writer = new FileWriter(cheminFichier)) {
				for (Ville ville : villes) {
					writer.write("ville(" + ville.getNom() + ").\n");
				}
				for (Route route : routes) {
					writer.write(
							"route(" + route.getVilleSource().getNom() + "," + route.getVilleCible().getNom() + ").\n");
				}
				for (Ville ville : bornes) {
					writer.write("recharge(" + ville.getNom() + ").\n");
				}
				System.out.println("Données écrites avec succès dans le fichier.");
			} catch (IOException e) {
				throw new IOException("Une erreur est survenue lors de l'écriture dans le fichier : " + e.getMessage());
			}
		} else {
			throw new IOException("Fichier introuvable");
		}
	}

	/**
	 * Cette méthode ajoute chaque ville de la liste des villes à la liste des
	 * bornes de recharge.
	 */

	public void RemplirBornesDeRecharge() {
		for (Ville ville : villes) {
			bornes.add(ville);
		}
	}

	/**
	 * Ajoute une ville à la liste des villes.
	 * 
	 * @param ville La ville à ajouter
	 * @throws InvalidInputException Si la ville existe déjà dans la liste des
	 *                               villes
	 */

	public void ajouterVille(Ville ville) throws InvalidInputException {
		if (!villes.contains(ville)) {
			villes.add(ville);
		} else {
			throw new InvalidInputException("La ville " + ville.getNom() + " existe déjà");
		}
	}

	/**
	 * Supprime une ville de la liste des villes et des bornes de recharge. Supprime
	 * également toutes les routes associées à cette ville.
	 * 
	 * @param ville La ville à supprimer
	 * @throws InvalidInputException Si la ville n'existe pas dans la liste des
	 *                               villes
	 */

	public void supprimerVille(Ville ville) throws InvalidInputException {
		if (villes.contains(ville)) {
			villes.remove(ville);
			if (bornes.contains(ville)) {
				bornes.remove(ville);
			}
			for (Route route : routes) {
				if ((ville == route.getVilleCible() || ville == route.getVilleSource())) {
					routes.remove(route);
				}
			}
		} else {
			throw new InvalidInputException("La ville " + ville.getNom() + " n'existe pas");
		}
	}

	/**
	 * Ajoute une route à la liste des routes si les villes sources et cibles
	 * existent dans la liste des villes.
	 * 
	 * @param route La route à ajouter
	 * @throws InvalidInputException Si au moins l'une des villes sources ou cibles
	 *                               n'existe pas, ou si la route existe déjà
	 */

	public void ajouterRoute(Route route) throws InvalidInputException {
		if (villes.contains(route.getVilleSource()) && villes.contains(route.getVilleCible())) {
			if (!routes.contains(route)) {
				routes.add(route);
			} else {
				throw new InvalidInputException("La route existe déjà");
			}
		} else {
			throw new InvalidInputException("Au moins l'une des villes n'existent pas.");
		}
	}

	/**
	 * Supprime une route de la liste des routes si les villes sources et cibles
	 * existent dans la liste des villes et si la route entre ces deux villes
	 * existe.
	 * 
	 * @param route La route à supprimer
	 * @throws InvalidInputException Si au moins l'une des villes sources ou cibles
	 *                               n'existe pas, ou si la route n'existe pas
	 */

	public void supprimerRoute(Route route) throws InvalidInputException {
		if (villes.contains(route.getVilleSource()) && villes.contains(route.getVilleCible())) {
			if (routes.contains(route)) {
				routes.remove(route);
			} else {
				throw new InvalidInputException("La route n'existe pas ");
			}
		} else {
			throw new InvalidInputException("Au moins l'une des villes n'existent pas.");
		}
	}

	/**
	 * Ajoute une borne de recharge à une ville si elle existe dans la liste des
	 * villes et si elle n'a pas déjà de borne de recharge.
	 * 
	 * @param ville La ville à laquelle ajouter une borne de recharge
	 * @throws InvalidInputException Si la ville n'existe pas ou si elle a déjà une
	 *                               borne de recharge
	 */

	public void ajouterBorneDeRecharge(Ville ville) throws InvalidInputException {
		if (!villes.contains(ville)) {
			throw new InvalidInputException("La ville n'existe pas");
		}
		if (bornes.contains(ville)) {
			throw new InvalidInputException(ville.getNom() + " a déjà une borne de recharge");
		}
		bornes.add(ville);
		afficherVillesAvecRecharge();
	}

	/**
	 * Vérifie si une ville (villeCible) est accessible, c'est à dire si elle a une
	 * borne de recharge ou si elle a une ville a une ville adjacente qui a une
	 * borne de recharge à part celle qu'on veut supprimer sa borne de recharge
	 * (villeSource).
	 * 
	 * @param villeSource La ville source (celle qu'on veut supprimer sa borne de
	 *                    recharge)
	 * @param villeCible  La ville cible (celle qu'on veut savoir si elle vérifie le
	 *                    crritère d'accessibilité
	 * @return true si la ville cible est accessible, sinon false
	 */

	public boolean isAccessible(Ville villeSource, Ville villeCible) {
		boolean res = false;
		if (bornes.contains(villeCible)) {
			res = true;
			/*
			 * si elle n'a pas de borne de recharge, on cherche si elle a une ville
			 * adjacente qui a une borne de recharge à part celle qu'on veut supprimer
			 */
		} else {
			for (Route route : routes) {
				if ((route.getVilleSource().equals(villeCible)) && !(route.getVilleCible().equals(villeSource))
						&& (bornes.contains(route.getVilleCible()))) {
					res = true;
				} else if ((route.getVilleCible().equals(villeCible)) && !(route.getVilleSource().equals(villeSource))
						&& bornes.contains(route.getVilleSource())) {
					res = true;
				}
			}
		}
		return res;
	}

	/**
	 * Supprime la borne de recharge d'une ville si elle existe et contient une
	 * borne de recharge et si le critère d'accessibilité est vérifié.
	 * 
	 * @param ville La ville dont la borne de recharge doit être supprimée
	 * @throws InvalidInputException Si la ville n'existe pas ou si elle n'a pas de
	 *                               borne de recharge, ou si la suppression de la
	 *                               borne laisserait une ville adjacente sans borne
	 *                               de recharge (critère d'accessibilité non
	 *                               vérifié)
	 */

	public void supprimerBorneRecharge(Ville ville) throws InvalidInputException {
		if (!villes.contains(ville)) {
			throw new InvalidInputException(ville.getNom() + " n'existe pas.");
		}
		if (!bornes.contains(ville)) {
			throw new InvalidInputException(ville.getNom() + " n'a pas de borne de recharge.");
		}
		for (Route route : routes) {
			if (route.getVilleSource().equals(ville)) {
				if (!isAccessible(ville, route.getVilleCible())) {
					throw new InvalidInputException("Si on supprime " + ville.getNom() + " alors "
							+ route.getVilleCible().getNom() + " n'a pas de borne de "
							+ "recharche et n'aura plus de ville adjacente qui en " + "a une. \n");
				}
			} else if (route.getVilleCible().equals(ville)) {
				if (!isAccessible(ville, route.getVilleSource())) {
					throw new InvalidInputException("Si on supprime " + ville.getNom() + " alors "
							+ route.getVilleSource().getNom() + " n'a pas de borne de "
							+ "recharche et n'aura plus de ville adjacente qui en " + "a une. \n");
				}
			}
		}
		bornes.remove(ville);
	}

	/**
	 * Vérifie si chaque ville a une borne de recharge ou si au moins une ville
	 * adjacente a une borne de recharge.
	 * 
	 * @return true si la condition d'accessibilité est vérifiée, sinon false
	 */

	public boolean accessibiliteVerifiee() {
		if (villes.size() == bornes.size()) {
			return true;
		}
		for (Ville ville : villes) {
			if (!bornes.contains(ville)) { // Pour chaque ville sans borne de recharge
				boolean hasAdjacentWithRecharge = false;
				for (Route route : routes) {
					if (route.getVilleSource().equals(ville) && bornes.contains(route.getVilleCible())) {
						hasAdjacentWithRecharge = true;
						break;
					} else if (route.getVilleCible().equals(ville) && bornes.contains(route.getVilleSource())) {
						hasAdjacentWithRecharge = true;
						break;
					}
				}
				if (!hasAdjacentWithRecharge) {
					return false; // Si aucune ville adjacente n'a de borne, retourne faux
				}
			}
		}
		return true; // Si toutes les vérifications passent, retourne vrai
	}

	/**
	 * Initialise la liste des bornes de recharge avec toutes les villes qui ne sont
	 * pas encore des bornes. Elle ajoute les villes manquantes à la liste des
	 * bornes de recharge.
	 */

	public void initBorneDeRecharge() {
		for (Ville ville : villes) {
			if (!(bornes.contains(ville))) {
				bornes.add(ville);
			}
		}
	}

	/**
	 * Récupère le nombre de routes connectées à une ville donnée.
	 * 
	 * @param ville La ville pour laquelle on veut connaître le nombre de routes
	 *              connectées
	 * @return Le nombre de routes connectées à la ville donnée
	 */

	public int getNombreRoutes(Ville ville) {
		int count = 0;
		for (Route route : routes) {
			if (route.getVilleSource().equals(ville) || route.getVilleCible().equals(ville)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Récupère la ville cible associée à une ville source donnée, à travers les
	 * routes existantes.
	 * 
	 * @param ville La ville source pour laquelle on cherche la ville cible
	 * @return La ville cible associée à la ville source donnée, ou null si elle
	 *         n'est pas trouvée
	 */

	private Ville getVilleCible(Ville ville) {
		for (Route route : routes) {
			if (ville.equals(route.getVilleSource())) {
				return route.getVilleCible();
			} else if (ville.equals(route.getVilleCible())) {
				return route.getVilleSource();
			}
		}
		return null;
	}

	/**
	 * Récupère la liste des villes adjacentes à une ville donnée à travers les
	 * routes.
	 * 
	 * @param ville La ville pour laquelle on veut trouver les villes adjacentes
	 * @return Une liste des villes adjacentes à la ville donnée
	 */

	public List<Ville> villesAdjacentes(Ville ville) {
		List<Ville> villesAdj = new ArrayList<Ville>();
		for (Route route : routes) {
			if (route.getVilleSource().equals(ville) || route.getVilleCible().equals(ville)) {
				villesAdj.add(ville);
			}
		}
		return villesAdj;
	}

	/**
	 * Tente d'améliorer l'optimisation en supprimant une à une les bornes de
	 * recharge. Si une exception est levée lors de la suppression d'une borne,
	 * affiche un message indiquant qu'on ne peut pas améliorer davantage.
	 */

	public void ameliorerOptimisation() {
		List<Ville> listBornes = new ArrayList<Ville>(bornes);
		for (Ville ville : listBornes) {
			try {
				supprimerBorneRecharge(ville);
			} catch (InvalidInputException e) {
				continue;
			}
		}
	}

	/**
	 * Identifie les villes qui nécessitent une borne de recharge obligatoire en
	 * raison de leur seule route sortante.
	 * 
	 * @return Une liste des villes qui nécessitent une borne de recharge.
	 */

	public List<Ville> bornesObligatoires() {
		List<Ville> listBornes = new ArrayList<Ville>(villes);
		List<Ville> bornesObligatoires = new ArrayList<Ville>();
		for (Ville ville : listBornes) {
			if (getNombreRoutes(ville) == 1 && getVilleCible(ville) != null) {
				if (!(bornes.contains(getVilleCible(ville)))) {
					bornes.add(getVilleCible(ville));
				}
				if (bornes.contains(ville)) {
					bornes.remove(ville);
				}
				bornesObligatoires.add(getVilleCible(ville));
			}
		}
		return bornesObligatoires;
	}

	/**
	 * Optimise l'emplacement des bornes de recharge en se basant sur plusieurs
	 * critères. Cet algorithme se base sur deux approches: -la première s'agit
	 * d'identifier les villes qui doivent absolument avoir une une borne de
	 * recharge comme elles sont liées à une ville qui a une seule route. -la
	 * deuxième est de parcourir toutes les villes en commençant par la ville qui a
	 * le plus de nombres de villes adjacentes et on lui met une borne et on retire
	 * les bornes de recharges, si elles existent, de toutes ses villes adjacentes
	 * si la conditino d'accessibilité reste maintenue.
	 *
	 */

	public void optimiserBornes() {
		/*
		 * on utilise bornesObligatoires pour indiquer les villes qui doivent
		 * obligatoirement avoir des bornes comme elles sont liées à une ville qui a une
		 * seule route.
		 */

		/*
		 * villesTraitees est utilisée pour supprimer la ville de listBornes lorsqu'on
		 * l'aurait déjà traitée ( pour ne pas prendre toujours la meme ville qui a le
		 * plus grand nombre de villes adjacentes).
		 */
		List<Ville> bornesObligatoires = bornesObligatoires();
		List<Ville> listVilles = new ArrayList<Ville>(villes);
		List<Ville> villesTraitees = new ArrayList<Ville>();
		Ville v = new Ville();
		int count = 0;

		do {
			int maxVoisins = 1;
			for (Ville ville : listVilles) {
				if ((getNombreRoutes(ville) > maxVoisins) && !(villesTraitees.contains(ville))) {
					maxVoisins = getNombreRoutes(ville);
					v = ville;
				}
			}
			count++;
			villesTraitees.add(v);
			List<Ville> villesAdj = villesAdjacentes(v);
			for (Ville villeCible : villesAdj) {
				if (villeCible != null && bornes.contains(villeCible) && !bornesObligatoires.contains(villeCible)) {

					/*
					 * on rajoute cette condition pour etre sur que si une ville avec un plus grand
					 * nombre de villes adjacentes, on ne lui retire pas sa borne pour une ville qui
					 * a moins de villes adjacentes.
					 */
					if (getNombreRoutes(villeCible) < getNombreRoutes(v)) {
						bornes.remove(villeCible);
						if (!bornes.contains(v)) {
							bornes.add(v);
						}
						if (!accessibiliteVerifiee()) {
							bornes.add(villeCible);
						}
					}
				}
			}
		} while (count < villes.size());

	}

	/**
	 * Affiche les noms des villes ayant des bornes de recharge.
	 */

	public void afficherVillesAvecRecharge() {
		System.out.println("les villes qui ont des bornes de recharges sont: ");
		for (Ville ville : bornes) {
			System.out.print(ville.getNom() + " ");
		}
		System.out.print("\n");
	}

}
