package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.structures.dispatchers.inclass.Handles;
import ru.devg.dem.structures.dispatchers.inclass.HandlesOrphans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
final class MethodWorker implements AbstractBinder {
    private final Object target;

    MethodWorker(Object target) {
        this.target = target;
    }

    public void tryBindMembers(List<BindedMember> grabbed, Class<?> targetClass) {
        for (Method method : targetClass.getMethods()) {
            BindedMember filter = tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

    private BindedMember tryBindMethod(Method method) throws NoSuchMethodError {
        if (method.getAnnotation(Handles.class) != null) {
            Handles a = method.getAnnotation(Handles.class);

            Class<? extends Event> annotatedClass = a.value();
            int priority = a.priority();
            return bindMethod(method, annotatedClass, priority);
        } else if (method.getAnnotation(HandlesOrphans.class) != null) {
            return bindMethod(method, Event.class, (long) Integer.MIN_VALUE - 1);
        } else {
            return null;
        }
    }

    private BindedMember bindMethod(Method method, Class<? extends Event> bound, long priority) {
        Class<?>[] types = method.getParameterTypes();
        int argsCount = types.length;
        if (argsCount > 1) {
            throw new NoSuchMethodError("method shouldn't have more than 1 parameter.");
        } else if (argsCount == 1) {
            Class<?> argClass = types[0];
            if (argClass != bound) {
                throw new NoSuchMethodError("declared parameter's type must be equal to annotated class.");
            }
            return new BindedMember(new MethodInvoker(argClass, method), priority);
        } else {
            return new BindedMember(new NoParamMethodInvoker(bound, method), priority);
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
