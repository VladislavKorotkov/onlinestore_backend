package bsuir.korotkov.onlinestore.util;

public class AccessException extends Exception{
    public AccessException (String errorMessage) {
        super(errorMessage);
    }

    public AccessException() {
    }
}
