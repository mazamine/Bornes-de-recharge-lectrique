package projet;

public class Ville {
	private String name;
	private boolean hasZoneDeRecharge;

	public Ville(String name) {
		this.name = name;
		hasZoneDeRecharge = false;
	}

	public String getName() {
		return name;
	}

	public boolean hasZoneDeRecharge() {
		return hasZoneDeRecharge;
	}

	public void setZoneDeRecharge() {
		hasZoneDeRecharge = true;
	}

	public void deleteZoneDeRecharge() {
		hasZoneDeRecharge = false;
	}
}
