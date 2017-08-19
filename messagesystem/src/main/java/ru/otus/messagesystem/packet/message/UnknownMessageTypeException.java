package ru.otus.messagesystem.packet.message;

/**
 * Created by Artem Gabbasov on 17.08.2017.
 */
public class UnknownMessageTypeException extends Exception {
    public UnknownMessageTypeException(String messageType) {
        super("Unknown message type: " + messageType);
    }
}
