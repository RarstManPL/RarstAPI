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
        template = template
                .subList(0, template.size() > 6 ? 6 : template.size() - 1)
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
                    for(int i = 0; i < 9; i++) {
                        this.templateMap.put(entrySet.getValue(), new Slot(entrySet.getKey() + 1, i + 1));
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
