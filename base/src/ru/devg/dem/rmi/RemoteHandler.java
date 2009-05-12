package ru.devg.dem.rmi;

import ru.devg.dem.remote.RemoteEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.175
 */
public interface RemoteHandler<E extends RemoteEvent> extends Remote {
    public void handleRemote(E event) throws RemoteException;
}
