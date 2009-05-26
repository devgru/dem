package ru.devg.dem.inclass;

import ru.devg.dem.inclass.binding.ClassWorkerConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: devgru
 * Date: 26.05.2009
 * Time: 18:47:13
 */
public class InclassDispatcherConfiguration extends ClassWorkerConfiguration {

    /**
     * Every event passed to dispatcher can be handled by more than one methods/fields
     */
    public final boolean broadcast;

    public InclassDispatcherConfiguration(boolean strictPrioritization, boolean broadcast) {
        super(strictPrioritization);
        this.broadcast = broadcast;
    }
}
