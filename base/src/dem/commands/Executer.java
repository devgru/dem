package dem.commands;

import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 */
public abstract class Executer<CO extends Context, C extends Command<CO>>
        implements Handler<C> {

    public void handle(C command) {
        command.execute(getContext());
    }

    public abstract CO getContext();
}
