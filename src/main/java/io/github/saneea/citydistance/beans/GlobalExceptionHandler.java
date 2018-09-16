package io.github.saneea.citydistance.beans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.saneea.citydistance.api.ErrorEntity;
import io.github.saneea.citydistance.exceptions.CityDistanceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CityDistanceException.class)
	@ResponseBody
	public ErrorEntity handleCityNotFound(CityDistanceException ex) {
		ErrorEntity err = new ErrorEntity();
		err.setMessage(ex.getMessage());
		err.setErrorCode(ex.getErrorCode());
		return err;
	}
}
