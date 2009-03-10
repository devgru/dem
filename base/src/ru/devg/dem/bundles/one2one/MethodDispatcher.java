package ru.devg.dem.bundles.one2one;

import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.quanta.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru &lt;devgru@mail.ru&gt;
 * @version 0.17
 */
public final class MethodDispatcher<E extends Event>
        extends TypeBoundedHandler<E> {

    private final MultiHandler<E> target;

    private Handler<E> handlerForOrphans = null;

    public void setHandlerForOrphans(Handler<E> handlerForOrphans) {
        this.handlerForOrphans = handlerForOrphans;
    }


    // fields

    private final List<Filter> handlers =
            new LinkedList<Filter>();

    //vv

    public final void handle(E event) {
        for (Filter<?> binder : handlers) {
            if (binder.handleIfPossible(event)) return;
        }
        if (handlerForOrphans != null) {
            handlerForOrphans.handle(event);
        }
    }


    //adding

    public MethodDispatcher(MultiHandler<E> handler) throws NoSuchMethodError,NoSuchFieldError {
        super(handler.getBoundClass());
        target = handler;
        init();
    }

    private void init() throws NoSuchMethodError,NoSuchFieldError {
        for (Method method : target.getClass().getMethods()) {
            tryBindMethod(method);
        }
        for (Field field : target.getClass().getDeclaredFields()) {
            tryBindField(field);
        }
    }


    private void tryBindMethod(Method method) throws NoSuchFieldError {
        MultiHandler.Handles a = method.getAnnotation(MultiHandler.Handles.class);
        if (a != null) {
            Class<?>[] types = method.getParameterTypes();
            int argsCount = types.length;
            if (argsCount > 1) {
                throw new NoSuchMethodError("method shouldn't have more than 1 parameter");
            } else if (argsCount == 1) {
                Class<?> argClass = types[0];
                if (argClass != a.value()) {
                    throw new NoSuchMethodError("declared parameter's type must be equal to annotated class");
                }
                if (!target.getBoundClass().isAssignableFrom(argClass)) {
                    throw new NoSuchMethodError("declared parameter's type should extend parent handler's");
                }
                handlers.add(new MethodInvoker(argClass, method));
            } else {
                handlers.add(new NoParamMethodInvoker(a.value(), method));
            }
        }
    }


    private void tryBindField(Field field) throws NoSuchFieldError {
        MultiHandler.Handles a = field.getAnnotation(MultiHandler.Handles.class);
        if (a != null) {
            Class<?> type = field.getType();
            try {
                field.get(target);
            } catch (IllegalAccessException e) {
                throw new NoSuchFieldError("field "+field.getName()+" is inaccessible");
            }
            if (Filter.class.isAssignableFrom(type)) {
                if (TypeBoundedHandler.class.isAssignableFrom(type)) {
                    if (type != a.value()) {
                        throw new NoSuchFieldError("declared parameter's type must be equal to annotated class");
                    }
                }
                if (!target.getBoundClass().isAssignableFrom(type)) {
                    throw new NoSuchFieldError("declared parameter's type should extend parent handler's");
                }
                handlers.add(new FilteredFieldHandler(field));
            } else if(Handler.class.isAssignableFrom(type)){
                handlers.add(new FieldHandler(a.value(), field));
            }else {
                throw new NoSuchFieldError("wtf");//todo asd
            }
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
                throw new RuntimeException(ignored);
//                return NoopHandler.getInstance();
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

    private class NoParamMethodInvoker extends TypeBoundedHandler {
        private final Method method;

        @SuppressWarnings("unchecked")
        private NoParamMethodInvoker(Class<?> bound, Method m) {
            super(bound);
            method = m;
        }

        public void handle(Event event) {
            try {
                method.invoke(target);
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            }
        }
    }

    private class MethodInvoker extends TypeBoundedHandler {
        private final Method method;

        @SuppressWarnings("unchecked")
        private MethodInvoker(Class<?> bound, Method m) {
            super(bound);
            assert m != null;
            method = m;
        }

        public void handle(Event event) {
            try {
                method.invoke(target, event);
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            }
        }
    }

}