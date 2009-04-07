package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
*/
public final class ObtainedRemoteHandler<E extends Event> implements Handler<E> {

    private final RemoteHandler<E> rh;

    public ObtainedRemoteHandler(RemoteHandler<E> rh) {
        this.rh = rh;
    }

    public void handle(E event) {
        try {
            rh.handleRemote(event);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
