package top.itlq.redis.base;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int size;

    public LRUCache(int size){
        super((int)Math.round(size / 0.75) + 1, 0.75f, true);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > size;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1, null);
        lruCache.put(2, null);
        lruCache.put(3, null);
        lruCache.get(1);
        lruCache.forEach((k,v)->{
            System.out.println(k + ": " + v);
        });
    }
}
