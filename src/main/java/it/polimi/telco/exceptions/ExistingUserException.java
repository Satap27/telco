package it.polimi.telco.exceptions;

public class ExistingUserException extends Exception {

    public ExistingUserException(String message) {
        super(message);
    }
}
