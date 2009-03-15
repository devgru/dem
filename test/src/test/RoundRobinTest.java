package test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import ru.devg.dem.structures.dispatchers.RoundRobin;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.events.SecondLevelEvent3;
import test.handlers.BaseHandler;
import test.handlers.Collector;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
public class RoundRobinTest {
    @Test
    public void test() {
        Collector c = new Collector();

        RoundRobin<BaseEvent> rrb = new RoundRobin<BaseEvent>();
        rrb.addHandler(new BaseHandler<BaseEvent>(c,BaseEvent.class, "a"));
        rrb.handle(new SecondLevelEvent1());
        rrb.handle(new SecondLevelEvent2());
        assertTrue(c.getString().equals("aa"));

        BaseHandler<BaseEvent> b = new BaseHandler<BaseEvent>(c,BaseEvent.class, "b");
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
    }

}
