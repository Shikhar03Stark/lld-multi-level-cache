package com.shikhar03stark.cache.impl;

import com.shikhar03stark.cache.CacheClient;
import com.shikhar03stark.cache.chain.CacheChain;

public class MultiLevelCacheClient implements CacheClient {

    private CacheChain firstCacheChain;

    public MultiLevelCacheClient(CacheChain firstCacheChain) {
        this.firstCacheChain = firstCacheChain;
    }

    @Override
    public String read(String key) {
        if (firstCacheChain == null) {
            throw new IllegalStateException("No cache chain available");
        }

        System.out.println("GET " + key);

        final long startTime = System.currentTimeMillis();
        String value = firstCacheChain.get(key);
        final long endTime = System.currentTimeMillis();
        final long timeTaken = endTime - startTime;

        System.out.println("Time taken: " + timeTaken + " ms");

        return value;
    }

    @Override
    public void write(String key, String value) {
        if (firstCacheChain == null) {
            throw new IllegalStateException("No cache chain available");
        }

        System.out.println("PUT " + key + " = " + value);

        final long startTime = System.currentTimeMillis();
        firstCacheChain.set(key, value);
        final long endTime = System.currentTimeMillis();
        final long timeTaken = endTime - startTime;

        System.out.println("Time taken: " + timeTaken + " ms");
    }

}
