package io.github.saneea.citydistance.exceptions;

import io.github.saneea.citydistance.api.ErrorCode;

public class CityNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String city;

	public CityNotFoundException(String city) {
		super("City \"" + city + "\" was not found");
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public ErrorCode getErrorCode() {
		return ErrorCode.CITY_NOT_FOUND;
	}

}
