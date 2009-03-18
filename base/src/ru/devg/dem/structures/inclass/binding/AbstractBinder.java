package ru.devg.dem.structures.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.sources.Source;
import ru.devg.dem.structures.inclass.PushesDown;
import ru.devg.dem.structures.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.structures.inclass.exceptions.ClassNotExtendsSourceException;
import ru.devg.dem.structures.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.translating.ExternalTranslator;
import ru.devg.dem.translating.TranslatorStrategy;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.177
 */
abstract class AbstractBinder {
    protected final Object target;

    AbstractBinder(Object target) {
        this.target = target;
    }

    public abstract void tryBindMembers(List<BindedMember> listToUpdate, Class<?> targetClass)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    protected Filter wrapByTranslator(Class<? extends Event> bound,
                                      Class<? extends TranslatorStrategy> translator,
                                      Filter halfResult)
            throws ElementIsUnbindableException {

        if (translator != TranslatorStrategy.class) {
            try {
                halfResult = new ExternalTranslator(halfResult, bound, translator.newInstance());
            } catch (InstantiationException e) {
                throw new ElementIsUnbindableException(e);
            } catch (IllegalAccessException e) {
                throw new ElementIsUnbindableException(e);
            }
        }
        return halfResult;
    }

    protected Filter wrapByDownpusher(AnnotatedElement element, Filter halfResult)
            throws ClassNotExtendsSourceException {
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
