package io.github.saneea.citydistance.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.saneea.citydistance.api.City2CityPath;
import io.github.saneea.citydistance.api.Distances;
import io.github.saneea.citydistance.api.ErrorCode;
import io.github.saneea.citydistance.beans.DistanceEngine;
import io.github.saneea.citydistance.core.City;
import io.github.saneea.citydistance.core.PathSegment;
import io.github.saneea.citydistance.exceptions.CityDistanceException;

@RestController
@RequestMapping(Distances.PATH)
public class DistanceController {

	@Autowired
	private DistanceEngine gistanceEngine;

	@RequestMapping(method = RequestMethod.POST)
	public void postDistance(@RequestBody Distances.postDistance.Request city2cityInfo) throws CityDistanceException {
		String city1 = checkArgCity("city1", city2cityInfo.getCity1());
		String city2 = checkArgCity("city2", city2cityInfo.getCity2());
		Integer distance = checkArgDistance("distance", city2cityInfo.getDistance());
		gistanceEngine.defineConnection(city1, city2, distance);
	}

	private String checkArgCity(String argName, String city) throws CityDistanceException {
		checkArgPresence(argName, city);
		if (city.isEmpty()) {
			throw new CityDistanceException(ErrorCode.BAD_ARGUMENT, argName, "non-empty-string");
		}
		return city;
	}

	private Integer checkArgDistance(String argName, Integer distance) throws CityDistanceException {
		checkArgPresence(argName, distance);
		if (distance <= 0) {
			throw new CityDistanceException(ErrorCode.BAD_ARGUMENT, argName, "positive number");
		}
		return distance;
	}

	private void checkArgPresence(String argName, Object arg) throws CityDistanceException {
		if (arg == null) {
			throw new CityDistanceException(ErrorCode.ARGUMENT_NOT_SPECIFIED, argName);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = Distances.getDistance.PATH)
	public Distances.getDistance.Response getDistance(//
			@PathVariable("city1") String city1, //
			@PathVariable("city2") String city2) throws CityDistanceException {
		List<City2CityPath> paths = gistanceEngine.getAllPaths(city1, city2)//
				.stream()//
				.map(pathInternal -> pathInternalToApi(city1, pathInternal))//
				.collect(Collectors.toList());

		Distances.getDistance.Response response = new Distances.getDistance.Response();
		response.setPaths(paths);
		return response;
	}

	private City2CityPath pathInternalToApi(String startCity,
			io.github.saneea.citydistance.core.City2CityPath pathInternal) {
		City2CityPath pathApi = new City2CityPath();

		Stream<String> tailCities = pathInternal//
				.stream()//
				.map(PathSegment::getNextCity).map(City::getName);

		pathApi.setCities(Stream.concat(//
				Stream.of(startCity), //
				tailCities)//
				.collect(Collectors.toList()));

		pathApi.setFinalDistance(pathInternal//
				.stream()//
				.mapToInt(PathSegment::getDistance)//
				.sum());

		return pathApi;
	}

}
