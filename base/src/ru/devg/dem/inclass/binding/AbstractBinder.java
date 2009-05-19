package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.translating.ExternalTranslator;
import ru.devg.dem.translating.TranslatorStrategy;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.177
 */
abstract class AbstractBinder {
    protected final Object target;

    AbstractBinder(Object target) {
        this.target = target;
    }

    public abstract void tryBindMembers(List<BoundElement> listToUpdate, Class<?> targetClass)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    protected BoundElement wrap(BindableElement desc, TypeFilter filterToWrap) throws ElementIsUnbindableException {
        Class<? extends TranslatorStrategy> translator = desc.getTranslatorStrategy();

        if (translator != BindableElement.WITHOUT_TRANSLATOR) {
            try {
                filterToWrap = new ExternalTranslator(filterToWrap, desc.getBound(), translator.newInstance());
            } catch (InstantiationException e) {
                throw new ElementIsUnbindableException(e);
            } catch (IllegalAccessException e) {
                throw new ElementIsUnbindableException(e);
            }
        }
        return new BoundElement(filterToWrap, desc.getPriority());
    }
}
