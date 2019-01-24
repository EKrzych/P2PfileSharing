package com.codecool;

public enum Message {
    HELLO("hello"),
    PEER("found peer"),
    FILE("looking for file"),
    PEERS("list with already checked peers"),
    PROBLEM("couldn't do operation");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
