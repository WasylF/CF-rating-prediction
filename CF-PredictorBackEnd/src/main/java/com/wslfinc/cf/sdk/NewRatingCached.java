package com.wslfinc.cf.sdk;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.wslfinc.cf.sdk.entities.additional.ContestantResult;
import com.wslfinc.cf.sdk.rating.RatingCalculatorTeam;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Wsl_F
 */
public class NewRatingCached {

  final ExecutorService executor = Executors.newFixedThreadPool(2);

  private int timeToRefreshSeconds;
  LoadingCache<Integer, List<ContestantResult>> cache;

  public NewRatingCached(int timeToRefreshSeconds) {
    this.timeToRefreshSeconds = timeToRefreshSeconds;
    initCache();
  }

  private void initCache() {
    cache = CacheBuilder.newBuilder()
      .refreshAfterWrite(timeToRefreshSeconds, TimeUnit.SECONDS)
      .build(
        new CacheLoader<>() {
          @Override
          public ListenableFuture<List<ContestantResult>> reload(Integer contestId, List<ContestantResult> oldValue) {
            ListenableFutureTask<List<ContestantResult>> task = ListenableFutureTask.create(() -> {
              long before = System.currentTimeMillis();
              System.out.println("Start updating rating: " + before);
              RatingCalculatorTeam ratingCalculator = RatingCalculatorTeam.getRatingCalculator(contestId);
              long before2 = System.currentTimeMillis();
              List<ContestantResult> result = ratingCalculator.getNewRatings();
              long after = System.currentTimeMillis();

              System.out.printf("Rating for contest #%d calculated total in: %d millis, excluding cf request: %d%n",
                contestId, after - before, after - before2);
              return result;
            });
            executor.execute(task);
            return task;
          }

          @Override
          public List<ContestantResult> load(Integer contestId) {
            return Collections.EMPTY_LIST;
          }
        });
  }

  public List<ContestantResult> getValue(int contestId) {
    try {
      return cache.get(contestId);
    } catch (ExecutionException e) {
      e.printStackTrace();
      System.err.println("Couldn't get next rating, because of " + e);
      return Collections.emptyList();
    }
  }

  public Set<Integer> getCachedKeys() {
    return cache.asMap().keySet();
  }

  public void remove(int contestId) {
    cache.invalidate(contestId);
  }
}
