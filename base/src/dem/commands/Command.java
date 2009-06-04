package dem.commands;

import dem.quanta.Event;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 */
public interface Command<C extends Context> extends Event {
    public void execute(C context);
}
