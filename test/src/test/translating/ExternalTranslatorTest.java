package test.translating;

import dem.quanta.Handler;
import dem.translating.external.ExternalTranslator;
import dem.translating.external.TranslatorStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.events.SecondLevelEvent1;
import test.events.SecondLevelEvent3;
import test.handlers.Counter;

public class ExternalTranslatorTest {

    private ExternalTranslator<SecondLevelEvent3, SecondLevelEvent1> translator;
    private Counter<SecondLevelEvent3> counter = Counter.getCounter();
    private final SecondLevelEvent1 event1 = new SecondLevelEvent1();
    private final SecondLevelEvent3 event3 = new SecondLevelEvent3();

    @Before
    public void setUp() {
        Handler<SecondLevelEvent3> a = counter;
        Class<SecondLevelEvent1> b = SecondLevelEvent1.class;
        TranslatorStrategy<? extends SecondLevelEvent3, ? super SecondLevelEvent1> c = new TranslatorStrategy<SecondLevelEvent3, SecondLevelEvent1>() {
            public SecondLevelEvent3 translate(SecondLevelEvent1 secondLevelEvent1) {
                return new SecondLevelEvent3();
            }
        };
        translator = new ExternalTranslator<SecondLevelEvent3, SecondLevelEvent1>(a, b, c);
        System.out.println(translator.toString());
    }

    @Test
    public void test() {
        translator.handle(event1);
        Assert.assertEquals(counter.getCount(), 1);
    }

}
