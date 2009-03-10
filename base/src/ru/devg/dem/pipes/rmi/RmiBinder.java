package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.17
 */
public class RmiBinder {
    public static <E extends Event> void bind(String name, final Handler<E> h)
            throws MalformedURLException, AlreadyBoundException, RemoteException {
        Naming.bind(name, new Connector<E>(h));
    }

    public static final class Connector<E extends Event> implements RemoteHandler<E> {
        private final Handler<E> h;

        public Connector(Handler<E> target) {
            this.h = target;
        }

        public void handleRemote(E event) throws RemoteException {
            h.handle(event);
        }
    }

}
