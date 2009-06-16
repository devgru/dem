package dem.inclass;

import dem.bounding.Filter;
import dem.inclass.exceptions.ClassIsUnbindableException;
import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.translating.ExternalTranslator;
import dem.translating.TranslatorStrategy;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.177
 */
public abstract class AbstractBinder {

    protected static final Class<TranslatorStrategy> WITHOUT_TRANSLATOR = TranslatorStrategy.class;

    public abstract void tryBindMembers(Object target, List<FilterWithPriority> listToFill)
            throws ClassIsUnbindableException;

    @SuppressWarnings("unchecked")
    public static FilterWithPriority wrap(Handles annotation, Filter filterToWrap) throws ElementIsUnbindableException {
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
