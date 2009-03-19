package ru.devg.dem.pipes.rmi;

import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.175
 */
public class RmiGetter {

    @SuppressWarnings("unchecked")
    public static <E extends Event> Handler<E> obtain(String name)
            throws MalformedURLException, NotBoundException, RemoteException {

        return new Connector<E>((RemoteHandler<E>) Naming.lookup(name));
    }

    private static final class Connector<E extends Event> implements Handler<E> {

        private final RemoteHandler<E> rh;

        private Connector(RemoteHandler<E> rh) {
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
}
