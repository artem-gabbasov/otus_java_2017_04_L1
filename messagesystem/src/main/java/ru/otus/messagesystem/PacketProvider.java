package ru.otus.messagesystem;

import ru.otus.messagesystem.PacketHandler;
import ru.otus.messagesystem.packet.Packet;

/**
 * Created by Artem Gabbasov on 19.08.2017.
 */
public interface PacketProvider {
    Packet take(PacketHandler handler) throws InterruptedException;
}
