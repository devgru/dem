package test.quanta;

import dem.quanta.Event;
import dem.quanta.Handler;
import dem.quanta.Source;
import org.junit.Test;

public class SourceTest {
    @Test
    public void testBasicOps() {
        Source<Event> s = new Source<Event>(new Handler<Event>() {
            public void handle(Event event) {
            }
        }) {
            {
                fire(new Event() {
                });
            }
        };
        s.toString();
    }


    @Test(expected = AssertionError.class)
    public void testBadCreation() {
        Handler<Event> h = null;
        Source<Event> source = new Source<Event>(null) {
        };
    }

    @Test(expected = AssertionError.class)
    public void testFireNull() {
        Source<Event> s = new Source<Event>(new Handler<Event>() {
            public void handle(Event event) {
            }
        }) {
            {
                fire(null);
            }
        };
    }
}
