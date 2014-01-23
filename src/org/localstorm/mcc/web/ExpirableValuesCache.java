package org.localstorm.mcc.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author localstorm
 */
public class ExpirableValuesCache<K, T> {

    private final ConcurrentHashMap<K, T> store;
    private final LinkedBlockingDeque<K> cyclic;
    private final int size;
    
    public ExpirableValuesCache(int size)
    {
        store = new ConcurrentHashMap<K, T>();
        cyclic = new LinkedBlockingDeque<K>();
        this.size = size;
    }

    public T get(K key)
    {
        return store.get(key);
    }

    @SuppressWarnings("unchecked")
    public synchronized void put(K key, T value)
    {
        cyclic.add(key);
        while (cyclic.size() > size) {
           K removed = cyclic.removeFirst();
           store.remove(removed);
        }

        store.put(key, value);
    }
   
}
