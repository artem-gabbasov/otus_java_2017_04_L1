package ru.otus.web.websockets.exceptions;

/**
 * Created by Artem Gabbasov on 06.08.2017.
 */
public class AdminWebSocketServerException extends AdminWebSocketException {
    public AdminWebSocketServerException(String message) {
        super("(server side) " + message);
    }
}
