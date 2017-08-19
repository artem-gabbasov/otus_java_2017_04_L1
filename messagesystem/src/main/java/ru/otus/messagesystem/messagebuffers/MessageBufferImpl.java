package ru.otus.messagesystem.messagebuffers;

import ru.otus.messagesystem.packet.Packet;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Artem Gabbasov on 12.08.2017.
 */
public class MessageBufferImpl implements MessageBuffer {
    private final Queue<Packet> incomingPackets = new ConcurrentLinkedQueue<>();

    public MessageBufferImpl() {
    }

    @Override
    public boolean receive(Packet packet) {
        return incomingPackets.add(packet);
    }

    @Override
    public Packet poll() {
        return incomingPackets.poll();
    }
}
