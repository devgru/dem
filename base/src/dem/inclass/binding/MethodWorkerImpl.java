package dem.inclass.binding;

import dem.bounding.BoundedHandler;
import dem.bounding.Filter;
import dem.inclass.ClassIsUnbindableException;
import dem.inclass.Handles;
import dem.inclass.binding.exceptions.ElementIsUnbindableException;
import dem.inclass.binding.exceptions.MethodIsUnbindableException;
import dem.quanta.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
final class MethodWorkerImpl {
    private final Object target;

    MethodWorkerImpl(Object target) {
        this.target = target;
    }

    FilterWithPriority tryBindMethod(Method method) throws ClassIsUnbindableException {
        try {
            if (method.isAnnotationPresent(Handles.class)) {
                return bindMethod(method);
            } else {
                return null;
            }
        } catch (ElementIsUnbindableException e) {
            throw new ClassIsUnbindableException(e);
        }
    }

    private FilterWithPriority bindMethod(Method method) throws ElementIsUnbindableException {
        Filter halfResult;

        Class<?>[] types = method.getParameterTypes();
        int argsCount = types.length;
        Handles annotation = method.getAnnotation(Handles.class);
        if (argsCount > 1) {
            throw new MethodIsUnbindableException("method shouldn't have more than 1 parameter.");
            //todo here I should implement the event-to-array translation
        } else if (argsCount == 1) {
            Class<?> argClass = types[0];
            if (argClass.isAssignableFrom(annotation.value())) {
                throw new MethodIsUnbindableException("declared parameter's type must be equal to annotated class.");
            }
            halfResult = new MethodInvoker(argClass, method, argsCount);
        } else {
            halfResult = new MethodInvoker(annotation.value(), method, argsCount);
        }

        return AbstractBinder.wrap(annotation, halfResult);
    }

    private class MethodInvoker extends BoundedHandler {
        private final Method method;
        private final int argsCount;

        @SuppressWarnings("unchecked")
        private MethodInvoker(Class<?> bound, Method method, int argsCount) {
            super(bound);
            assert method != null;
            this.method = method;
            this.argsCount = argsCount;
        }

        public void handle(Event event) {
            try {
                if (argsCount == 1) {
                    method.invoke(target, event);
                } else {
                    method.invoke(target);
                }
            } catch (IllegalAccessException ignored) {
                //todo
            } catch (InvocationTargetException ignored) {
                //todo
            }
        }

        @Override
        public String toString() {
            return "method " + method.getName();
        }
    }

}
