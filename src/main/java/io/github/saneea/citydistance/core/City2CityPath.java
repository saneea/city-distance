package io.github.saneea.citydistance.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class City2CityPath {

	private final List<PathSegment> segments = new ArrayList<>();

	public City2CityPath(PathSegment headSegment) {
		segments.add(headSegment);
	}

	public void append(City2CityPath other) {
		segments.addAll(other.segments);
	}

	public Stream<PathSegment> stream() {
		return segments.stream();
	}

	@Override
	public String toString() {
		return segments.toString();
	}

}
