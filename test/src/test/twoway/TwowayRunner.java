package test.twoway;

import org.junit.Test;
import dem.processing.BiConnector;
import dem.processing.external.BiProcessorStrategy;
import dem.processing.external.ExternalBiProcessor;
import test.twoway.DataEvent;
import test.twoway.ControlEvent;
import test.twoway.DataProducer;
import test.twoway.Controller;

public class TwowayRunner {

    @Test
    public void main() {
        BiConnector<DataEvent, ControlEvent> dataProducer = new DataProducer();
        Controller controller = new Controller();

        BiProcessorStrategy<DataEvent, ControlEvent> strategy = new BiProcessorStrategy<DataEvent, ControlEvent>() {

            int i = 0;

            public boolean processLeft(DataEvent event) {
                return ++i % 2 == 0;
            }

            public boolean processRight(ControlEvent event) {
                return true;
            }
        };



        controller.bp = new ExternalBiProcessor<DataEvent, ControlEvent>(controller, dataProducer, strategy);

        System.out.println(controller);
        controller.start();

        System.gc();
        System.gc();
    }


}
