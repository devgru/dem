package test;

import org.junit.Test;
import dem.bundles.Broadcaster;
import dem.bundles.RoundRobin;
import dem.quanta.Source;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.179
 */
public class SourceTest {
    @Test
    public void test() {

    }

    private final static class Tested extends Source<BaseEvent> {

        private Tested(int i) {
            super(new RoundRobin<BaseEvent>());

            fire(new SecondLevelEvent1());
        }

        private Tested(String i) {
            super(new Broadcaster<BaseEvent>());

            fire(new SecondLevelEvent1());
        }
    }
}
