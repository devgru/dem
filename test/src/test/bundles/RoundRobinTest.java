package test.bundles;

import dem.bundles.RoundRobin;
import dem.quanta.Handler;
import dem.stuff.NoopHandler;
import org.junit.Assert;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.events.SecondLevelEvent3;
import test.handlers.BaseHandler;
import test.handlers.Collector;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RoundRobinTest {

    @Test
    public void testCreation() {
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>(
                (new NoopHandler<BaseEvent>())
        );

        System.out.println(rrb);
    }

    @Test
    public void testCreation2() {
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>(
                Arrays.asList(new NoopHandler<BaseEvent>())
        );

        System.out.println(rrb);
    }

    @Test(expected = AssertionError.class)
    public void testCreationFail() {
        new RoundRobin<BaseEvent>(
                (List<Handler<BaseEvent>>) null
        );
    }

    @Test(expected = AssertionError.class)
    public void testAssertion() {
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();

        rrb.handle(null);
    }


    @Test(expected = AssertionError.class)
    public void testAssertion2() {
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();
        rrb.addHandler(null);
    }

    @Test
    public void testRemove() {
        Collector c = new Collector();
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();
        BaseHandler<BaseEvent> eventBaseHandler = new BaseHandler<BaseEvent>(c, BaseEvent.class, "a");
        rrb.addHandler(eventBaseHandler);
        rrb.removeHandler(null);
        rrb.handle(new BaseEvent());
        Assert.assertEquals("a", c.getString());
        rrb.removeHandler(new NoopHandler<BaseEvent>());
        rrb.handle(new BaseEvent());
        Assert.assertEquals("aa", c.getString());
        rrb.removeHandler(eventBaseHandler);
        rrb.removeHandler(eventBaseHandler);
        rrb.handle(new BaseEvent());
        Assert.assertEquals("aa", c.getString());
    }

    @Test
    public void testHandling() {
        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();
        rrb.handle(new BaseEvent());
    }

    @Test
    public void test() {
        Collector c = new Collector();

        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();
        rrb.addHandler(new BaseHandler<BaseEvent>(c, BaseEvent.class, "a"));
        rrb.handle(new SecondLevelEvent1());
        rrb.handle(new SecondLevelEvent2());
        assertTrue(c.getString().equals("aa"));

        BaseHandler<BaseEvent> b = new BaseHandler<BaseEvent>(c, BaseEvent.class, "b");
        rrb.addHandler(b);
        rrb.handle(new SecondLevelEvent2());
        rrb.handle(new SecondLevelEvent2());
        rrb.handle(new SecondLevelEvent2());
        assertTrue(c.getString().equals("aabab"));

        rrb.removeHandler(b);
        rrb.handle(new SecondLevelEvent3());
        rrb.handle(new SecondLevelEvent3());
        rrb.handle(new SecondLevelEvent3());
        rrb.handle(new SecondLevelEvent3());
        rrb.handle(new SecondLevelEvent3());
        assertTrue(c.getString().equals("aababaaaaa"));

        System.out.println(rrb.toString());
    }

}
