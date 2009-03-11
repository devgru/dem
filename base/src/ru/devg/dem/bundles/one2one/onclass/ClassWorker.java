package ru.devg.dem.bundles.one2one.onclass;

import ru.devg.dem.filtering.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/**
 * @author Devgru devgru@mail.ru
 * @version 0.176
 */
final class ClassWorker {
    private final InplaceDispatchable target;

    ClassWorker(InplaceDispatchable target) {
        this.target = target;
    }

    public List<Filter<?>> result() {
        List<DispatchedEntry> fieldEntryList = new FieldWorker(target).result();
        List<DispatchedEntry> methodEntryList = new MethodWorker(target).result();
        int capacity = fieldEntryList.size() + methodEntryList.size();

        List<DispatchedEntry> grabbed;
        grabbed = new ArrayList<DispatchedEntry>(capacity);
        grabbed.addAll(fieldEntryList);
        grabbed.addAll(methodEntryList);
        Collections.sort(grabbed);
        return grabResult(grabbed);
    }
    
    private List<Filter<?>> grabResult(List<DispatchedEntry> entries){
        List<Filter<?>> grabbed = new LinkedList<Filter<?>>();
        for(DispatchedEntry entry : entries){
            grabbed.add(entry.getFilter());
        }
        return grabbed;
    }
}
