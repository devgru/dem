package test.twoway;

import ru.devg.dem.quanta.Source;
import ru.devg.dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.182
 */
public class DataProducer extends Source<DataEvent> implements Handler<ControlEvent> {
    public DataProducer(Handler<? super DataEvent> target) {
        super(target);
    }

    public void handle(ControlEvent event) {
        
    }
}
