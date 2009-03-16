package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.extended.NoopHandler;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.structures.dispatchers.inclass.Handles;
import ru.devg.dem.structures.dispatchers.inclass.HandlesOrphans;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
final class FieldWorker implements AbstractBinder {
    private final Object target;

    FieldWorker(Object target) {
        this.target = target;
    }

    public void tryBindMembers(List<BindedMember> grabbed, Class<?> targetClass) {
        for (Field field : targetClass.getDeclaredFields()) {
            BindedMember entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    private BindedMember tryBindField(Field field) throws NoSuchFieldError {
        if (field.getAnnotation(Handles.class) != null) {
            Handles a = field.getAnnotation(Handles.class);

            Class<? extends Event> bound = a.value();
            int priority = a.priority();

            return innerBind(field, bound, priority);
        } else if (field.getAnnotation(HandlesOrphans.class) != null) {
            return innerBind(field, Event.class, (long) Integer.MIN_VALUE - 1);
        } else {
            return null;
        }
    }

    private BindedMember innerBind(Field field, Class<? extends Event> bound, long priority) {
        Class<?> type = field.getType();
        try {
            field.get(target);
        } catch (IllegalAccessException e) {
            throw new NoSuchFieldError("field " + field.getName() + " is inaccessible. Probably it's not public.");
        }
        if (Filter.class.isAssignableFrom(type)) {
            if (TypeBoundedHandler.class.isAssignableFrom(type)) {
/*
                if (type.isAssignableFrom(bound)) {
                    throw new NoSuchFieldError("field " + field.getName() + " must have same type as annotated class.");
                }
*/
            }
            return new BindedMember(new FilteredFieldHandler(field), priority);
        } else if (Handler.class.isAssignableFrom(type)) {
            return new BindedMember(new FieldHandler(bound, field), priority);
        } else {
            throw new NoSuchFieldError("field's must contain any Handler or null.");
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
                return new NoopHandler();
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
