package ru.otus.web.websockets.exceptions;

/**
 * Created by Artem Gabbasov on 06.08.2017.
 */
public class AdminWebSocketClientException extends AdminWebSocketException {
    public AdminWebSocketClientException(String message) {
        super("(client side) " + message);
    }
}
