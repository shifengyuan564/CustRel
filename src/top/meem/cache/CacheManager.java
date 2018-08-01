package top.meem.cache;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * 缓存管理
 *
 * @author Administrator
 */
public class CacheManager {

    private static Logger log = Logger.getLogger(CacheManager.class);

    //消息缓存
    private static HashMap<String, Cache> cache = new HashMap<String, Cache>();

    private CacheManager() {
        super();
    }


    /**
     * 清除所有缓存
     */
    public synchronized static void clearCache() {
        Set<String> keys = CacheManager.getKeys();
        for (String key : keys) {
            if (new Date().getTime() - CacheManager.getCacheIgnoreTime(key).getDate() > CacheManager.getCacheIgnoreTime(key).getMaxInactiveInterval()) {
                CacheManager.clearCache(key);
            }
        }
    }

    public synchronized static void clearCache(String key) {
        cache.remove(key);
    }

    public static void setCache(String key, String val, Long maxInactiveInterval) {
        Cache c = new Cache(val, maxInactiveInterval);
        cache.put(key, c);
    }


    public static Set<String> getKeys() {
        return cache.keySet();
    }

    public synchronized static Cache getCache(String key) {

        Cache c = cache.get(key);
        if (c == null) {
            return null;
        }
        if (new Date().getTime() - c.getDate() > c.getMaxInactiveInterval()) {
            CacheManager.clearCache(key);
            return null;
        }
        return cache.get(key);
    }

    public synchronized static Cache getCacheIgnoreTime(String key) {
        Cache c = cache.get(key);
        if (c == null) {
            return null;
        }
        return cache.get(key);
    }

}
