package test.quanta;

import dem.bounding.BoundedHandler;
import dem.quanta.Event;
import dem.quanta.Handler;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class QuantaTest {
    private int countOfSimple = 0;
    private int countOfSmart = 0;

    private Handler<Event> lazyHandler;
    private BoundedHandler<SmartEvent> smartHandler;
    private Handler unknownHandler;

    @Before
    public void setUp() {
        lazyHandler = new Handler<Event>() {
            public void handle(Event event) {
                System.out.println("Huh, there's something in the air!");
                countOfSimple++;
            }
        };
        smartHandler = new BoundedHandler<SmartEvent>(SmartEvent.class) {
            public void handle(SmartEvent event) {
                System.out.println("Oh, something smart!");
                countOfSmart++;
            }

        };

        lazyHandler.toString();
        smartHandler.toString();
        unknownHandler = smartHandler;
    }

    @Test
    public void testSimpleMessaging() {
        int a = new Random().nextInt(99);
        for (int i = 0; i < a; i++) {
            lazyHandler.handle(new Event() {
            });
        }
        assertEquals(a, countOfSimple);
    }

    @Test
    public void testSmartMessaging() {
        int a = new Random().nextInt(49);
        for (int i = 0; i < a; i++) {
            lazyHandler.handle(new SmartEvent());
            smartHandler.handle(new SmartEvent());
        }
        assertEquals(a, countOfSimple);
    }

    @Test
    public void testCheckedHandle() {
        int a = new Random().nextInt(49);
        for (int i = 0; i < a; i++) {
            lazyHandler.handle(new SmartEvent());
            smartHandler.handleIfPossible(new SmartEvent());
        }
        assertEquals(a, countOfSimple);
        assertEquals(a, countOfSmart);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHowWeFail() {
        Exception e = null;
        try {
            unknownHandler.handle(new Event() {
            });

        } catch (ClassCastException cce) {
            e = cce;
        } finally {
            assertNotNull(e);
        }
    }

    private static class SmartEvent implements Event {
    }
}
