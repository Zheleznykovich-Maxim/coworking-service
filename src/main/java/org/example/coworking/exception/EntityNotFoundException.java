package org.example.coworking.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, int id) {
        super(String.format("The %s with id %d not found", entity, id));
    }
}
