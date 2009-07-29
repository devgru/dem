package test.buffers;

import dem.buffers.EventBuffer;
import dem.quanta.Event;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import test.handlers.Counter;

import java.util.LinkedList;
import java.util.List;

public class BufferTest {
    private Counter<Event> target;
    private EventBuffer<Event> eventBuffer;
    private static final Event event = new Event() {
    };

    @Before
    public void init() {
        target = Counter.getCounter();
        eventBuffer = new EventBuffer<Event>(target);
        eventBuffer.toString();
    }

    @Test
    public void testBuffering() {
        eventBuffer.reset();
        eventBuffer.handle(event);
        assertEquals(target.getCount(), 0L);

        List<Event> collectedEvents = new LinkedList<Event>();
        eventBuffer.export(collectedEvents);
        assertEquals(collectedEvents.size(), 1);
        assertEquals(collectedEvents.get(0), event);
    }

    @Test(expected = AssertionError.class)
    public void testFailOnNull() {
        eventBuffer.reset();
        eventBuffer.handle(null);
    }

    @Test
    public void testHandlingWhileLocked() {
        eventBuffer.handle(event);
        eventBuffer.release();
        assertEquals(target.getCount(), 1);
        eventBuffer.lock();
        eventBuffer.handle(event);
        eventBuffer.release();
        assertEquals(target.getCount(), 1);
    }

    @Test
    public void testLock() {
        assertTrue(eventBuffer.lock());
        assertTrue(eventBuffer.isLocked());
    }


    @Test
    public void testDoubleLockPrevention() {
        assertTrue(eventBuffer.lock());
        assertTrue(eventBuffer.isLocked());
        assertFalse(eventBuffer.lock());
        assertTrue(eventBuffer.isLocked());
    }


    @Test
    public void testStopage() {
        eventBuffer.reset();
        eventBuffer.handle(event);
        assertEquals(target.getCount(), 0L);
    }


}
