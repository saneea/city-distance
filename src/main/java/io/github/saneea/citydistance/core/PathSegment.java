package io.github.saneea.citydistance.core;

public class PathSegment {
	private final Integer distance;
	private final City nextCity;

	public PathSegment(Integer distance, City nextCity) {
		this.distance = distance;
		this.nextCity = nextCity;
	}

	public Integer getDistance() {
		return distance;
	}

	public City getNextCity() {
		return nextCity;
	}

	@Override
	public String toString() {
		return distance + " -> " + nextCity;
	}

}
