package test;

import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.handlers.Collector;
import test.handlers.BaseHandler;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.structures.dispatchers.inclass.Handles;
import ru.devg.dem.structures.dispatchers.inclass.InclassDispatcher;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
public class InclassDispatcherTest {
    @Test
    public void testPriorities(){
        Dolly d = new Dolly();
        Handler<BaseEvent> h = new InclassDispatcher<BaseEvent>(d);

        h.handle(new SecondLevelEvent1());
        h.handle(new BaseEvent());
    }

    public static final class Dolly {
        private final Collector c = new Collector();

        private Dolly() {
        }

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c,SecondLevelEvent1.class,"SLE1");
    }

}
