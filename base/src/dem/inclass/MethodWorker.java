package dem.inclass;

import dem.bounding.BoundedHandler;
import dem.bounding.Filter;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.inclass.exceptions.MethodIsUnbindableException;
import dem.quanta.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.176
 */
final class MethodWorker extends AbstractBinder {

    public void tryBindMembers(Object target, List<FilterWithPriority> grabbed, Class<?> clz) throws ClassIsUnbindableException {
        Context context = new Context(target);
        for (Method method : clz.getDeclaredMethods()) {
            FilterWithPriority filter = context.tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

    static final class Context {
        private final Object target;

        Context(Object target) {
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

            return wrap(annotation, halfResult);
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
                return "Method handler (method is " + method.getName() + ")";
            }
        }

    }
}