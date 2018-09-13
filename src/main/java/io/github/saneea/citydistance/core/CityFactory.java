package io.github.saneea.citydistance.core;

import java.util.HashMap;
import java.util.Map;

public class CityFactory {

	private Map<String, City> cities = new HashMap<>();

	public City getCity(String cityName) {
		City city = cities.get(cityName);
		if (city == null) {
			city = new City(cityName);
			cities.put(cityName, city);
		}
		return city;
	}
}
