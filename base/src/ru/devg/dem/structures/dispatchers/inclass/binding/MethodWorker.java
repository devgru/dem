package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.filtering.TypeBoundedHandler;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.structures.dispatchers.inclass.Handles;
import ru.devg.dem.structures.dispatchers.inclass.HandlesOrphans;
import ru.devg.dem.structures.dispatchers.inclass.SomeStructure;
import ru.devg.dem.structures.dispatchers.inclass.exceptions.ClassNotExtendsSourceException;
import ru.devg.dem.structures.dispatchers.inclass.exceptions.MethodIsUnbindableException;
import ru.devg.dem.structures.dispatchers.inclass.exceptions.ClassIsUnbindableException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.176
 */
final class MethodWorker extends AbstractBinder {

    MethodWorker(Object target) {
        super(target);
    }

    public void tryBindMembers(List<BindedMember> grabbed, Class<?> targetClass) throws ClassIsUnbindableException {
        for (Method method : targetClass.getMethods()) {
            BindedMember filter = tryBindMethod(method);
            if (filter != null) grabbed.add(filter);
        }
    }

    private BindedMember tryBindMethod(Method method) throws NoSuchMethodError, ClassIsUnbindableException {
        if (method.getAnnotation(Handles.class) != null) {
            Handles a = method.getAnnotation(Handles.class);
            try {
                return bindMethod(method, new SomeStructure(a.value(), a.priority(), a.translator()));
            } catch (MethodIsUnbindableException e) {
                throw new ClassIsUnbindableException(e);
            }
        } else if (method.getAnnotation(HandlesOrphans.class) != null) {
            try {
                return bindMethod(method, new SomeStructure(Event.class, (long) Integer.MIN_VALUE - 1, null));
            } catch (MethodIsUnbindableException e) {
                throw new ClassIsUnbindableException(e);
            }
        } else {
            return null;
        }
    }

    private BindedMember bindMethod(Method method, SomeStructure ss) throws MethodIsUnbindableException {
        Filter halfResult;

        Class<?>[] types = method.getParameterTypes();
        int argsCount = types.length;
        if (argsCount > 1) {
            throw new NoSuchMethodError("method shouldn't have more than 1 parameter.");
        } else if (argsCount == 1) {
            Class<?> argClass = types[0];
            if (argClass != ss.getBound()) {
                throw new NoSuchMethodError("declared parameter's type must be equal to annotated class.");
            }
            halfResult = new MethodInvoker(argClass, method);
        } else {
            halfResult = new NoParamMethodInvoker(ss.getBound(), method);
        }


        try {
            halfResult = wrapByDownpusher(method, halfResult);
        } catch (ClassNotExtendsSourceException e) {
            throw  new MethodIsUnbindableException(e);
        }
        halfResult = wrapByTranslator(ss.getBound(), ss.getTranslatorStrategy(), halfResult);
        return new BindedMember(halfResult, ss.getPriority());
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
