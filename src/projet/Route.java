package projet;

import java.util.Objects;

//Déclaration de la classe Route
public class Route {

	private Ville villeSource;
	private Ville villeCible;

	public Route(Ville villeSource, Ville villeCible) {
		this.villeSource = villeSource;
		this.villeCible = villeCible;
	}

	public Ville getVilleSource() {
		return villeSource;
	}

	public Ville getVilleCible() {
		return villeCible;
	}

	// Méthode equals pour vérifier l'égalité entre deux objets de type Route
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Route aux = (Route) obj;
		return Objects.equals(this.villeSource, aux.villeSource) && Objects.equals(this.villeCible, aux.villeCible);
	}

}
