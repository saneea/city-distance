package io.github.saneea.citydistance.beans;

import java.util.List;

import io.github.saneea.citydistance.core.City2CityPath;

public interface DistanceEngine {

	void defineConnection(String cityName1, String cityName2, int distance);

	List<City2CityPath> getAllPaths(String cityName1, String cityName2);
}
