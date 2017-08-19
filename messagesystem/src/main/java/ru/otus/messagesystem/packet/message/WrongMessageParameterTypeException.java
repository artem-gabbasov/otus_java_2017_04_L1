package ru.otus.messagesystem.packet.message;

/**
 * Created by Artem Gabbasov on 17.08.2017.
 */
public class WrongMessageParameterTypeException extends Exception {
    public WrongMessageParameterTypeException(String parameterName, String messageType, Class<?> actual, Class<?> expected) {
        super("Wrong type for the parameter \"" + parameterName + "\" in a message of type \"" + messageType + "\": " + actual.getName() + " (expected type: " + expected.getName() + ")");
    }
}
