package test;

import dem.inclass.Handles;
import dem.inclass.InclassDispatcher;
import dem.inclass.Prioritized;
import dem.inclass.SuppressExceptions;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.quanta.Handler;
import junit.framework.Assert;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.handlers.BaseHandler;
import test.handlers.Collector;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
public class InclassDispatcherTest {

    @Test
    public void simpleTest() throws ClassIsUnbindableException {
        c.clear();

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

        Assert.assertTrue(c.getString().length() == 2);

        Handler<BaseEvent> h2 = new InclassDispatcher<BaseEvent>(new Child());
        System.out.println(h2);
        h2.handle(new SecondLevelEvent1());
        Assert.assertTrue(c.getString().length() == 6);

    }

    private final Collector c = new Collector();

    public final class WellFormedClass {

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> samePriority = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");


        @Handles(SecondLevelEvent1.class)
        @Prioritized(5)
        public Handler<SecondLevelEvent1> b = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "S1");

    }


    public class Parent {
        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "SLE1");

    }

    public final class Child extends Parent {
        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent2> a = new BaseHandler<SecondLevelEvent2>(c, SecondLevelEvent2.class, "SLE2");


        public void x() {
        }
    }

    @Test
    public void testExceptions() throws ClassIsUnbindableException {
        c.clear();

        class A {
            @SuppressExceptions
            @Prioritized(1)
            @Handles(SecondLevelEvent1.class)
            public Handler<SecondLevelEvent1> b = new Handler<SecondLevelEvent1>(){

                public void handle(SecondLevelEvent1 event) {
                    throw new RuntimeException();
                }
            };

            @Handles(SecondLevelEvent1.class)
            public Handler<SecondLevelEvent1> a = new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "a");
        }


        Handler<SecondLevelEvent1> h = new InclassDispatcher<SecondLevelEvent1>(new A());


        Assert.assertTrue(c.getString().length() ==0);
        h.handle(new SecondLevelEvent1());
        Assert.assertTrue(c.getString().length() ==1);

    }


}
