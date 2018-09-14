package io.github.saneea.citydistance.exceptions;

import io.github.saneea.citydistance.api.ErrorCode;

public class CityDistanceException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public CityDistanceException(ErrorCode errorCode, Object... args) {
		super(String.format(errorCode.getMessageTemplate(), args));
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
