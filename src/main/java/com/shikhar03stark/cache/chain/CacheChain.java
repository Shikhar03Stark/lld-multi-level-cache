package com.shikhar03stark.cache.chain;

import com.shikhar03stark.cache.CacheClient;

public abstract class CacheChain {

    protected CacheChain nextChain;

    public void setNextChain(CacheChain nextCacheChain) {
        this.nextChain = nextCacheChain;
    }

    public abstract String get(String key);

    public abstract void set(String key, String value);
}
