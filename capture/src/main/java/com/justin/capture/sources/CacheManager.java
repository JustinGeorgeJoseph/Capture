package com.justin.capture.sources;

import android.util.LruCache;

import com.justin.capture.models.CaptureModel;


public class CacheManager {
    private int maxCacheSize;
    private static CacheManager instance;
    private LruCache<String, CaptureModel> mCache;

    private CacheManager() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        maxCacheSize =   maxMemory / 8;
        mCache = new LruCache<>(maxCacheSize);
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public CaptureModel getData(String key) {
        return mCache.get(key);
    }

    public boolean putData(String key, CaptureModel captureModel) {
        return mCache.put(key, captureModel) != null;
    }

    public void clearCache() {
        mCache.evictAll();
    }
}
