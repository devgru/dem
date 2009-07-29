package test;

import dem.bounding.BoundedHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent3;
import test.handlers.Counter;

public class BoundedHandlerTest {

    private BoundedHandler<SecondLevelEvent3> boundedHandler;
    private Counter<SecondLevelEvent3> counter = Counter.getCounter();
    private final BaseEvent baseEvent = new BaseEvent();
    private final BaseEvent event = new SecondLevelEvent3();


    @Before
    public void setUp() {
        boundedHandler = BoundedHandler.bound(counter, SecondLevelEvent3.class);
        boundedHandler.toString();
    }

    @Test
    public void testHandling() {
        boundedHandler.handleIfPossible(event);
        Assert.assertEquals(counter.getCount(), 1);
    }

    @Test
    public void testWrongType() {
        boundedHandler.handleIfPossible(baseEvent);
        boundedHandler.handleIfPossible(event);
        Assert.assertEquals(counter.getCount(), 1);
    }

    @Test(expected = AssertionError.class)
    public void testFail() {
        boundedHandler = BoundedHandler.bound(counter, null);
    }

    @Test(expected = AssertionError.class)
    public void testFail2() {
        boundedHandler = BoundedHandler.bound(null, SecondLevelEvent3.class);
    }
}
