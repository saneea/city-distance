package io.github.saneea.citydistance.beans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.saneea.citydistance.api.ErrorCode;
import io.github.saneea.citydistance.exceptions.CityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	public static class ErrorEntity {
		public String message;
		public Exception exception;
		public ErrorCode internalCode;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CityNotFoundException.class)
	@ResponseBody
	public ErrorEntity handleCityNotFound(CityNotFoundException ex) {
		ErrorEntity err = new ErrorEntity();
		err.message = ex.getMessage();
		err.exception = ex;
		err.internalCode = ex.getErrorCode();
		return err;
	}
}
