package projet;

import java.util.Objects;

//Déclaration de la classe Ville
public class Ville {
	private String nom;

	public Ville(String nom) {
		this.nom = nom;
	}

	public Ville() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	// Méthode equals pour vérifier l'égalité entre deux objets de type Ville
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Ville ville = (Ville) obj;
		return Objects.equals(nom, ville.nom);
	}
}
