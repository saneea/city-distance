package io.github.saneea.citydistance;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.saneea.citydistance.api.City2CityPath;
import io.github.saneea.citydistance.api.Distances.getDistance.Response;
import io.github.saneea.citydistance.api.Distances.postDistance.Request;
import io.github.saneea.citydistance.api.ErrorCode;
import io.github.saneea.citydistance.api.ErrorEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceControllerIT {

	@LocalServerPort
	private int port;

	private String base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = "http://localhost:" + port + "/distances";
	}

	@Test
	public void simpleTest() throws Exception {
		assertReflectionEquals(null, post("A", "B", 20));

		assertReflectionEquals(//
				error(ErrorCode.REDEFINING_DISTANCE, "Distance between \"A\" and \"B\" has been already defined"), //
				post("A", "B", 20));

		assertReflectionEquals(response("A", "B", 20), get("A", "B"));
	}

	private Response get(String city1, String city2) {
		return template.getForEntity(base + "/" + city1 + "/" + city2, Response.class).getBody();
	}

	private ErrorEntity post(String city1, String city2, Integer distance) {
		return template.postForEntity(base, request(city1, city2, distance), ErrorEntity.class).getBody();
	}

	private static ErrorEntity error(ErrorCode errorCode, String message) {
		ErrorEntity e = new ErrorEntity();
		e.setErrorCode(errorCode);
		e.setMessage(message);
		return e;
	}

	private static Response response(String city1, String city2, Integer distance) {
		Response r = new Response();
		City2CityPath p = new City2CityPath();
		p.setCities(Arrays.asList(city1, city2));
		p.setFinalDistance(distance);
		r.setPaths(Arrays.asList(p));
		return r;
	}

	private static Request request(String city1, String city2, Integer distance) {
		Request r = new Request();
		r.setCity1(city1);
		r.setCity2(city2);
		r.setDistance(distance);
		return r;
	}
}
