package io.github.saneea.citydistance.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class City2CityPath implements Iterable<PathSegment> {

	private final List<PathSegment> segments = new ArrayList<>();

	public City2CityPath(PathSegment headSegment) {
		segments.add(headSegment);
	}

	public void append(City2CityPath other) {
		segments.addAll(other.segments);
	}

	@Override
	public Iterator<PathSegment> iterator() {
		return segments.iterator();
	}

	@Override
	public String toString() {
		return segments.toString();
	}

}
