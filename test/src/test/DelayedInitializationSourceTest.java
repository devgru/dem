package test;

import dem.quanta.Event;
import dem.mutables.DelayedInitializationSource;
import org.junit.Test;
import org.junit.Assert;
import test.handlers.Counter;

public class DelayedInitializationSourceTest {
    private static final Counter<Event> counter = Counter.getCounter();

    @Test(expected = AssertionError.class)
    public void testNullFail() {
        new DelayedInitializationSource<Event>() {
            {
                setTarget(null);
            }
        };
    }

    @Test
    public void testNormalCreation() {
        DelayedInitializationSource<Event> dis = new DelayedInitializationSource<Event>() {
            {
                setTarget(counter);
                fire(new Event() {
                });

            }
        };
        System.out.println(dis.toString());
        Assert.assertEquals(counter.getCount(), 1);
    }

        @Test(expected = RuntimeException.class)
    public void testDoubleCreationFail() {
        DelayedInitializationSource<Event> dis = new DelayedInitializationSource<Event>() {
            {
                setTarget(counter);
                setTarget(counter);
            }
        };
    }
}
