package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.Filter;
import ru.devg.dem.inclass.PushesDown;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ClassNotExtendsSourceException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.inclass.exceptions.MethodIsUnbindableException;
import ru.devg.dem.quanta.Event;
import ru.devg.dem.sources.Source;
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

    public abstract void tryBindMembers(List<BindedElement> listToUpdate, Class<?> targetClass)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    private Filter wrapByTranslator(Class<? extends Event> bound,
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

    private Filter wrapByDownpusher(AnnotatedElement element, Filter halfResult)
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

    protected BindedElement wrap(AnnotatedElement element, BindableElementDescriptor desc, Filter halfResult) throws ElementIsUnbindableException {
        try {
            halfResult = wrapByDownpusher(element, halfResult);
        } catch (ClassNotExtendsSourceException e) {
            throw new MethodIsUnbindableException(e);
        }
        halfResult = wrapByTranslator(desc.getBound(), desc.getTranslatorStrategy(), halfResult);
        return new BindedElement(halfResult, desc.getPriority());
    }
}
