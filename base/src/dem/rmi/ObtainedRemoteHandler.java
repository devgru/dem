package dem.rmi;

import dem.quanta.Handler;
import dem.quanta.Log;
import dem.remote.RemoteEvent;

import java.rmi.RemoteException;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public final class ObtainedRemoteHandler<E extends RemoteEvent>
        implements Handler<E> {

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

    @Override
    public String toString() {
        return "Obtained remote handler\n" + Log.offset("target is " + rh);

    }

}
