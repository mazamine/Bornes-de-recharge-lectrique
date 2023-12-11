package projet;

public class Route {
	private Ville city1, city2;

	public Route(Ville city1, Ville city2) {
		this.city1 = city1;
		this.city2 = city2;
	}

	public Ville getCity1() {
		return city1;
	}

	public Ville getCity2() {
		return city2;
	}
}