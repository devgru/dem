package dem.inclass.binding;

import dem.inclass.ClassIsUnbindableException;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class FieldWorker extends AbstractBinder {

    public void tryBindMembers(Object target, List<FilterWithPriority> grabbed) throws ClassIsUnbindableException {
        FieldWorkerImpl worker = new FieldWorkerImpl(target);
        for (Field field : target.getClass().getDeclaredFields()) {
            FilterWithPriority entry = worker.tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

}
