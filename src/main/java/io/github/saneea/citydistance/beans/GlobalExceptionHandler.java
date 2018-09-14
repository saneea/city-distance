package io.github.saneea.citydistance.beans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.saneea.citydistance.api.ErrorCode;
import io.github.saneea.citydistance.exceptions.CityDistanceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	public static class ErrorEntity {
		public String message;
		public ErrorCode errorCode;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CityDistanceException.class)
	@ResponseBody
	public ErrorEntity handleCityNotFound(CityDistanceException ex) {
		ErrorEntity err = new ErrorEntity();
		err.message = ex.getMessage();
		err.errorCode = ex.getErrorCode();
		return err;
	}
}
