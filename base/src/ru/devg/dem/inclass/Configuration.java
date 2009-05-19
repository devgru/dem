package ru.devg.dem.inclass;

/**
 * Inclass dispatcher configuration. May be passed to
 * {@link InclassDispatcher#InclassDispatcher(Object, java.util.EnumSet)}.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.178
 */
public enum Configuration {

    /**
     * Every event passed to dispatcher can be handled by more than one methods/fields
     */
    broadcast,

    /**
     * Every class member with {@link Handles} annotation must have it's own priority,
     * else {@link InclassDispatcher#InclassDispatcher(Object, java.util.EnumSet)} will throw a
     * {@link ru.devg.dem.inclass.exceptions.ClassIsUnbindableException}
     */
    strictPrioritization
}
