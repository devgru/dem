package test.twoway;

import ru.devg.dem.processing.BiConnector;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public class DataProducer extends BiConnector<DataEvent, ControlEvent> {

    public void handle(ControlEvent event) {
        System.out.println("oh, they control me");
        fire(new DataEvent());
    }
}
