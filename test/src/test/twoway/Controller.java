package test.twoway;

import org.junit.Assert;
import ru.devg.dem.processing.BiConnector;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public class Controller extends BiConnector<ControlEvent, DataEvent> {

    private int i = 0;

    public void handle(DataEvent event) {
        System.out.println("it returns some data ^_^");
        i++;
    }

    public void start() {
        fire(new ControlEvent());
        fire(new ControlEvent());
        Assert.assertTrue(i == 1);

        fire(new ControlEvent());
        fire(new ControlEvent());

        Assert.assertTrue(i == 2);
    }
}
