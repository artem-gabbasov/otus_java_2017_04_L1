package ru.otus.messagesystem.messagebuffers;

import ru.otus.messagesystem.MessageSystemException;
import ru.otus.messagesystem.PacketHandler;
import ru.otus.messagesystem.PacketProvider;
import ru.otus.messagesystem.RoleClass;
import ru.otus.messagesystem.packet.Destination;
import ru.otus.messagesystem.packet.Packet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Artem Gabbasov on 18.08.2017.
 */
public class MessageBuffersManager implements PacketProvider {
    private final Map<Destination, MessageBuffer> messageBufferMap = new ConcurrentHashMap<>();

    private final Map<PacketHandler, Set<MessageBuffer>> handlerMessageBufferMap = new ConcurrentHashMap<>();
    private final Map<MessageBuffer, Set<PacketHandler>> messageBufferHandlerMap = new ConcurrentHashMap<>();

    private final Map<PacketHandler, SemaphoreManager> semaphoreManagerMap = new ConcurrentHashMap<>();

    public void registerHandler(PacketHandler handler) {
        int bufferCount = 0;

        MessageBuffer personalBuffer = new MessageBufferImpl();

        Set<MessageBuffer> bufferSet = new HashSet<>();

        bufferSet.add(personalBuffer);
        messageBufferHandlerMap.put(personalBuffer, Collections.singleton(handler));
        bufferCount++;

        Arrays.stream(handler.getAgents())
                .forEach(agent -> messageBufferMap.put(agent, personalBuffer));

        for (RoleClass role : handler.getRoles()) {
            MessageBuffer roleBuffer = messageBufferMap.get(role);
            Set<PacketHandler> handlerSet;
            if (roleBuffer == null) {
                roleBuffer = new MessageBufferImpl();
                messageBufferMap.put(role, roleBuffer);
                handlerSet = new HashSet<>();
                messageBufferHandlerMap.put(roleBuffer, handlerSet);
            } else {
                handlerSet = messageBufferHandlerMap.get(roleBuffer);
            }

            bufferSet.add(roleBuffer);
            handlerSet.add(handler);
            bufferCount++;
        }

        handlerMessageBufferMap.put(handler, bufferSet);

        semaphoreManagerMap.put(handler, new SemaphoreManager(bufferCount - 1));
    }

    public Set<PacketHandler> getHandlers() {
        return handlerMessageBufferMap.keySet();
    }

    public void receive(Packet packet) throws MessageSystemException {
        MessageBuffer messageBuffer = messageBufferMap.get(packet.getDestination());
        if (messageBuffer != null) {
            messageBuffer.receive(packet);
            messageBufferHandlerMap.get(messageBuffer).forEach(handler -> semaphoreManagerMap.get(handler).release());
        } else {
            throw new MessageSystemException("Unrecognized destination: " + packet.getDestination().toString());
        }
    }

    @Override
    public Packet take(PacketHandler handler) throws InterruptedException {
        SemaphoreManager semaphoreManager = semaphoreManagerMap.get(handler);
        Packet packet = null;
        Set<MessageBuffer> messageBuffers = handlerMessageBufferMap.get(handler);
        while (packet == null) {
            semaphoreManager.init();
            for (MessageBuffer buffer : messageBuffers) {
                packet = buffer.poll();
                if (packet != null) {
                    break;
                }
                semaphoreManager.acquire();
            }
        }
        semaphoreManager.clear();
        return packet;
    }
}
