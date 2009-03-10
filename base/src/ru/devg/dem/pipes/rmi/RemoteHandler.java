package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;

import java.rmi.RemoteException;
import java.rmi.Remote;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 04.01.2009
 * Time: 23:56:23
 */
public interface RemoteHandler<E extends Event> extends Remote {
    public void handleRemote(E event) throws RemoteException;
}
