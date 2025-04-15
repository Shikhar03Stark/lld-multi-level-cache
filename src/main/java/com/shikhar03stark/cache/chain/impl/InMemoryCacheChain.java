package com.shikhar03stark.cache.chain.impl;

import com.shikhar03stark.cache.chain.CacheChain;
import com.shikhar03stark.cache.chain.impl.options.InMemoryCacheChainOptions;
import com.shikhar03stark.kvstore.KVStore;

public class InMemoryCacheChain extends CacheChain {

    private final InMemoryCacheChainOptions options;
    private final KVStore store;

    public InMemoryCacheChain(InMemoryCacheChainOptions options, KVStore store) {
        this.options = options;
        this.store = store;
    }

    private void simulateReadTime() {
        try {
            Thread.sleep(options.getReadTimeInMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    private void simulateWriteTime() {
        try {
            Thread.sleep(options.getWriteTimeInMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    @Override
    public String get(String key) {
        // make a get call to the store
        simulateReadTime();
        String value = store.get(key);
        if (value != null) {
            return value;
        }

        // check in next cache chain
        if (nextChain != null) {
            String valueFromNext = nextChain.get(key);
            if (valueFromNext != null) {
                // update the store
                simulateWriteTime();
                store.put(key, valueFromNext);
            }
            return valueFromNext;
        }

        return null; // this is the last cache chain.
        
    }

    @Override
    public void set(String key, String value) {
        simulateReadTime();
        final String existingValue = store.get(key);

        if (existingValue != null && existingValue.equals(value)) {
            return; // no need to update the store and subsequent cache chains
        }

        simulateWriteTime();
        store.put(key, value);

        if (nextChain != null) {
            nextChain.set(key, value);
        }
    }

}
