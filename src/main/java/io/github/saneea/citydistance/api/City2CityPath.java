package io.github.saneea.citydistance.api;

import java.util.List;

public class City2CityPath {

	private int finalDistance;
	private List<String> cities;

	public int getFinalDistance() {
		return finalDistance;
	}

	public void setFinalDistance(int finalDistance) {
		this.finalDistance = finalDistance;
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

}
