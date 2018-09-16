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
	public void noArgumentTest() throws Exception {
		assertReflectionEquals(//
				error(ErrorCode.ARGUMENT_NOT_SPECIFIED, "Argument \"city1\" was not specified"), //
				post(null, "B", 20));

		assertReflectionEquals(//
				error(ErrorCode.ARGUMENT_NOT_SPECIFIED, "Argument \"city2\" was not specified"), //
				post("A", null, 20));

		assertReflectionEquals(//
				error(ErrorCode.ARGUMENT_NOT_SPECIFIED, "Argument \"distance\" was not specified"), //
				post("A", "B", null));
	}

	@Test
	public void badArgumentTest() throws Exception {
		assertReflectionEquals(//
				error(ErrorCode.BAD_ARGUMENT, "Argument \"city1\" is invalid. It must be non-empty-string"), //
				post("", "B", 20));

		assertReflectionEquals(//
				error(ErrorCode.BAD_ARGUMENT, "Argument \"city2\" is invalid. It must be non-empty-string"), //
				post("A", "", 20));

		assertReflectionEquals(//
				error(ErrorCode.BAD_ARGUMENT, "Argument \"distance\" is invalid. It must be positive number"), //
				post("A", "B", 0));

		assertReflectionEquals(//
				error(ErrorCode.BAD_ARGUMENT, "Argument \"distance\" is invalid. It must be positive number"), //
				post("A", "B", -1));
	}

	@Test
	public void noCityTest() throws Exception {
		assertReflectionEquals(null, post("C", "D", 20));

		assertReflectionEquals(error(ErrorCode.CITY_NOT_FOUND, "City \"N1\" was not found"), getForError("N1", "D"));
		assertReflectionEquals(error(ErrorCode.CITY_NOT_FOUND, "City \"N2\" was not found"), getForError("C", "N2"));
	}

	@Test
	public void positiveTest() throws Exception {
		assertReflectionEquals(null, post("E", "F", 20));
		assertReflectionEquals(response("E", "F", 20), getForOk("E", "F"));

		assertReflectionEquals(//
				error(ErrorCode.REDEFINING_DISTANCE, "Distance between \"E\" and \"F\" has been already defined"), //
				post("E", "F", 20));
	}

	@Test
	public void redefineDistanceTest() throws Exception {
		assertReflectionEquals(null, post("G", "H", 20));
		assertReflectionEquals(response("G", "H", 20), getForOk("G", "H"));

		assertReflectionEquals(//
				error(ErrorCode.REDEFINING_DISTANCE, "Distance between \"G\" and \"H\" has been already defined"), //
				post("G", "H", 20));
	}

	@Test
	public void noPathTest() throws Exception {
		assertReflectionEquals(null, post("I", "J", 20));
		assertReflectionEquals(null, post("K", "L", 20));

		assertReflectionEquals(//
				error(ErrorCode.NO_PATH, "Path between \"I\" and \"L\" can not be built"), //
				getForError("I", "L"));
	}

	@Test
	public void pathToSameCityTest() throws Exception {
		assertReflectionEquals(//
				error(ErrorCode.DEFINING_DISTANCE_TO_THE_SAME_CITY, "Distance from city to itself can not be defined"), //
				post("M", "M", 20));
	}

	private Response getForOk(String city1, String city2) {
		return getFor(city1, city2, Response.class);
	}

	private ErrorEntity getForError(String city1, String city2) {
		return getFor(city1, city2, ErrorEntity.class);
	}

	private <ResponseType> ResponseType getFor(String city1, String city2, Class<ResponseType> responseType) {
		return template.getForEntity(base + "/" + city1 + "/" + city2, responseType).getBody();
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
