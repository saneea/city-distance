package io.github.saneea.citydistance.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class City {

	private final String name;

	// actually HashMap is better, but I use LinkedHashMap only for useful testing
	// because (HashMap change ordering each applications launch)
	private final Map<City, Integer> connections = new LinkedHashMap<>();

	City(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void connect(int distance, City city) {
		connections.put(city, distance);
		city.connections.put(this, distance);
	}

	public List<City2CityPath> getAllPathsTo(City targetCity) {
		return getAllPathsTo(targetCity, new HashSet<>());
	}

	private List<City2CityPath> getAllPathsTo(City targetCity, Set<City> visitedCities) {
		List<City2CityPath> ret = new ArrayList<>();
		visitedCities.add(this);// push (avoid cycling)
		try {
			for (Entry<City, Integer> connection : connections.entrySet()) {
				City childCity = connection.getKey();
				if (!visitedCities.contains(childCity)) {
					int distanceToChild = connection.getValue();

					PathSegment firstPathSegment = new PathSegment(distanceToChild, childCity);

					if (childCity == targetCity) {
						// direct connection
						ret.add(new City2CityPath(firstPathSegment));
					} else {
						ret.addAll(//
								getAllPathsForChild(firstPathSegment, targetCity, visitedCities));
					}
				}
			}
		} finally {
			visitedCities.remove(this);// pop
		}
		return ret;
	}

	private List<City2CityPath> getAllPathsForChild(PathSegment firstPathSegment, City targetCity,
			Set<City> visitedCities) {
		List<City2CityPath> ret = new ArrayList<>();

		List<City2CityPath> childPaths = firstPathSegment.getNextCity().getAllPathsTo(targetCity, visitedCities);
		for (City2CityPath childPath : childPaths) {
			City2CityPath resultPath = new City2CityPath(firstPathSegment);
			resultPath.append(childPath);
			ret.add(resultPath);
		}

		return ret;
	}

	@Override
	public String toString() {
		return name;
	}

}
