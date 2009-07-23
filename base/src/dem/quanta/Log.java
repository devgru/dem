package dem.quanta;

import java.util.Iterator;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.0
 */
public final class Log {
    private static final String OFFSETTER = "  ";
    public static final Log instance = new Log();//probably we will need it once

    private Log() {
    }

    public static String offset(String string, int i) {
        assert string != null;
        if (i == 1) {
            return offset(string);
        } else {
            return offset(offset(string, i - 1));
        }
    }

    public static String offset(String string) {
        assert string != null;
        return OFFSETTER + string.replace("\n", "\n" + OFFSETTER);
    }

    public static String inline(Iterable<?> handlers) {
        assert handlers != null;
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = handlers.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
