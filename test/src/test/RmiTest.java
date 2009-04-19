package test;

import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.devg.dem.rmi.BindableHandler;
import ru.devg.dem.rmi.ObtainedRemoteHandler;
import ru.devg.dem.rmi.RemoteHandler;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.CollectedEvent;
import test.events.RemoteEvent;
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
    private static final int PORT = 62051;
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
        Handler<BaseEvent> h = new MyBaseHandler();

        System.out.println(h);
        r.rebind("test", new BindableHandler<RemoteEvent>(h));
    }

    @Test
    public void testRmi() throws IOException, AlreadyBoundException, NotBoundException {

        Remote x = r.lookup("test");
        if (x instanceof RemoteHandler) {
            RemoteHandler<RemoteEvent> remoteHandler = (RemoteHandler<RemoteEvent>) x;
            Handler<RemoteEvent> be = new ObtainedRemoteHandler<RemoteEvent>(remoteHandler);
            RemoteEvent event = new RemoteEvent() {
                private int a = new Random().nextInt();

            };
            be.handle(event);
            assertTrue(c.getString().length() == 1);
        } else {
            throw new RuntimeException();
        }
    }

    @AfterClass
    public static void tearDown() throws RemoteException, NotBoundException {
        r.unbind("test");
        rmir.destroy();
    }

    private static class MyBaseHandler extends BaseHandler<BaseEvent> {
        public MyBaseHandler() {
            super(RmiTest.c, BaseEvent.class, "+");
        }

        @Override
        public CollectedEvent translate(BaseEvent event) {
            System.out.println(event);
            System.out.println(MyBaseHandler.this);
            return super.translate(event);
        }

    }
}
