package ru.otus.messagesystem;

import ru.otus.messagesystem.packet.Packet;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public interface MessageSystem {
    void addHandlers(List<Function<PacketProvider, PacketHandler>> handlerFactories);
    void start();
    void send(Packet packet);
}
