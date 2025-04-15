package com.shikhar03stark.kvstore.factory;

import com.shikhar03stark.kvstore.KVStore;

public class KVStoreFactory {

    public static KVStore create(int capacity) {
        return new KVStore(capacity);
    }
}
