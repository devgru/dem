package ru.devg.dem.inclass;

/**
 * Inclass dispatcher configuration. May be passed to
 * {@link ru.devg.dem.inclass.InclassDispatcher#InclassDispatcher(Object, java.util.EnumSet)}.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.178
 */
public class ClassWorkerConfiguration {

    /**
     * Every class member with {@link ru.devg.dem.inclass.Handles} annotation must have it's own priority,
     * else {@link ru.devg.dem.inclass.InclassDispatcher#InclassDispatcher(Object, java.util.EnumSet)} will throw a
     * {@link ru.devg.dem.inclass.exceptions.ClassIsUnbindableException}
     */
    public final boolean strictPrioritization;

    public ClassWorkerConfiguration(boolean strictPrioritization) {
        this.strictPrioritization = strictPrioritization;
    }
}
