package test.translating;

import org.junit.Test;
import dem.translating.NoopTranslator;
import dem.translating.Translator;
import dem.quanta.Handler;
import test.events.BaseEvent;
import test.events.SecondLevelEvent2;
import test.handlers.Counter;

public class NoopTranslatorTest {
    @Test
    public void test(){
        Translator<BaseEvent, SecondLevelEvent2> translator;
        Handler<BaseEvent> target = Counter.getCounter();
        translator = new NoopTranslator<BaseEvent, SecondLevelEvent2>(target, SecondLevelEvent2.class);
        translator.handle(new SecondLevelEvent2());
        System.out.println(translator.toString());
    }
}
