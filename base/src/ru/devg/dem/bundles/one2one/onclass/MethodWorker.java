package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
final class MethodWorker {
    private final InplaceDispatchable target;

    MethodWorker(InplaceDispatchable target) {
        this.target = target;
    }

    public List<DispatchedEntry> result() {
        List<DispatchedEntry> grabbed = new LinkedList<DispatchedEntry>();
        Class<? extends InplaceDispatchable> targetClass = target.getClass();
        for (Method method : targetClass.getMethods()) {
            DispatchedEntry filter = tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
        return grabbed;
    }

    private DispatchedEntry tryBindMethod(Method method) throws NoSuchMethodError {
        if (method.getAnnotation(InplaceDispatchable.Handles.class) != null) {
            return bindMethod(method);
        } else {
            return null;
        }
    }

    private DispatchedEntry bindMethod(Method method) {
        InplaceDispatchable.Handles a = method.getAnnotation(InplaceDispatchable.Handles.class);

        Class<?>[] types = method.getParameterTypes();
        int argsCount = types.length;

        Class<?> annotatedClass = a.value();
        int priority = a.priority();
        if (argsCount > 1) {
            throw new NoSuchMethodError("method shouldn't have more than 1 parameter.");
        } else if (argsCount == 1) {
            Class<?> argClass = types[0];
            if (argClass != annotatedClass) {
                throw new NoSuchMethodError("declared parameter's type must be equal to annotated class.");
            }
            if (!target.getBoundClass().isAssignableFrom(argClass)) {
                throw new NoSuchMethodError("declared parameter's type should extend parental handler's one.");
            }
            return new DispatchedEntry(new MethodInvoker(argClass, method),priority);
        } else {
            return new DispatchedEntry(new NoParamMethodInvoker(a.value(), method),priority);
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
