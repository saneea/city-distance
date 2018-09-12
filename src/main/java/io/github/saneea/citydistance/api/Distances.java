package io.github.saneea.citydistance.api;

import java.util.List;

public interface Distances {

	String PATH = "/distances";

	interface getDistance {
		String PATH = "/{city1}/{city2}";

		class Response {
			List<City2CityPath> paths;

			public List<City2CityPath> getPaths() {
				return paths;
			}

			public void setPaths(List<City2CityPath> paths) {
				this.paths = paths;
			}

		}
	}

	interface postDistance {

		class Request {
			int distance;
			String city1;
			String city2;

			public int getDistance() {
				return distance;
			}

			public void setDistance(int distance) {
				this.distance = distance;
			}

			public String getCity1() {
				return city1;
			}

			public void setCity1(String city1) {
				this.city1 = city1;
			}

			public String getCity2() {
				return city2;
			}

			public void setCity2(String city2) {
				this.city2 = city2;
			}

		}
	}
}
