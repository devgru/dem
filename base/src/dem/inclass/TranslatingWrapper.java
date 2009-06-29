package dem.inclass;

import dem.inclass.exceptions.ElementIsUnbindableException;
import dem.translating.ExternalTranslator;
import dem.translating.TranslatorStrategy;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 29.06.2009
 * Time: 16:38:44
 */
public class TranslatingWrapper implements Wrapper {
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
