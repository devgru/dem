package test.bundles;

import dem.bounding.BoundedHandler;
import dem.bounding.Filter;
import dem.inclass.Handles;
import dem.inclass.InclassDispatcher;
import dem.inclass.annotations.Prioritized;
import dem.inclass.annotations.SuppressExceptions;
import dem.inclass.annotations.Translated;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.quanta.Handler;
import dem.translating.external.NoopTranslatorStrategy;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.handlers.Counter;

import static junit.framework.Assert.assertEquals;

public class InclassDispatcherTest {

    @Before
    public void init() {
        c1 = Counter.getCounter();
        c2 = Counter.getCounter();
    }

    @Test
    public void testFilteredFields() throws ClassIsUnbindableException {
        class Filtered {
            @Handles(SecondLevelEvent1.class)
            public Filter<SecondLevelEvent1> filter = BoundedHandler.bound(c1, SecondLevelEvent1.class);
        }
        Handler<BaseEvent> handler = new InclassDispatcher<BaseEvent>(new Filtered());
        handler.handle(new BaseEvent());
        handler.handle(new SecondLevelEvent1());
        assertEquals(c1.getCount(), 1);
    }

    @Test
    public void testTranslated() throws ClassIsUnbindableException {
        class TranslatedClass {
            @Handles(SecondLevelEvent1.class)
            @Translated(strategy = NoopTranslatorStrategy.class, bound = SecondLevelEvent1.class)
            public Filter<BaseEvent> filter = BoundedHandler.bound(c0, BaseEvent.class);
        }
        Handler<BaseEvent> handler = new InclassDispatcher<BaseEvent>(new TranslatedClass());
        handler.handle(new BaseEvent());
        assertEquals(c0.getCount(), 0);
        handler.handle(new SecondLevelEvent1());
        assertEquals(c0.getCount(), 1);
    }

    @Test
    public void simpleTest() throws ClassIsUnbindableException {

        WellFormedClass d = new WellFormedClass();
        Handler<BaseEvent> h = new InclassDispatcher<BaseEvent>(d);
        h.handle(new BaseEvent());
        h.handle(new SecondLevelEvent1());
        h.handle(new SecondLevelEvent2());
        assertEquals(c1.getCount(), 1);
        assertEquals(c2.getCount(), 1);
        System.out.println(h.toString()
        );
    }

    @Test
    public void asd() throws ClassIsUnbindableException {
        Handler<BaseEvent> h2 = new InclassDispatcher<BaseEvent>(new Child());
        assertEquals(c1.getCount(), 0);
        assertEquals(c2.getCount(), 0);
        System.out.println(h2);
        h2.handle(new SecondLevelEvent1());
        assertEquals(c2.getCount(), 1);
        assertEquals(c1.getCount(), 0);
    }

    @Test(expected = ClassIsUnbindableException.class)
    public void samePriorityTest() throws ClassIsUnbindableException {
        WellFormedClass d = new WellFormedClass();
        new InclassDispatcher<BaseEvent>(d, true);
    }

    private Counter<BaseEvent> c0 = Counter.getCounter();
    private Counter<SecondLevelEvent1> c1 = Counter.getCounter();
    private Counter<SecondLevelEvent2> c2 = Counter.getCounter();

    public final class WellFormedClass {

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> sample = c1;

        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> samePriority;

        public Handler<SecondLevelEvent1> fieldWithoutAnnotation;

        public void methodWithoutAnnotation() {
        }

        @Handles(SecondLevelEvent2.class)
        public void customMethod(SecondLevelEvent2 sle2) {
            c2.handle(sle2);
        }
    }


    public class Parent {
        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent1> a = c1;
    }

    public final class Child extends Parent {
        @Handles(SecondLevelEvent1.class)
        public Handler<SecondLevelEvent2> a = c2;

    }

    @Test
    public void testExceptions() throws ClassIsUnbindableException {

        class A {
            @SuppressExceptions
            @Prioritized(1)
            @Handles(SecondLevelEvent1.class)
            public Handler<SecondLevelEvent1> b = new Handler<SecondLevelEvent1>() {

                public void handle(SecondLevelEvent1 event) {
                    throw new RuntimeException();
                }
            };

            @Handles(SecondLevelEvent1.class)
            public Handler<SecondLevelEvent1> a = c1;
        }


        Handler<SecondLevelEvent1> h = new InclassDispatcher<SecondLevelEvent1>(new A());


        Assert.assertTrue(c1.getCount() == 0);
        h.handle(new SecondLevelEvent1());
        Assert.assertTrue(c1.getCount() == 1);

    }


}
