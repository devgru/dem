package test;

import dem.quanta.Handler;
import dem.rmi.BindableHandler;
import dem.rmi.ObtainedRemoteHandler;
import dem.rmi.RemoteHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
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

import static org.junit.Assert.assertTrue;


public class RmiTest {

    private static final Registry r;
    private static final int PORT = Math.max(new Random().nextInt() % 64512 + 1024, 1999);
    private static final Collector c = new Collector();

    static {
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            registry = null;
            Assert.fail();
        }
        r = registry;

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
            be.handle(event);
            System.out.println(be);
            assertTrue(c.getString().length() == 2);
        } else {
            Assert.fail();
        }
    }

    @AfterClass
    public static void tearDown() throws RemoteException, NotBoundException {
        r.unbind("test");
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
