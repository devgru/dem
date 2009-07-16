package dem.quanta;

/**
 * Handler is a second <i>quantum</i> of the framework.
 * It was created to provide the alternative Observer idiom.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @see Event
 * @since 0.15
 */
public interface Handler<E extends Event> {

    /**
     * This method is the only method in {@link Handler this interface}.
     *
     * @param event an event that you handle.
     */
    public void handle(E event);
}
