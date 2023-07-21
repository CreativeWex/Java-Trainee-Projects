package io.ylab.intensive.lesson03.DatedMap;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private Map<String, Entry> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, new Entry(value, new Date()));
    }

    @Override
    public String get(String key) {
        Entry entry = map.get(key);
        return entry != null ? entry.getValue() : null;
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        Entry entry = map.get(key);
        return entry != null ? entry.getInsertionDate() : null;
    }

    private static class Entry {
        private final String value;
        private final Date insertionDate;
        public Entry(String value, Date insertionDate) {
            this.value = value;
            this.insertionDate = insertionDate;
        }
        public String getValue() {
            return value;
        }
        public Date getInsertionDate() {
            return insertionDate;
        }
    }
}