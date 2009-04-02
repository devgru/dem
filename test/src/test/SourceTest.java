package test;

import org.junit.Test;
import ru.devg.dem.bundles.Broadcaster;
import ru.devg.dem.bundles.RoundRobin;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.sources.MultiTargetedSource;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.179
 */
public class SourceTest {
    @Test
    public void test() {

    }

    private final static class Tested extends MultiTargetedSource<Handler<BaseEvent>, BaseEvent> {

        private Tested(int i) {
            super(new RoundRobin<BaseEvent>());

            fire(new SecondLevelEvent1());
        }


        private Tested(String i) {
            super(new Broadcaster<BaseEvent>());

            fire(new SecondLevelEvent1());
        }

/*
        private Tested(Tested i ) {
            super(BASE_EVENT_BROADCASTER);

            fire(new SecondLevelEvent1());
        }*/


    }
}
