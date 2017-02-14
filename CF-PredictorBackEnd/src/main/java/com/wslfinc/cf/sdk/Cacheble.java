package com.wslfinc.cf.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores data at the cache.
 *
 * @author Wsl_F
 * @param <CachedT> type of cached data
 */
public abstract class Cacheble<CachedT> {
    
    protected static final int MAX_CACHED_CONTESTS = 5;
    
    final long TIME_TO_RECALCULATE_MS;
    /**
     * 3 minutes
     */
    protected static final int MAX_TIME_INTERVAL_MS = 180_000;
    
    protected static final int INITIAL_USING = MAX_CACHED_CONTESTS * 5 + 1;

    /**
     * Key - index, Value - cached value. Map contains not more than
     * {@code MAX_CACHED_CONTESTS} items
     */
    private final Map<Integer, List<CachedT>> cache = new HashMap<>();

    /**
     * Key - index, Value - {number of requests, time of last request, time of
     * adding}.
     */
    private final Map<Integer, long[]> cacheUsing = new HashMap<>();
    
    public Cacheble(long recalculateTimeMS) {
        this.TIME_TO_RECALCULATE_MS = recalculateTimeMS;
    }
    
    public void removeFromCahce(int index) {
        cacheUsing.remove(index);
        cache.remove(index);
    }
    
    private void decreaseUsing() {
        long time = System.currentTimeMillis();
        for (Integer index : cacheUsing.keySet()) {
            if (cacheUsing.get(index)[0] <= 1
                    || time - cacheUsing.get(index)[1] > MAX_TIME_INTERVAL_MS) {
                removeFromCahce(index);
            } else {
                cacheUsing.get(index)[0]--;
                if (time - cacheUsing.get(index)[2] > TIME_TO_RECALCULATE_MS) {
                    // to prevent calling few times before getting new result
                    cacheUsing.get(index)[2] = time;
                    CalculatingStraigth calculator = new CalculatingStraigth(this, index);
                    new Thread(calculator).start();
                }
            }
        }
    }
    
    private List<CachedT> getChached(int index) {
        long time = System.currentTimeMillis();
        cacheUsing.get(index)[0]+= 2;
        cacheUsing.get(index)[1] = time;
        return cache.get(index);
    }
    
    protected abstract List<CachedT> getStraight(int index);

    /**
     * Getting value from cache if this is possible otherwise - getStraight.
     * Also add value to the cache if it's needed.
     *
     * @param index index of cached value
     * @return value
     */
    public List<CachedT> getValue(int index) {
        List<CachedT> result;
        if (cache.containsKey(index)) {
            result = getChached(index);
        } else {
            result = getStraight(index);
            addIfNeed(index, result);
        }
        
        decreaseUsing();
        
        return result;
    }
    
    private void addIfNeed(int index, List<CachedT> value) {
        if (cache.size() < MAX_CACHED_CONTESTS) {
            cache.put(index, value);
            long time = System.currentTimeMillis();
            long[] using = new long[]{INITIAL_USING, time, time};
            cacheUsing.put(index, using);
        }
    }
    
    void updateValueInCache(int index) {
        List<CachedT> value = getStraight(index);
        cache.put(index, value);
        long time = System.currentTimeMillis();
        long lastTimeUsing;
        long reqNumber;
        if (cacheUsing.containsKey(index)) {
            lastTimeUsing = cacheUsing.get(index)[1];
            reqNumber = cacheUsing.get(index)[0];
        } else {
            lastTimeUsing = time - MAX_TIME_INTERVAL_MS / 2;
            reqNumber = 2;
        }
        long[] using = new long[]{reqNumber, lastTimeUsing, time};
        cacheUsing.put(index, using);
    }
    
    public List<Integer> getCachedIds() {
        return new ArrayList<>(cache.keySet());
    }
}
