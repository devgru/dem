package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.BoundedHandler;
import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.inclass.HandlesOrphans;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.inclass.exceptions.MethodIsUnbindableException;
import ru.devg.dem.quanta.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class MethodWorker extends AbstractBinder {

    MethodWorker(Object target) {
        super(target);
    }

    public void tryBindMembers(List<BindedElement> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Method method : targetClass.getMethods()) {
            BindedElement filter = tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

    private BindedElement tryBindMethod(Method method) throws ClassIsUnbindableException {
        try {
            if (method.getAnnotation(Handles.class) != null) {
                Handles annotation = method.getAnnotation(Handles.class);
                BindableElementDescriptor desc =
                        new BindableElementDescriptor(annotation.value(), annotation.priority(), annotation.translator());
                return bindMethod(method, desc);
            } else if (method.getAnnotation(HandlesOrphans.class) != null) {
                BindableElementDescriptor desc =
                        new BindableElementDescriptor(Event.class, (long) Integer.MIN_VALUE - 1, null);
                return bindMethod(method, desc);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private BindedElement bindMethod(Method method, BindableElementDescriptor desc) throws ElementIsUnbindableException {
        TypeFilter halfResult;

        Class<?>[] types = method.getParameterTypes();
        int argsCount = types.length;
        if (argsCount > 1) {
            throw new MethodIsUnbindableException("method shouldn't have more than 1 parameter.");
        } else if (argsCount == 1) {
            Class<?> argClass = types[0];
            if (argClass != desc.getBound()) {
                throw new MethodIsUnbindableException("declared parameter's type must be equal to annotated class.");
            }
            halfResult = new MethodInvoker(argClass, method);
        } else {
            halfResult = new NoParamMethodInvoker(desc.getBound(), method);
        }

        return wrap(desc, halfResult);
    }

    private class NoParamMethodInvoker extends BoundedHandler {
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

    private class MethodInvoker extends BoundedHandler {
        private final Method method;

        @SuppressWarnings("unchecked")
        private MethodInvoker(Class<?> bound, Method method) {
            super(bound);
            assert method != null;
            this.method = method;
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
