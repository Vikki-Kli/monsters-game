package ru.headsandhands.game.exception;

public class CreatureIsNotValidException extends RuntimeException{
    public CreatureIsNotValidException(String message){
        super(message);
    }
}
