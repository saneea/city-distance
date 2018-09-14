package io.github.saneea.citydistance.core;

import java.util.HashMap;
import java.util.Map;

import io.github.saneea.citydistance.api.ErrorCode;
import io.github.saneea.citydistance.exceptions.CityDistanceException;

public class CityFactory {

	private Map<String, City> cities = new HashMap<>();

	public City getOrCreateCity(String cityName) {
		City city = cities.get(cityName);
		if (city == null) {
			city = new City(cityName);
			cities.put(cityName, city);
		}
		return city;
	}

	public City getCity(String cityName) throws CityDistanceException {
		City ret = cities.get(cityName);
		if (ret == null) {
			throw new CityDistanceException(ErrorCode.CITY_NOT_FOUND, cityName);
		}
		return ret;
	}
}
