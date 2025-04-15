package com.shikhar03stark.cache;

public interface CacheClient {

    String read(String key);

    void write(String key, String value);

    // stat
}
