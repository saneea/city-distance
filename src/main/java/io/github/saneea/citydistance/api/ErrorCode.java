package io.github.saneea.citydistance.api;

public enum ErrorCode {
	CITY_NOT_FOUND("City \"%s\" was not found"), //
	REDEFINING_DISTANCE("Distance between \"%s\" and \"%s\" has been already defined"), //
	ARGUMENT_NOT_SPECIFIED("Argument \"%s\" was not specified"), //
	BAD_ARGUMENT("Argument \"%s\" is invalid. It must be %s"), //
	NO_PATH("Path between \"%s\" and \"%s\" can not be built");

	private final String messageTemplate;

	ErrorCode(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

}
