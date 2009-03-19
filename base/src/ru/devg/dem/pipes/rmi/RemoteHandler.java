package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public interface RemoteHandler<E extends Event> extends Remote {
    public void handleRemote(E event) throws RemoteException;
}
