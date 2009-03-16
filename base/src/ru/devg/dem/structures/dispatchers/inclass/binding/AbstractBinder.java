package ru.devg.dem.structures.dispatchers.inclass.binding;

import java.util.List;

/**
 * @author Devgru &lt;java@devg.ru&gt;
 * @version 0.177
 */
interface AbstractBinder {
    public void tryBindMembers(List<BindedMember> listToUpdate,Class<?> targetClass);

}
