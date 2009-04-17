package ru.devg.dem.inclass.binding;

import ru.devg.dem.filtering.TypeFilter;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.translating.ExternalTranslator;
import ru.devg.dem.translating.TranslatorStrategy;

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
    protected BindedElement wrap(BindableElementDescriptor desc, TypeFilter halfResult) throws ElementIsUnbindableException {
        Class<? extends TranslatorStrategy> translator = desc.getTranslatorStrategy();

        if (translator != TranslatorStrategy.class) {
            try {
                halfResult = new ExternalTranslator(halfResult, desc.getBound(), translator.newInstance());
            } catch (InstantiationException e) {
                throw new ElementIsUnbindableException(e);
            } catch (IllegalAccessException e) {
                throw new ElementIsUnbindableException(e);
            }
        }
        return new BindedElement(halfResult, desc.getPriority());
    }
}
