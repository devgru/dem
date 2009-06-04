package dem.commands;

import dem.quanta.Handler;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 */
public final class Executer< CO extends Context, C extends Command<CO>> implements Handler<C> {

    private CO c = null;

    public void handle(C command) {
        command.execute(c);
    }
}
