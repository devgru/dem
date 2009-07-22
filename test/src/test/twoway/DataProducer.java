package test.twoway;

import dem.processing.BiConnector;

public class DataProducer extends BiConnector<DataEvent, ControlEvent> {

    public void handle(ControlEvent event) {
        System.out.println("oh, they control me");
        fire(new DataEvent());
    }
}
