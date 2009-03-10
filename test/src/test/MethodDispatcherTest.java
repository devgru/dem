package test;

import org.junit.Test;
import ru.devg.dem.bundles.one2one.MethodDispatcher;
import ru.devg.dem.bundles.one2one.MultiHandler;
import ru.devg.dem.bundles.one2one.MultiHandlerImpl;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.quanta.Event;
import test.events.BaseEvent;
import test.events.SecondLevelEvent2;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent3;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.0
 */
public class MethodDispatcherTest {

    @Test
    public void tryTest() {
        TestedClass testedClass = new TestedClass();
        MethodDispatcher<BaseEvent> md = new MethodDispatcher<BaseEvent>(testedClass);

        md.handle(new BaseEvent());
        md.handle(new SecondLevelEvent1());
        md.handle(new SecondLevelEvent2());
        md.handle(new SecondLevelEvent3());
    }

    public static final class TestedClass extends MultiHandlerImpl<BaseEvent> {

        @MultiHandler.Handles(SecondLevelEvent1.class)
        public Handler<?> dsa = new Yielder();

        private TestedClass() {
            super(BaseEvent.class);
        }

    }

    private static class Yielder implements Handler{
        public void handle(Event event) {
            System.out.println("Handled " +event.getClass().getSimpleName()+ " by" + this);
        }
    }
}
