package ru.otus.messagesystem.packet.message;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 * Сообщение, передаваемое между агентами.
 * Подразумевается, что оно полностью неизменяемо с момента создания
 */
public class Message {
    private final String messageType;
    private final Map<String, Object> parametersMap;

    public Message(String messageType, Pair<String, Object>... parameters) {
        this.messageType = messageType;
        Map<String, Object> map = new HashMap<>();
        Arrays.stream(parameters)
                .forEach(pair -> map.put(pair.getKey(), pair.getValue()));
        this.parametersMap = Collections.unmodifiableMap(map);
    }

    public String getMessageType() {
        return messageType;
    }

    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }

    public <T> T getParameter(String name, Class<T> clazz) throws MessageParameterNotFoundException, WrongMessageParameterTypeException {
        Object result = parametersMap.get(name);
        if (result == null) {
            throw new MessageParameterNotFoundException(name, messageType);
        } else {
            if (clazz.isAssignableFrom(result.getClass())) {
                return (T) result;
            } else {
                throw new WrongMessageParameterTypeException(name, messageType, result.getClass(), clazz);
            }
        }
    }
}
