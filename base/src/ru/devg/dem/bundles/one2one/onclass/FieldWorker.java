package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.extended.NoopHandler;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
final class FieldWorker {
    private final InplaceDispatchable<?> target;

    FieldWorker(InplaceDispatchable<?> target) {
        this.target = target;
    }

    public List<DispatchedEntry> result() {
        List<DispatchedEntry> grabbed = new LinkedList<DispatchedEntry>();
        Class<? extends InplaceDispatchable> targetClass = target.getClass();

        for (Field field : targetClass.getDeclaredFields()) {
            DispatchedEntry entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
        return grabbed;
    }

    private DispatchedEntry tryBindField(Field field) throws NoSuchFieldError {
        if (field.getAnnotation(InplaceDispatchable.Handles.class) != null) {
            return bindField(field);
        } else {
            return null;
        }
    }

    private DispatchedEntry bindField(Field field) {
        InplaceDispatchable.Handles a = field.getAnnotation(InplaceDispatchable.Handles.class);

        Class<?> type = field.getType();
        try {
            field.get(target);
        } catch (IllegalAccessException e) {
            throw new NoSuchFieldError("field " + field.getName() + " is inaccessible. Probably it's not public.");
        }

        Class<?> annotatedClass = a.value();
        int priority = a.priority();
        if (Filter.class.isAssignableFrom(type)) {
            if (TypeBoundedHandler.class.isAssignableFrom(type)) {
                if (type.isAssignableFrom(annotatedClass)) {
                    throw new NoSuchFieldError("field " + field.getName() + " mush have same type as annotated class.");
                }
            }
            return new DispatchedEntry(new FilteredFieldHandler(field), priority);
        } else if (Handler.class.isAssignableFrom(type)) {
            return new DispatchedEntry(new FieldHandler(annotatedClass, field), priority);
        } else {
            throw new NoSuchFieldError("field's class must implement ru.devg.dem.quanta.Handler.");
        }
    }

    private abstract class AbstractFieldHandler extends Filter {
        protected final Field field;

        private AbstractFieldHandler(Field field) {
            this.field = field;
        }

        protected Handler getHandler() {
            try {
                Object rawHandler = field.get(target);
                return (Handler) rawHandler;
            } catch (IllegalAccessException ignored) {
//                throw new RuntimeException(ignored);
                return NoopHandler.getInstance();
            }
        }

        @SuppressWarnings("unchecked")
        public void handle(Event event) {
            getHandler().handle(event);
        }
    }

    private class FieldHandler extends AbstractFieldHandler {
        private final Class<?> bound;

        @SuppressWarnings("unchecked")
        private FieldHandler(Class<?> bound, Field field) {
            super(field);
            this.bound = bound;
        }

        public boolean canHandle(Event event) {
            return bound.isInstance(event);
        }
    }

    private class FilteredFieldHandler extends AbstractFieldHandler {

        private FilteredFieldHandler(Field field) {
            super(field);
        }

        public boolean canHandle(Event event) {
            Handler handler = getHandler();
            if (handler == null) return false;
            Filter filter = (Filter) handler;
            return filter.canHandle(event);
        }

    }


}
