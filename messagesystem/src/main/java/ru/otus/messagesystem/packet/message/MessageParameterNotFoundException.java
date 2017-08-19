package ru.otus.messagesystem.packet.message;

/**
 * Created by Artem Gabbasov on 17.08.2017.
 */
public class MessageParameterNotFoundException extends Exception {
    public MessageParameterNotFoundException(String parameterName, String messageType) {
        super("Parameter \"" + parameterName + "\" not found in a message of type: " + messageType);
    }
}
