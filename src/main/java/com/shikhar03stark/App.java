package com.shikhar03stark;

import com.shikhar03stark.cache.CacheClient;
import com.shikhar03stark.cache.chain.CacheChain;
import com.shikhar03stark.cache.chain.impl.InMemoryCacheChain;
import com.shikhar03stark.cache.chain.impl.options.InMemoryCacheChainOptions;
import com.shikhar03stark.cache.impl.MultiLevelCacheClient;
import com.shikhar03stark.kvstore.factory.KVStoreFactory;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
    
        CacheChain c1 = createCacheChain(3, 80, 80);
        CacheChain c2 = createCacheChain(5, 110, 125);
        CacheChain c3 = createCacheChain(10, 150, 150);
        CacheChain c4 = createCacheChain(20, 200, 200);

        c1.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);

        final CacheClient cacheClient = new MultiLevelCacheClient(c1);
        
        cacheClient.write("Shruty", "Nice");
        cacheClient.write("Shikhar", "Good");
        cacheClient.write("Harshit", "Okay");
        cacheClient.write("Adam", "Alright");
        cacheClient.write("Sam", "Theek hai");

        System.out.println(cacheClient.read("Paul"));
        System.out.println(cacheClient.read("Shruty"));
        System.out.println(cacheClient.read("Shikhar"));
        System.out.println(cacheClient.read("Shruty")); // will be lesser than the first call to Shruty


    }

    private static CacheChain createCacheChain(int capacity, long readTimeInMillis, long writeTimeInMillis) {
        final InMemoryCacheChainOptions options = new InMemoryCacheChainOptions(readTimeInMillis, writeTimeInMillis);
        return new InMemoryCacheChain(options, KVStoreFactory.create(capacity));
    }
}
