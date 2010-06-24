package test;

import dem.exceptions.ExceptionCatcher;
import dem.exceptions.ExceptionEvent;
import dem.quanta.Event;
import dem.quanta.Handler;
import org.junit.Before;
import org.junit.Test;
import test.handlers.Counter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExceptionsTest {
    private Counter counter;
    private static final Event event = new Event() {
    };
    Thrower thrower = new Thrower();

    private ExceptionCatcher<Event> exceptionCatcher = null;

    @Before
    public void init() {
        counter = Counter.getCounter();
    }

    @Test
    public void testToString() {
        exceptionCatcher = new ExceptionCatcher<Event>(counter);
        exceptionCatcher.toString();
    }

    @Test(expected = AssertionError.class)
    public void testNullConstructor() {//here we rely on Source
        exceptionCatcher = new ExceptionCatcher<Event>(null);
    }

    @Test
    public void testNormal() {
        exceptionCatcher = new ExceptionCatcher<Event>(counter);
        exceptionCatcher.handle(event);
        exceptionCatcher.handleIfPossible(event);
    }

    @Test
    public void testFail() {
        exceptionCatcher = new ExceptionCatcher<Event>(thrower);
        exceptionCatcher.handle(event);
        exceptionCatcher.handleIfPossible(event);
    }

    @Test
    public void testFailWithHole() {
        exceptionCatcher = new ExceptionCatcher<Event>(thrower, counter);
        exceptionCatcher.handle(event);
        assertEquals(counter.getCount(), 1);
        exceptionCatcher.handleIfPossible(event);
        assertEquals(counter.getCount(), 2);
    }

    @Test
    public void testGetException() {
        exceptionCatcher = new ExceptionCatcher<Event>(thrower, new Handler<ExceptionEvent>() {
            public void handle(ExceptionEvent event) {
                System.out.println(event);
                assertTrue(event.getException() instanceof RuntimeException);
            }
        });
        exceptionCatcher.handle(event);
    }

    public final class Thrower implements Handler<Event> {
        public void handle(Event event) {
            throw new RuntimeException();
        }
    }
}
