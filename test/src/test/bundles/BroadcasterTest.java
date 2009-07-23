package test.bundles;

import dem.bundles.Broadcaster;
import dem.quanta.Handler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent2;
import test.events.SecondLevelEvent3;
import test.handlers.Counter;

import java.util.LinkedList;

public class BroadcasterTest {

    private final Counter<BaseEvent> c1 = Counter.getCounter();
    private final Counter<BaseEvent> c2 = Counter.getCounter();
    private Handler<SecondLevelEvent2> broadcaster =
            new Broadcaster<SecondLevelEvent2>(c1, c2);


    @Before
    public void setUp() {
        System.out.println(broadcaster.toString());
    }

    @Test
    public void test() {
        broadcaster.handle(new SecondLevelEvent3());
        Assert.assertEquals(c1.getCount(), c2.getCount());
    }

    @Test
    public void testAddAndRemove() {
        ((Broadcaster<SecondLevelEvent2>) broadcaster).removeHandler(c1);
        broadcaster.handle(new SecondLevelEvent3());
        ((Broadcaster<SecondLevelEvent2>) broadcaster).addHandler(c1);
        ((Broadcaster<SecondLevelEvent2>) broadcaster).removeHandler(c2);
        broadcaster.handle(new SecondLevelEvent2());
        Assert.assertEquals(c1.getCount(), c2.getCount());
    }

    @Test(expected = AssertionError.class)
    public void testAddNull() {
        ((Broadcaster<SecondLevelEvent2>) broadcaster).addHandler(null);

    }

    @Test(expected = AssertionError.class)
    public void testFailOnNull() {
        broadcaster.handle(null);
    }

    @Test
    public void testRemoveNull() {
        ((Broadcaster<SecondLevelEvent2>) broadcaster).removeHandler(null);
    }


    @Test
    public void testAlternativeConstructor() {
        LinkedList<Handler<? super SecondLevelEvent2>> list = new LinkedList<Handler<? super SecondLevelEvent2>>();
        list.add(c2);
        list.add(c1);
        broadcaster = new Broadcaster<SecondLevelEvent2>(list);
        Assert.assertEquals(c1.getCount(), c2.getCount());
    }
}
