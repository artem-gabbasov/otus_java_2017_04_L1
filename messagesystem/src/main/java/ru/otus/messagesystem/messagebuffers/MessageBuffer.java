package ru.otus.messagesystem.messagebuffers;

import ru.otus.messagesystem.packet.Packet;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public interface MessageBuffer {
    boolean receive(Packet packet);
    Packet poll();
}
