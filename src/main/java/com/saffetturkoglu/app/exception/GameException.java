package com.saffetturkoglu.app.exception;

public class GameException extends GlobalResponseException{


    private static final long serialVersionUID = 1L;

    public GameException(final String message) {
        super(message);
    }

}
