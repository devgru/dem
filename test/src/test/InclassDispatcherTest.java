package test;

import org.junit.Assert;
import org.junit.Test;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.inclass.InclassDispatcher;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.handlers.BaseHandler;
import test.handlers.Collector;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
public class InclassDispatcherTest {
    @Test
    public void simpleTest() throws ClassIsUnbindableException {
        WellFormedClass d = new WellFormedClass();
        Handler<BaseEvent> h = new InclassDispatcher<BaseEvent>(d);

        h.handle(new SecondLevelEvent1());
        h.handle(new BaseEvent());
    }

    @Test
    public void testBadClass() {
        ClassIsUnbindableException exc;
        exc = null;
        try {
            new InclassDispatcher<BaseEvent>(new BadFormedClass());
        } catch (ClassIsUnbindableException e) {
            exc = e;
        } finally {
            Assert.assertNotNull(exc);
        }

        exc = null;
        try {
            new InclassDispatcher<BaseEvent>(new ClassWithPrivates());
        } catch (ClassIsUnbindableException e) {
            exc = e;
        } finally {
            Assert.assertNotNull(exc);
        }
    }

    private final Collector c = new Collector();

    public final class WellFormedClass {

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

    }

    public final class BadFormedClass {

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");
    }

    public final class ClassWithPrivates {

        @Handles(SecondLevelEvent2.class)
        private Filter<SecondLevelEvent1> b = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

    }

}
