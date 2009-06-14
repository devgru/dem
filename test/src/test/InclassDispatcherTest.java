package test;

import org.junit.Test;
import dem.inclass.Handles;
import dem.inclass.InclassDispatcher;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.quanta.Handler;
import dem.translating.TranslatorStrategy;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.handlers.BaseHandler;
import test.handlers.Collector;

import junit.framework.Assert;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public class InclassDispatcherTest {

    @Test
    public void simpleTest() throws ClassIsUnbindableException {
        WellFormedClass d = new WellFormedClass();
        Handler<BaseEvent> h = new InclassDispatcher<BaseEvent>(d);

        ClassIsUnbindableException exc = null;
        try {
            new InclassDispatcher<BaseEvent>(d, true);
        } catch (ClassIsUnbindableException e) {
            exc = e;
        } finally {
            Assert.assertTrue(exc != null);
        }

        h.handle(new SecondLevelEvent1());
        h.handle(new BaseEvent());

        assert c.getString().length() == 1;
    }

    private final Collector c = new Collector();

    public final class WellFormedClass {

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

        @Handles(value=SecondLevelEvent1.class, translator = TranslatorStrategy.class,priority = 0)
        public Handler<SecondLevelEvent1> b = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

    }




}
