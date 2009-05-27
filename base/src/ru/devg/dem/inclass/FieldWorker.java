package ru.devg.dem.inclass;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.inclass.exceptions.FieldIsUnbindableException;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.stuff.NoopHandler;

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

    public void tryBindMembers(List<BoundElement> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Field field : targetClass.getDeclaredFields()) {
            BoundElement entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    private BoundElement tryBindField(Field field) throws ClassIsUnbindableException {
        try {
            if (field.isAnnotationPresent(Handles.class)) {
                Handles annotation = field.getAnnotation(Handles.class);
                return bindField(field, annotation);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private BoundElement bindField(Field field, Handles element) throws ElementIsUnbindableException {
        TypeFilter halfResult;

        try {
            field.setAccessible(true);
        } catch (SecurityException e) {
            throw new ElementIsUnbindableException(e);
        }

        Class<?> type = field.getType();

        if (TypeFilter.class.isAssignableFrom(type)) {
            halfResult = new FilteredFieldHandler(field);
        } else if (Handler.class.isAssignableFrom(type)) {
            halfResult = new FieldHandler(element.value(), field);
        } else {
            throw new FieldIsUnbindableException("field's type must implement Handler.");
        }

        return wrap(element, halfResult);
    }

    private abstract class AbstractFieldHandler implements TypeFilter {
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
            TypeFilter filter = (TypeFilter) getHandler();
            return filter.handleIfPossible(event);
        }

    }

}
