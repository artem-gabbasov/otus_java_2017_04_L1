package ru.otus.messagesystem;

import ru.otus.messagesystem.impl.MessageSystemImpl;

import java.util.concurrent.Executors;

/**
 * Created by Artem Gabbasov on 19.08.2017.
 */
public class MessageSystemFactory {
    public static MessageSystem createMessageSystem() {
        return new MessageSystemImpl(Executors.newSingleThreadExecutor());
    }
}
