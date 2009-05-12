package test.twoway;

import org.junit.Test;
import ru.devg.dem.processing.BiConnector;
import ru.devg.dem.processing.external.BiProcessorStrategy;
import ru.devg.dem.processing.external.ExternalBiProcessor;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public class Runner {

    @Test
    public void main() {
        BiConnector<DataEvent, ControlEvent> dataProducer = new DataProducer();
        Controller controller = new Controller();

        BiProcessorStrategy<DataEvent, ControlEvent> strategy = new BiProcessorStrategy<DataEvent, ControlEvent>() {

            int i = 0;

            protected boolean processLeft(DataEvent event) {
                return ++i % 2 == 0;
            }

            protected boolean processRight(ControlEvent event) {
                return true;
            }
        };


        new ExternalBiProcessor<DataEvent, ControlEvent>(controller, dataProducer, strategy);

        controller.start();
    }


}
