package dem.processing.external;

import dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public interface BiProcessorStrategy<LEFT extends Event, RIGHT extends Event> {

    public abstract boolean processLeft(LEFT event);

    public abstract boolean processRight(RIGHT event);

}
