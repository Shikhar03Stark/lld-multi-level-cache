package com.shikhar03stark.cache.chain.impl.options;

public class InMemoryCacheChainOptions {
    private final long readTimeInMillis;
    private final long writeTimeInMillis;

    public InMemoryCacheChainOptions(long readTimeInMillis, long writeTimeInMillis) {
        this.readTimeInMillis = readTimeInMillis;
        this.writeTimeInMillis = writeTimeInMillis;
    }

    public long getReadTimeInMillis() {
        return readTimeInMillis;
    }

    public long getWriteTimeInMillis() {
        return writeTimeInMillis;
    }

    
}
