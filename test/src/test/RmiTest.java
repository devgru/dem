package test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.devg.dem.pipes.rmi.BindableHandler;
import ru.devg.dem.pipes.rmi.ObtainedRemoteHandler;
import ru.devg.dem.pipes.rmi.RemoteHandler;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.handlers.BaseHandler;
import test.handlers.Collector;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;


/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public class RmiTest {

    private static final Registry r;
    private static final int PORT = new Random().nextInt(60000) + 1000;
    private static final Process rmir;
    private static final Collector c = new Collector();

    static {
        try {
            r = LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            rmir = new ProcessBuilder("rmiregistry", Integer.toString(PORT)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void prepare() throws IOException {


        Handler<BaseEvent> h = new BaseHandler<BaseEvent>(c, BaseEvent.class, "+");

        r.rebind("test", new BindableHandler<BaseEvent>(h));
    }

    @AfterClass
    public static void tearDown() throws RemoteException, NotBoundException {
        r.unbind("test");
        rmir.destroy();
    }

    @Test
    public void testRmi() throws IOException, AlreadyBoundException, NotBoundException {

        Remote x = r.lookup("test");
        if (x instanceof RemoteHandler) {
            RemoteHandler<BaseEvent> remoteHandler = (RemoteHandler<BaseEvent>) x;
            Handler<BaseEvent> be = new ObtainedRemoteHandler<BaseEvent>(remoteHandler);
            be.handle(new BaseEvent());
            Assert.assertTrue(c.getString().length() == 1);

        }
    }
}
