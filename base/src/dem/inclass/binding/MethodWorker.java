package dem.inclass.binding;

import dem.inclass.ClassIsUnbindableException;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class MethodWorker extends AbstractBinder {

    public void tryBindMembers(Object target, List<FilterWithPriority> grabbed) throws ClassIsUnbindableException {
        MethodWorkerImpl worker = new MethodWorkerImpl(target);
        for (Method method : target.getClass().getDeclaredMethods()) {
            FilterWithPriority filter = worker.tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

}