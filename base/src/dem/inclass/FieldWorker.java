package dem.inclass;

import dem.bounding.Filter;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.inclass.exceptions.FieldIsUnbindableException;
import dem.quanta.Event;
import dem.quanta.Handler;
import dem.stuff.NoopHandler;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class FieldWorker implements AbstractBinder {

    public void tryBindMembers(Object target, List<AnnotatedFilter> grabbed, Class<?> clz)
            throws ClassIsUnbindableException {

        Context worker = new Context(target);
        for (Field field : clz.getDeclaredFields()) {
            AnnotatedFilter entry = worker.tryBindField(field);
            if (entry != null) grabbed.add(entry);
        }
    }

    static final class Context {
        private final Object target;

        Context(Object target) {
            this.target = target;
        }

        AnnotatedFilter tryBindField(Field field) throws ClassIsUnbindableException {
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

        private AnnotatedFilter bindField(Field field) throws ElementIsUnbindableException {
            final AnnotatedFilter result;

            try {
                field.setAccessible(true);
            } catch (SecurityException e) {
                throw new ElementIsUnbindableException(e);
            }

            Class<?> type = field.getType();

            Handles annotation = field.getAnnotation(Handles.class);
            if (Filter.class.isAssignableFrom(type)) {
                result = new FilteredFieldHandler(field);
            } else if (Handler.class.isAssignableFrom(type)) {
                result = new FieldHandler(annotation.value(), field);
            } else {
                throw new FieldIsUnbindableException("field's type must implement Handler.");
            }

            return result;
        }

        private abstract class AbstractFieldHandler implements AnnotatedFilter {
            final Field field;

            private AbstractFieldHandler(Field field) {
                this.field = field;
            }

            public AnnotatedElement getAnnotatedElement() {
                return field;
            }

            Handler getHandler() {
                try {
                    Object rawHandler = field.get(target);
                    if (rawHandler == null) {
                        return new NoopHandler();
                    }
                    return (Handler) rawHandler;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @SuppressWarnings("unchecked")
            public void handle(Event event) {
                getHandler().handle(event);
            }

            @Override
            public String toString() {
                return "Field handler (field is " + field + ")";
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
}
