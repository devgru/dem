package dem.stuff;

import dem.quanta.Handler;

import java.util.Iterator;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public final class Log {
    private static final String OFFSETTER = " ";

    private Log() {
    }

    public static String offset(String string, int i) {
        if (i == 1) {
            return offset(string);
        } else {
            return offset(offset(string, i-1));
        }
    }

    public static String offset(String string) {
        return OFFSETTER + string.replace("\n", "\n"+OFFSETTER );
    }

    public static String describe(List<? extends Handler> handlers) {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends Handler> it = handlers.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
