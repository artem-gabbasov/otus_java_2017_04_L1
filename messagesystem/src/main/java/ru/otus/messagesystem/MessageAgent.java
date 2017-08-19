package ru.otus.messagesystem;

import javafx.util.Pair;
import ru.otus.messagesystem.packet.Destination;
import ru.otus.messagesystem.packet.message.Message;
import ru.otus.messagesystem.packet.Packet;

import java.util.concurrent.Executors;

/**
 * Created by Artem Gabbasov on 11.08.2017.
 */
public interface MessageAgent extends Destination {
    MessageSystem MESSAGE_SYSTEM = MessageSystemFactory.createMessageSystem();
}
