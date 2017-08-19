package ru.otus.messagesystem.impl;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemException;
import ru.otus.messagesystem.PacketHandler;
import ru.otus.messagesystem.packet.Packet;
import ru.otus.messagesystem.messagebuffers.MessageBuffersManager;
import ru.otus.messagesystem.PacketProvider;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public class MessageSystemImpl implements MessageSystem {
    private final BlockingQueue<Packet> outbox = new LinkedBlockingQueue<>();

//    private final List<PacketHandler> handlers = new CopyOnWriteArrayList<>();
//    private final Set<RoleClass> supportedRoles = new CopyOnWriteArraySet<>();
//    private final Map<Destination, Receiver> receiverMap = new ConcurrentHashMap<>();

    private final ExecutorService executorService;
    private final MessageBuffersManager messageBuffersManager = new MessageBuffersManager();

    public MessageSystemImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void addHandlers(List<Function<PacketProvider, PacketHandler>> handlerFactories) {
        handlerFactories.forEach(factory -> messageBuffersManager.registerHandler(factory.apply(messageBuffersManager)));
//        Set<RoleClass> newRoles = new HashSet<>();
//        handlers.forEach(handler -> {
//            newRoles.addAll(handler.getRoles());
//            registerAgents(handler);
//        });
//        newRoles.removeAll(supportedRoles);
//        newRoles.forEach(role -> register(role, messageBuffersManager.createMessageBuffer(role)));
//        supportedRoles.addAll(newRoles);
//        this.handlers.addAll(handlers);
//        handlers.forEach(executorService::submit);
    }

    @Override
    public void start() {
        messageBuffersManager.getHandlers().forEach(executorService::submit);
    }

//    private void register(Destination destination, Receiver receiver) {
//        receiverMap.put(destination, receiver);
//    }
//
//    private void registerAgents(PacketHandler handler) {
//        Arrays.stream(handler.getAgents()).forEach(agent -> register(agent, handler));
//    }

    @Override
    public void send(Packet packet) {
        outbox.add(packet);
    }

//    private Receiver resolveReceiver(Destination destination) {
//        return receiverMap.get(destination);
//    }

    /**
     * Created by Artem Gabbasov on 18.08.2017.
     */
    private class Mailer implements Callable<Void> {
        @Override
        public Void call() throws MessageSystemException {
            while (true) {
                try {
                    Packet packet = outbox.take();
                    messageBuffersManager.receive(packet);
                } catch (InterruptedException e) {
                    e.printStackTrace(); // TODO: log: there are n unhandled packets
                }
            }
        }
    }
}
