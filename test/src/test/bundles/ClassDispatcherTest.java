package test.bundles;

import dem.bounding.BoundedHandler;
import dem.bundles.ClassDispatcher;
import org.junit.Assert;
import org.junit.Test;
import test.events.BaseEvent;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent2;
import test.handlers.BaseHandler;
import test.handlers.Collector;

public class ClassDispatcherTest {

    @Test
    public void test() {
        Collector c = new Collector();

        ClassDispatcher<BaseEvent> cd = new ClassDispatcher<BaseEvent>(BaseEvent.class);

        BoundedHandler<BaseEvent> bh =
                new BaseHandler<BaseEvent>(c, BaseEvent.class, "B");

        cd.addHandler(bh);
        cd.handle(new BaseEvent());
        Assert.assertTrue(c.getString().equals("B"));

        cd.removeHandler(bh);
        cd.removeHandler(bh);    //double remove should work as single
        cd.handle(new BaseEvent());
        Assert.assertTrue(c.getString().equals("B"));

        cd.addHandler(bh);
        cd.handle(new BaseEvent());
        Assert.assertTrue(c.getString().equals("BB"));

        BoundedHandler<SecondLevelEvent1> bh1 =
                new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "1");

        cd.addHandler(bh1);
        cd.handle(new SecondLevelEvent1());
        cd.handle(new BaseEvent());

        cd.removeHandler(bh1);

        cd.handle(new SecondLevelEvent1());

        Assert.assertTrue(c.getString().equals("BB1BB"));

        System.out.println(cd);
    }


    @Test
    public void add() {
        Collector c = new Collector();

        ClassDispatcher cd = new ClassDispatcher<SecondLevelEvent2>(SecondLevelEvent2.class);

        BoundedHandler<SecondLevelEvent2> bh =
                new BaseHandler<SecondLevelEvent2>(c, SecondLevelEvent2.class, "B");
        BoundedHandler<SecondLevelEvent1> bh1 =
                new BaseHandler<SecondLevelEvent1>(c, SecondLevelEvent1.class, "1");

        cd.addHandler(bh1);
        cd.addHandler(bh);
    }
}
