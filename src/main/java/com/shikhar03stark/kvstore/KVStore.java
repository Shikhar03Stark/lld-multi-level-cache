package com.shikhar03stark.kvstore;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class KVStore {

    private final Map<String, String> store;
    private final int capacity;
    private final Deque<String> keysQueue;

    // Keeping eviction policy very simple for now
    // evict the oldest key when the capacity is reached (in the order of writes)
    
    public KVStore(int capacity) {
        store = new HashMap<>();
        this.capacity = capacity;
        keysQueue = new LinkedList<>();
    }

    public void put(String key, String value) {
        int idx = 0;
        Iterator<String> iterator = keysQueue.iterator();
        boolean isKeyPresent = false;
        while(idx < capacity && iterator.hasNext()) {
            String k = iterator.next();
            if (k.equals(key)) {
                isKeyPresent = true;
                break;
            }
        }
        if (!isKeyPresent) {
            keysQueue.add(key);
        }

        // remove the oldest key if the capacity is reached
        if (keysQueue.size() > capacity) {
            String oldestKey = keysQueue.removeFirst();
            store.remove(oldestKey);
        }

        store.put(key, value);
    }

    public String get(String key) {
        int index = 0;
        for (String k : keysQueue) {
            if (k.equals(key) && index < capacity) {
                return store.get(key);
            }
            else if (index >= capacity) break;
            index++;
        }
        return null;
    }

    public boolean keyExists(String key) {
        return get(key) != null;
    }

}
