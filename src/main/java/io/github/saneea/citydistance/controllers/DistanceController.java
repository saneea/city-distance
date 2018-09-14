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
import io.github.saneea.citydistance.beans.DistanceEngine;
import io.github.saneea.citydistance.core.City;
import io.github.saneea.citydistance.core.PathSegment;

@RestController
@RequestMapping(Distances.PATH)
public class DistanceController {

	@Autowired
	private DistanceEngine gistanceEngine;

	@RequestMapping(method = RequestMethod.POST)
	public void postDistance(@RequestBody Distances.postDistance.Request city2cityInfo) {
		gistanceEngine.defineConnection(//
				city2cityInfo.getCity1(), //
				city2cityInfo.getCity2(), //
				city2cityInfo.getDistance());
	}

	@RequestMapping(method = RequestMethod.GET, path = Distances.getDistance.PATH)
	public Distances.getDistance.Response getDistance(//
			@PathVariable("city1") String city1, //
			@PathVariable("city2") String city2) {
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
