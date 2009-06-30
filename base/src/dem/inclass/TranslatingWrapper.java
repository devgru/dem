package dem.inclass;

import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.translating.ExternalTranslator;
import dem.translating.TranslatorStrategy;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.183
 */
final class TranslatingWrapper implements Wrapper {
    @SuppressWarnings("unchecked")
    public void wrap(AnnotatedElement clz, FilterWithPriority filterWithPriority) throws ElementIsUnbindableException {

        Translated translated = clz.getAnnotation(Translated.class);
        if (translated == null) {
            return;
        }
        Class<? extends TranslatorStrategy> translator = translated.value();
        try {
            ExternalTranslator externalTranslator =
                    new ExternalTranslator(filterWithPriority, translated.bound(), translator.newInstance());
            filterWithPriority.setFilter(externalTranslator);
        } catch (Exception e) {
            throw new ElementIsUnbindableException(e);
        }

    }
}
