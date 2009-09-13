package dem.bundles;

import dem.quanta.Handler;

/**
 * HandlerBundle is a way to use some strategies of bundling handlers.
 * If you want to use your own strategy - you just have
 * to implement this interface.
 * <p/>
 * There is no general contract about duplicate
 * {@link Handler handlers} in one bundle, each implementation of {@link HandlerBundle}
 * should specify its behavior in case of addition one {@link Handler}
 * more than one time.
 *
 * @author Devgru &lt;java@devg.ru&gt;
 * @since 0.15
 */
public interface HandlerBundle<H extends Handler> {

    /**
     * Adds handler to {@link HandlerBundle bundle}.
     * May do nothing if you try to add one handler
     * to bundle more than one time.
     *
     * @param handler handler to add
     * @return <code>true</code> if and only if this {@link HandlerBundle} guarantees that
     *         the {@link Handler} was successfully added;<br/>
     *         <code>false</code> if {@link Handler} was not added or if
     *         {@link HandlerBundle bundle} already contains this handler.
     * @throws IllegalArgumentException <em>should be</em> thrown
     *                                  if handler is <code>null</code>.
     */
    public boolean addHandler(H handler) throws IllegalArgumentException;

    /**
     * Removes handler from {@link HandlerBundle bundle}.
     * Should not throw any exception.
     * Should do nothing if handler is <code>null</code> or if
     * all its instances are already removed from bundle.
     *
     * @param handler handler to add
     * @return <code>true</code> if and only if this {@link HandlerBundle} had removed
     *         given {@link Handler};                     <br/>
     *         <code>false</code> if {@link Handler} was not contained or if
     *         it is null.
     */
    public boolean removeHandler(H handler);

}
