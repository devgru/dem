package dem.rmi;

import dem.quanta.Handler;
import dem.remote.RemoteEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public final class BindableHandler<E extends RemoteEvent>
        extends UnicastRemoteObject implements RemoteHandler<E> {

    private final Handler<? super E> target;

    public BindableHandler(Handler<? super E> target) throws RemoteException {
        super();
        this.target = target;
    }

    public void handleRemote(E event) throws RemoteException {
        try {
            target.handle(event);
        } catch (Exception e) {
            throw new RemoteException("Handling failed", e);
        }
    }
}
