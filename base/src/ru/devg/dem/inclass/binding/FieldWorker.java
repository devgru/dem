package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.NoopFilter;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.inclass.HandlesOrphans;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ClassNotExtendsSourceException;
import ru.devg.dem.inclass.exceptions.FieldIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
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

    public void tryBindMembers(List<BindedMember> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Field field : targetClass.getDeclaredFields()) {
            BindedMember entry = tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    private BindedMember tryBindField(Field field) throws ClassIsUnbindableException {
        if (field.getAnnotation(Handles.class) != null) {
            Handles a = field.getAnnotation(Handles.class);

            Class<? extends Event> bound = a.value();
            int priority = a.priority();

            BindableElementDescriptor ss = new BindableElementDescriptor(bound, priority, a.translator());
            try {
                return bindField(field, ss);
            } catch (ElementIsUnbindableException e) {
                throw new ClassIsUnbindableException(e);
            }
        } else if (field.getAnnotation(HandlesOrphans.class) != null) {
            BindableElementDescriptor ss = new BindableElementDescriptor(Event.class, (long) Integer.MIN_VALUE - 1, TranslatorStrategy.class);
            try {
                return bindField(field, ss);
            } catch (ElementIsUnbindableException e) {
                throw new ClassIsUnbindableException(e);
            }
        } else {
            return null;
        }
    }

    private BindedMember bindField(Field field, BindableElementDescriptor ss) throws ElementIsUnbindableException {
        Filter halfResult;

        Class<?> type = field.getType();
        try {
            field.get(target);
        } catch (IllegalAccessException e) {
            throw new FieldIsUnbindableException("field " + field.getName() + " is inaccessible. Probably it's not public.",e);
        }
        if (Filter.class.isAssignableFrom(type)) {
            halfResult = new FilteredFieldHandler(field);
        } else if (Handler.class.isAssignableFrom(type)) {
            halfResult = new FieldHandler(ss.getBound(), field);
        } else {
            throw new FieldIsUnbindableException("field's type must implement Handler.");
        }

        try {
            halfResult = wrapByDownpusher(field, halfResult);
        } catch (ClassNotExtendsSourceException e) {
            throw new FieldIsUnbindableException(e);
        }
        halfResult = wrapByTranslator(ss.getBound(), ss.getTranslatorStrategy(), halfResult);

        return new BindedMember(halfResult, ss.getPriority());
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
