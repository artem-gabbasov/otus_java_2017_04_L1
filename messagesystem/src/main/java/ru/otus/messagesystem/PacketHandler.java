package ru.otus.messagesystem;

import ru.otus.messagesystem.packet.message.*;
import ru.otus.messagesystem.messagebuffers.ReflectionHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Artem Gabbasov on 12.08.2017.
 */
public abstract class PacketHandler implements Runnable, Role {
    private final PacketProvider packetProvider;

    private final Set<RoleClass> roles = Collections.unmodifiableSet(findRoles());

    public PacketHandler(PacketProvider packetProvider) {
        this.packetProvider = packetProvider;
    }

    @Override
    public void run() {
        try {
            while (true) {
                dispatch(packetProvider.take(this).getMessage());
            }
        } catch (InterruptedException e) {
            onInterrupt(e);
        } catch (MessageParameterNotFoundException e) {
            e.printStackTrace();
        } catch (WrongMessageParameterTypeException e) {
            e.printStackTrace();
        } catch (UnknownMessageTypeException e) {
            e.printStackTrace();
        }
    }

    protected abstract void dispatch(Message message) throws UnknownMessageTypeException, MessageParameterNotFoundException, WrongMessageParameterTypeException;

    protected void onInterrupt(InterruptedException e) {
        e.printStackTrace();
    }

    protected Set<RoleClass> findRoles() {
        return new HashSet<>(
                ReflectionHelper.listSubInterfaces(this.getClass(), Role.class).stream()
                    .map(clazz -> new RoleClass(clazz.getSimpleName()))
                    .collect(Collectors.toSet())
        );
    }

    public Set<RoleClass> getRoles() {
        return roles;
    }

    public abstract MessageAgent[] getAgents();
}
