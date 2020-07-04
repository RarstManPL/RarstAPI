package me.rarstman.rarstapi.inventory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import one.util.streamex.EntryStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryTemplate {

    private final Multimap<String, Slot> templateMap = ArrayListMultimap.create();

    public InventoryTemplate(List<String> template) {
        if(template.isEmpty()) {
            return;
        }
        template = template
                .subList(0, Math.min(template.size(), 6))
                .stream()
                .map(string -> {
                    if(string.length() > 9) {
                        string = string.substring(0, 9);
                    }
                    return string;
                })
                .collect(Collectors.toList());

        EntryStream.of(template)
                .forEach(entrySet -> {
                    System.out.println(entrySet.getKey() + " " + entrySet.getValue());
                    for(int i = 0; i < entrySet.getValue().length(); i++) {
                        this.templateMap.put(String.valueOf(entrySet.getValue().charAt(i)), new Slot(entrySet.getKey() + 1, i + 1));
                    }
                });
    }

    public InventoryTemplate(final String... template) {
        this(Arrays.asList(template));
    }

    public Collection<Slot> getSlots(final String field) {
        return this.templateMap.get(field);
    }

}
