package test.buffers;

import dem.buffers.EventCollection;
import dem.buffers.EventCollectionHandler;
import dem.quanta.Event;
import org.junit.Before;
import org.junit.Test;
import test.handlers.Counter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class EventCollectionHandlerTest {
    private EventCollectionHandler<Event> collectionHandler;
    private EventCollection<Event> collection;
    private Counter<Event> counter;
    private final Event event = new Event() {
    };

    @Before
    public void setUp() {
        counter = Counter.getCounter();
        collection = new EventCollection<Event>();
        collectionHandler = new EventCollectionHandler<Event>(counter);
        collectionHandler.toString();
    }

    @Test
    public void testHandling() {
        collection.handle(event);
        collectionHandler.handle(collection);
        assertEquals(counter.getCount(), 1);
    }

    @Test
    public void testSecondConstructor() {
        List<Event> oneEventList = Arrays.asList(event);
        EventCollection<Event> newCollection = new EventCollection<Event>(oneEventList);
        collectionHandler.handle(newCollection);
        assertEquals(counter.getCount(), 1);
    }

    @Test(expected = AssertionError.class)
    public void testNullEventFail() {
        List<Event> oneEventList = Arrays.asList(new Event[]{null});
        EventCollection<Event> newCollection = new EventCollection<Event>(oneEventList);
        collectionHandler.handle(newCollection);
    }


    @Test(expected = AssertionError.class)
    public void testNullCollectionFail() {
        //noinspection NullArgumentToVariableArgMethod
        collectionHandler.handle(null);
    }

    @Test
    public void testBasicOps() {
        collection.handle(event);
        collectionHandler.handle(collection);
        assertEquals(counter.getCount(), 1);
    }
}
