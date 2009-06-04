package dem.processing.external;

import dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.182
 */
public interface ProcessorStrategy<E extends Event> {
    public boolean process(E event);
}
