package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.NoopFilter;
import ru.devg.dem.filtering.CommonFilter;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.inclass.HandlesOrphans;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.inclass.exceptions.FieldIsUnbindableException;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.translating.TranslatorStrategy;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Devgru &lt;javaм@devg.ru&gt;
 * @version 0.176
 */
final class FieldWorker extends AbstractBinder {

    FieldWorker(Object target) {
        super(target);
    }

    public void tryBindMembers(List<BindedElement> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Field field : targetClass.getDeclaredFields()) {
            BindedElement entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    private BindedElement tryBindField(Field field) throws ClassIsUnbindableException {
        try {
            if (field.getAnnotation(Handles.class) != null) {
                Handles a = field.getAnnotation(Handles.class);
                Class<? extends Event> bound = a.value();
                int priority = a.priority();
                BindableElementDescriptor desc =
                        new BindableElementDescriptor(bound, priority, a.translator());
                return bindField(field, desc);
            } else if (field.getAnnotation(HandlesOrphans.class) != null) {
                BindableElementDescriptor desc =
                        new BindableElementDescriptor(Event.class, (long) Integer.MIN_VALUE - 1, TranslatorStrategy.class);
                return bindField(field, desc);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private BindedElement bindField(Field field, BindableElementDescriptor desc) throws ElementIsUnbindableException {
        Filter halfResult;

        try {
            field.get(target);
        } catch (IllegalAccessException e) {
            throw new FieldIsUnbindableException("field " + field.getName() + " is inaccessible. Probably it's not public.", e);
        }

        Class<?> type = field.getType();

        if (CommonFilter.class.isAssignableFrom(type)) {
            halfResult = new FilteredFieldHandler(field);
        } else if (Handler.class.isAssignableFrom(type)) {
            halfResult = new FieldHandler(desc.getBound(), field);
        } else {
            throw new FieldIsUnbindableException("field's type must implement Handler.");
        }

        return wrap(field, desc, halfResult);
    }

    private abstract class AbstractFieldHandler implements Filter {
        protected final Field field;

        private AbstractFieldHandler(Field field) {
            this.field = field;
        }

        protected Handler getHandler() {
            try {
                Object rawHandler = field.get(target);
                return (Handler) rawHandler;
            } catch (IllegalAccessException ignored) {
                return new NoopFilter();
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

        public boolean handleIfPossible(Event event) {

            boolean isInstance = bound.isInstance(event);
            if(isInstance)handle(event);
            return isInstance;
        }
    }

    private class FilteredFieldHandler extends AbstractFieldHandler {
        private FilteredFieldHandler(Field field) {
            super(field);
        }

        public boolean handleIfPossible(Event event) {
            CommonFilter filter = (CommonFilter) getHandler();
            return filter.handleIfPossible(event);
        }

    }

}
