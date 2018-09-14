package io.github.saneea.citydistance.beans;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.github.saneea.citydistance.core.City;
import io.github.saneea.citydistance.core.City2CityPath;
import io.github.saneea.citydistance.core.CityFactory;
import io.github.saneea.citydistance.exceptions.CityNotFoundException;

public class DistanceEngineImpl implements DistanceEngine {

	private final CityFactory cityFactory = new CityFactory();

	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	@Override
	public void defineConnection(String cityName1, String cityName2, int distance) {
		rwLock.writeLock().lock();
		try {
			City city1 = cityFactory.getOrCreateCity(cityName1);
			City city2 = cityFactory.getOrCreateCity(cityName2);
			city1.connect(distance, city2);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public List<City2CityPath> getAllPaths(String cityName1, String cityName2) throws CityNotFoundException {
		rwLock.readLock().lock();
		try {
			City city1 = cityFactory.getCity(cityName1);
			City city2 = cityFactory.getCity(cityName2);
			return city1.getAllPathsTo(city2);
		} finally {
			rwLock.readLock().unlock();
		}
	}

}
