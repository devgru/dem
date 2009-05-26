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
    final Object target;
    static final Class<TranslatorStrategy> WITHOUT_TRANSLATOR = TranslatorStrategy.class;

    AbstractBinder(Object target) {
        this.target = target;
    }

    abstract void tryBindMembers(List<BoundElement> listToUpdate, Class<?> targetClass)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    BoundElement wrap(BindableElement element, TypeFilter filterToWrap) throws ElementIsUnbindableException {
        Class<? extends TranslatorStrategy> translator = element.getTranslatorStrategy();

        if (translator != WITHOUT_TRANSLATOR) {
            try {
                filterToWrap = new ExternalTranslator(filterToWrap, element.getBound(), translator.newInstance());
            } catch (InstantiationException e) {
                throw new ElementIsUnbindableException(e);
            } catch (IllegalAccessException e) {
                throw new ElementIsUnbindableException(e);
            }
        }
        return new BoundElement(filterToWrap, element.getPriority());
    }
}
