package ru.otus.messagesystem.packet;

import ru.otus.messagesystem.packet.message.Message;

import java.io.Serializable;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class Packet implements Serializable {
    private final Destination destination;
    private final Destination replyDestination;

    private final Message message;

    public Packet(Destination destination, Destination replyDestination, Message message) {
        this.destination = destination;
        this.replyDestination = replyDestination;
        this.message = message;
    }

    public Destination getDestination() {
        return destination;
    }

    public Destination getReplyDestination() {
        return replyDestination;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message: " + message.toString();
    }
}
