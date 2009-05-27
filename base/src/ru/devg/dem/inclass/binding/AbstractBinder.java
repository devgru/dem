package ru.devg.dem.inclass.binding;

import ru.devg.dem.bounding.TypeFilter;
import ru.devg.dem.inclass.exceptions.ClassIsUnbindableException;
import ru.devg.dem.inclass.exceptions.ElementIsUnbindableException;
import ru.devg.dem.inclass.Handles;
import ru.devg.dem.translating.ExternalTranslator;
import ru.devg.dem.translating.TranslatorStrategy;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.177
 */
public abstract class AbstractBinder {
    protected final Object target;
    protected static final Class<TranslatorStrategy> WITHOUT_TRANSLATOR = TranslatorStrategy.class;

    protected AbstractBinder(Object target) {
        this.target = target;
    }

    public abstract void tryBindMembers(List<FilterWithPriority> listToFill, Class<?> targetClass)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    protected final FilterWithPriority wrap(Handles annotation, TypeFilter filterToWrap) throws ElementIsUnbindableException {
        Class<? extends TranslatorStrategy> translator = annotation.translator();

        if (translator != WITHOUT_TRANSLATOR) {
            try {
                filterToWrap = new ExternalTranslator(filterToWrap, annotation.value(), translator.newInstance());
            } catch (Exception e) {
                throw new ElementIsUnbindableException(e);
            }
        }
        return new FilterWithPriority(filterToWrap, annotation.priority());
    }
}
