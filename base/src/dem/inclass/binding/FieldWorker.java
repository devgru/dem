package dem.inclass.binding;

import dem.bounding.Filter;
import dem.inclass.ClassIsUnbindableException;
import dem.inclass.Handles;
import dem.inclass.binding.exceptions.ElementIsUnbindableException;
import dem.inclass.binding.exceptions.FieldIsUnbindableException;
import dem.quanta.Event;
import dem.quanta.Handler;
import dem.stuff.NoopHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class FieldWorker extends AbstractBinder {

    public FieldWorker(Object target) {
        super(target);
    }

    public void tryBindMembers(List<FilterWithPriority> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Field field : targetClass.getDeclaredFields()) {
            FilterWithPriority entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    private FilterWithPriority tryBindField(Field field) throws ClassIsUnbindableException {
        try {
            if (field.isAnnotationPresent(Handles.class)) {
                return bindField(field);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private FilterWithPriority bindField(Field field) throws ElementIsUnbindableException {
        Filter halfResult;

        try {
            field.setAccessible(true);
        } catch (SecurityException e) {
            throw new ElementIsUnbindableException(e);
        }

        Class<?> type = field.getType();

        Handles annotation = field.getAnnotation(Handles.class);
        if (Filter.class.isAssignableFrom(type)) {
            halfResult = new FilteredFieldHandler(field);
        } else if (Handler.class.isAssignableFrom(type)) {
            halfResult = new FieldHandler(annotation.value(), field);
        } else {
            throw new FieldIsUnbindableException("field's type must implement Handler.");
        }

        return wrap(annotation, halfResult);
    }

    private abstract class AbstractFieldHandler implements Filter {
        final Field field;

        private AbstractFieldHandler(Field field) {
            this.field = field;
        }

        Handler getHandler() {
            try {
                Object rawHandler = field.get(target);
                return (Handler) rawHandler;
            } catch (IllegalAccessException ignored) {
                return new NoopHandler();
            }
        }

        @SuppressWarnings("unchecked")
        public void handle(Event event) {
            getHandler().handle(event);
        }

        @Override
        public String toString() {
            return "field " + field.getName();
        }
    }

    private class FieldHandler extends AbstractFieldHandler {
        private final Class<?> bound;

        @SuppressWarnings("unchecked")
        private FieldHandler(Class<?> bound, Field field) {
            super(field);
            this.bound = bound;
        }

        public boolean handleIfPossible(Event event) {

            boolean isInstance = bound.isInstance(event);
            if (isInstance) handle(event);
            return isInstance;
        }
    }

    private class FilteredFieldHandler extends AbstractFieldHandler {
        private FilteredFieldHandler(Field field) {
            super(field);
        }

        public boolean handleIfPossible(Event event) {
            Filter filter = (Filter) getHandler();
            return filter.handleIfPossible(event);
        }

    }

}
