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

    public MethodWorker(Object target) {
        super(target);
    }

    public void tryBindMembers(List<BoundElement> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Method method : targetClass.getMethods()) {
            BoundElement filter = tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

    private BoundElement tryBindMethod(Method method) throws ClassIsUnbindableException {
        try {
            if (method.getAnnotation(Handles.class) != null) {
                Handles annotation = method.getAnnotation(Handles.class);
                BindableElement desc =
                        new BindableElement(annotation);
                return bindMethod(method, desc);
            } else if (method.getAnnotation(HandlesOrphans.class) != null) {
                BindableElement desc = BindableElement.ORPHAN_HANDLER;
                return bindMethod(method, desc);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private BoundElement bindMethod(Method method, BindableElement desc) throws ElementIsUnbindableException {
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
            halfResult = new MethodInvoker(argClass, method, true);
        } else {
            halfResult = new MethodInvoker(desc.getBound(), method, false);
        }

        return wrap(desc, halfResult);
    }

    private class MethodInvoker extends BoundedHandler {
        private final Method method;
        private final boolean passEventToMethod;

        @SuppressWarnings("unchecked")
        private MethodInvoker(Class<?> bound, Method method, boolean passEventToMethod) {
            super(bound);
            assert method != null;
            this.method = method;
            this.passEventToMethod = passEventToMethod;
        }

        public void handle(Event event) {
            try {
                if(passEventToMethod){
                    method.invoke(target, event);
                } else {
                    method.invoke(target);
                }
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            }
        }

        @Override
        public String toString() {
            return "method "+ method.getName();
        }
    }

}
