package projet;

public class Ville {
	private String name;
	private boolean zoneDeRecharge;

	public Ville(String name) {
		this.name = name;
		zoneDeRecharge = true;
	}

	public String getName() {
		return name;
	}

	public boolean hasZoneDeRecharge() {
		return zoneDeRecharge;
	}

	public void setZoneDeRecharge(boolean zoneDeRecharge) {
		this.zoneDeRecharge = zoneDeRecharge;
	}
}
