package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.filtering.TypeBoundedHandler;

import java.util.Random;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.0
 */
public class QuantsTest {
    private int countOfSimple = 0;
    private int countOfSmart = 0;

    private Handler<Event> lazyHandler;
    private TypeBoundedHandler<SmartEvent> smartHandler;
    private Handler unknownHandler;

    @Before
    public void setUp() {
        lazyHandler = new Handler<Event>() {
            public void handle(Event event) {
                System.out.println("Huh, there's something in the air!");
                countOfSimple++;
            }
        };
        smartHandler = new TypeBoundedHandler<SmartEvent>(SmartEvent.class) {
            public void handle(SmartEvent event) {
                System.out.println("Oh, something smart!");
                countOfSmart++;
            }

        };

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
    public void testHowWeFail() {
        Exception e = null;
        try {
            unknownHandler.handle(new Event() {});

        } catch (ClassCastException cce) {
            e = cce;
        } finally {
            assertNotNull(e);
        }
    }

    private static class SmartEvent implements Event {
    }
}
