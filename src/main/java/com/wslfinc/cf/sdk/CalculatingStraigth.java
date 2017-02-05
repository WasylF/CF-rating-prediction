package com.wslfinc.cf.sdk;

/**
 *
 * @author Wsl_F
 */
public class CalculatingStraigth<CachedT> implements Runnable {

    final Cacheble<CachedT> object;
    final int index;

    public CalculatingStraigth(Cacheble<CachedT> object, int index) {
        this.object = object;
        this.index = index;
    }

    @Override
    public void run() {
        object.updateValueInCache(index);
    }

}
