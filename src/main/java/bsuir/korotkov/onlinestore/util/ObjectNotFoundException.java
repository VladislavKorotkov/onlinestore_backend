package bsuir.korotkov.onlinestore.util;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ObjectNotFoundException() {
    }
}


