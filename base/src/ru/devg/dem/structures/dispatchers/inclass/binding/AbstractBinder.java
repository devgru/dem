package ru.devg.dem.structures.dispatchers.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.translating.TranslatorStrategy;
import ru.devg.dem.translating.ExternalTranslator;
import ru.devg.dem.structures.dispatchers.inclass.exceptions.ClassNotExtendsSourceException;
import ru.devg.dem.structures.dispatchers.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.structures.dispatchers.inclass.PushesDown;
import ru.devg.dem.sources.Source;

import java.util.List;
import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.177
 */
abstract class AbstractBinder {
    protected final Object target;

    AbstractBinder(Object target) {
        this.target = target;
    }

    public abstract void tryBindMembers(List<BindedMember> listToUpdate,Class<?> targetClass) throws ClassIsUnbindableException;

    protected Filter wrapByTranslator(Class<? extends Event> bound, Class<? extends TranslatorStrategy> translator, Filter halfResult) {
        if (translator != TranslatorStrategy.class) {
            try {
                halfResult = new ExternalTranslator(halfResult, bound, translator.newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return halfResult;
    }

    protected Filter wrapByDownpusher(AnnotatedElement element, Filter halfResult) throws ClassNotExtendsSourceException {
        if (element.getAnnotation(PushesDown.class) != null) {
            if (target instanceof Source) {
                Source s = (Source) target;
                halfResult = new DownPusher(s, halfResult);
            } else {
                throw new ClassNotExtendsSourceException("target must extend Source class" +
                        "if you want to use PushesDown annotation.");
            }
        }
        return halfResult;
    }
}
