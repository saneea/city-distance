package io.github.saneea.citydistance.controllers;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.saneea.citydistance.api.City2CityPath;
import io.github.saneea.citydistance.api.Distances;

@RestController
@RequestMapping(Distances.PATH)
public class DistanceController {

	private Distances.postDistance.Request lastCity2cityInfo;

	@RequestMapping(method = RequestMethod.GET, path = Distances.getDistance.PATH)
	public Distances.getDistance.Response getDistance(//
			@PathVariable("city1") String city1, //
			@PathVariable("city2") String city2) {

		// TODO this is stub implementation, just for endpoint testing

		City2CityPath path1 = new City2CityPath();
		path1.setFinalDistance(10);
		path1.setCities(Arrays.asList(city1, city2));

		City2CityPath path2 = new City2CityPath();
		if (lastCity2cityInfo == null) {
			path2.setFinalDistance(20);
			path2.setCities(Arrays.asList(city1, "X", city2));
		} else {
			path2.setFinalDistance(lastCity2cityInfo.getDistance());
			path2.setCities(Arrays.asList(lastCity2cityInfo.getCity1(), "X", lastCity2cityInfo.getCity2()));
		}

		Distances.getDistance.Response response = new Distances.getDistance.Response();
		response.setPaths(Arrays.asList(path1, path2));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void postDistance(@RequestBody Distances.postDistance.Request city2cityInfo) {
		// TODO this is stub implementation, just for endpoint testing
		lastCity2cityInfo = city2cityInfo;
	}

}
