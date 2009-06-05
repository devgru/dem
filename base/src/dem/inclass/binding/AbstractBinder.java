package dem.inclass.binding;

import dem.bounding.Filter;
import dem.inclass.ClassIsUnbindableException;
import dem.inclass.Handles;
import dem.inclass.binding.exceptions.ElementIsUnbindableException;
import dem.translating.ExternalTranslator;
import dem.translating.TranslatorStrategy;

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
    protected final FilterWithPriority wrap(Handles annotation, Filter filterToWrap) throws ElementIsUnbindableException {
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
