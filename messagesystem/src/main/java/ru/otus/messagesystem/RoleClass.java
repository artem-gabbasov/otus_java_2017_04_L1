package ru.otus.messagesystem;

import ru.otus.messagesystem.packet.Destination;

/**
 * Created by Artem Gabbasov on 20.08.2017.
 */
public class RoleClass implements Destination {
    private final String name;

    public RoleClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
