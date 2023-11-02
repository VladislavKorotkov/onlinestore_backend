package bsuir.korotkov.onlinestore.util;

public class ObjectIsPresentException extends Exception{
    public ObjectIsPresentException(String errorMessage) {
        super(errorMessage);
    }

    public ObjectIsPresentException() {
    }
}
