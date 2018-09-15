package io.github.saneea.citydistance;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.net.URL;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.saneea.citydistance.api.City2CityPath;
import io.github.saneea.citydistance.api.Distances.getDistance.Response;
import io.github.saneea.citydistance.api.Distances.postDistance.Request;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceControllerIT {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/distances");
	}

	@Test
	public void simpleTest() throws Exception {

		Request city2cityInfo = new Request();
		city2cityInfo.setCity1("A");
		city2cityInfo.setCity2("B");
		city2cityInfo.setDistance(20);

		template.postForLocation(base.toString(), city2cityInfo);

		ResponseEntity<Response> response = template.getForEntity(base.toString() + "/A/B", Response.class);

		Response expected = new Response();
		City2CityPath expectedCity2CityPath = new City2CityPath();
		expectedCity2CityPath.setCities(Arrays.asList("A", "B"));
		expectedCity2CityPath.setFinalDistance(20);
		expected.setPaths(Arrays.asList(expectedCity2CityPath));

		Response actual = response.getBody();
		assertReflectionEquals(expected, actual);
	}
}
