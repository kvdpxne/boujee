package me.kvdpxne.boujee;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import me.kvdpxne.boujee.exceptions.InvalidCacheSizeException;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;

/**
 * Cache for translation lookups using LRU (Least Recently Used) eviction policy.
 *
 * This implementation provides:
 * - Thread-safe operations without explicit locks where possible
 * - LRU eviction strategy for optimal cache utilization
 * - Dynamic size adjustment with proper validation
 * - Detailed performance monitoring
 *
 * @since 0.2.1
 */
final class TranslationCache {
  private volatile int maxSize;
  private final ConcurrentMap<CacheKey, Object> cache;
  private final ConcurrentMap<CacheKey, Boolean> inQueue;
  private final Queue<CacheKey> lruQueue;
  private final AtomicInteger hits = new AtomicInteger(0);
  private final AtomicInteger misses = new AtomicInteger(0);

  /**
   * Creates a new TranslationCache with the specified maximum size.
   *
   * @param maxSize the maximum number of entries in the cache (must be > 0)
   * @throws InvalidCacheSizeException if maxSize is <= 0
   * @since 0.2.1
   */
  TranslationCache(final int maxSize) {
    validateCacheSize(maxSize);
    this.maxSize = maxSize;
    this.cache = new ConcurrentHashMap<>(maxSize);
    this.inQueue = new ConcurrentHashMap<>(maxSize);
    this.lruQueue = new ConcurrentLinkedQueue<>();
  }

  /**
   * Validates cache size parameter.
   *
   * @param size the size to validate
   * @throws InvalidCacheSizeException if size is invalid
   * @since 0.2.1
   */
  private void validateCacheSize(int size) {
    if (size <= 0) {
      throw new InvalidCacheSizeException("Cache size must be greater than zero, but was: " + size);
    }
  }

  /**
   * Calculates the default cache size based on available memory (9%-11% range).
   *
   * @return the recommended cache size
   * @since 0.2.1
   */
  public static int calculateDefaultCacheSize() {
    // Calculate 10% of available memory (midpoint of 9%-11% range)
    long maxMemory = Runtime.getRuntime().maxMemory();
    int size = (int) (maxMemory * 0.1 / 1024); // 10% of memory in KB

    // Ensure reasonable bounds (min 100, max 50,000)
    return Math.min(Math.max(size, 100), 50_000);
  }

  /**
   * Sets a new maximum size for the cache and evicts entries if necessary.
   *
   * @param newSize the new maximum size (must be > 0)
   * @throws InvalidCacheSizeException if newSize is <= 0
   * @since 0.2.1
   */
  public void setMaxSize(int newSize) {
    validateCacheSize(newSize);

    int oldSize = this.maxSize;
    this.maxSize = newSize;

    // If new size is smaller, evict excess entries
    if (newSize < oldSize) {
      maintainSize();
    }
  }

  /**
   * Returns the current maximum size of the cache.
   *
   * @return the maximum size
   * @since 0.2.1
   */
  public int getMaxSize() {
    return this.maxSize;
  }

  /**
   * Returns the current number of entries in the cache.
   *
   * @return the current size
   * @since 0.2.1
   */
  public int getCurrentSize() {
    return this.cache.size();
  }

  /**
   * Evicts the least recently used entries to maintain the maximum size.
   *
   * @since 0.2.1
   */
  private void maintainSize() {
    if (cache.size() <= maxSize || maxSize <= 0) {
      return;
    }

    // Używamy synchronized dla operacji na kolejkach
    synchronized (lruQueue) {
      while (cache.size() > maxSize && maxSize > 0) {
        CacheKey oldestKey = null;
        while (oldestKey == null) {
          oldestKey = lruQueue.poll();
          if (oldestKey == null) {
            return; // Brak kluczy do usunięcia
          }
          // Sprawdzamy, czy klucz jest nadal w inQueue
          if (inQueue.remove(oldestKey, true)) {
            cache.remove(oldestKey);
          } else {
            oldestKey = null; // Klucz został odświeżony, szukamy dalej
          }
        }
      }
    }
  }

  /**
   * Clears all entries from the cache.
   *
   * @since 0.2.1
   */
  public void clear() {
    cache.clear();
    inQueue.clear();
    lruQueue.clear();
    hits.set(0);
    misses.set(0);
  }

  /**
   * Retrieves a message from the cache.
   *
   * @param localeSource the locale source
   * @param translationKey the translation key
   * @return the message if found, null otherwise
   * @throws NullPointerException if localeSource or translationKey is null
   * @since 0.2.1
   */
  public TranslationMessage getMessage(
    final LocaleSource localeSource,
    final TranslationKey translationKey
  ) {
    if (localeSource == null) {
      throw new NullPointerException("localeSource must not be null");
    }
    if (translationKey == null) {
      throw new NullPointerException("translationKey must not be null");
    }

    final CacheKey key = new CacheKey(localeSource, translationKey, true);
    final Object result = cache.get(key);
    if (result instanceof TranslationMessage) {
      // Update LRU order
      synchronized (lruQueue) {
        lruQueue.remove(key);
        inQueue.put(key, true);
        lruQueue.add(key);
      }
      hits.incrementAndGet();
      return (TranslationMessage) result;
    }
    misses.incrementAndGet();
    return null;
  }

  /**
   * Retrieves text from the cache.
   *
   * @param localeSource the locale source
   * @param translationKey the translation key
   * @return the text if found, null otherwise
   * @throws NullPointerException if localeSource or translationKey is null
   * @since 0.2.1
   */
  public TranslationText getText(
    final LocaleSource localeSource,
    final TranslationKey translationKey
  ) {
    if (localeSource == null) {
      throw new NullPointerException("localeSource must not be null");
    }
    if (translationKey == null) {
      throw new NullPointerException("translationKey must not be null");
    }

    final CacheKey key = new CacheKey(localeSource, translationKey, false);
    final Object result = cache.get(key);
    if (result instanceof TranslationText) {
      // Update LRU order
      synchronized (lruQueue) {
        lruQueue.remove(key);
        inQueue.put(key, true);
        lruQueue.add(key);
      }
      hits.incrementAndGet();
      return (TranslationText) result;
    }
    misses.incrementAndGet();
    return null;
  }

  /**
   * Adds a message to the cache.
   *
   * @param localeSource the locale source
   * @param translationKey the translation key
   * @param message the message to cache
   * @throws NullPointerException if any parameter is null
   * @since 0.2.1
   */
  public void putMessage(
    final LocaleSource localeSource,
    final TranslationKey translationKey,
    final TranslationMessage message
  ) {
    if (localeSource == null) {
      throw new NullPointerException("localeSource must not be null");
    }
    if (translationKey == null) {
      throw new NullPointerException("translationKey must not be null");
    }
    if (message == null) {
      throw new NullPointerException("message must not be null");
    }

    CacheKey key = new CacheKey(localeSource, translationKey, true);
    synchronized (lruQueue) {
      // Remove previous entry from queue if exists
      lruQueue.remove(key);
      cache.put(key, message);
      inQueue.put(key, true);
      lruQueue.add(key);
    }
    maintainSize();
  }

  /**
   * Adds text to the cache.
   *
   * @param localeSource the locale source
   * @param translationKey the translation key
   * @param text the text to cache
   * @throws NullPointerException if any parameter is null
   * @since 0.2.1
   */
  public void putText(
    final LocaleSource localeSource,
    final TranslationKey translationKey,
    final TranslationText text
  ) {
    if (localeSource == null) {
      throw new NullPointerException("localeSource must not be null");
    }
    if (translationKey == null) {
      throw new NullPointerException("translationKey must not be null");
    }
    if (text == null) {
      throw new NullPointerException("text must not be null");
    }

    CacheKey key = new CacheKey(localeSource, translationKey, false);
    synchronized (lruQueue) {
      // Remove previous entry from queue if exists
      lruQueue.remove(key);
      cache.put(key, text);
      inQueue.put(key, true);
      lruQueue.add(key);
    }
    maintainSize();
  }

  /**
   * Gets performance statistics for the cache.
   *
   * @return a string with cache statistics
   * @since 0.2.1
   */
  public String getStatistics() {
    int total = hits.get() + misses.get();
    if (total == 0) {
      return "Cache: 0 hits, 0 misses, size: 0/" + maxSize;
    }
    double hitRate = (double) hits.get() / total * 100;
    return String.format("Cache: %d hits, %d misses, hit rate: %.2f%%, size: %d/%d",
      hits.get(), misses.get(), hitRate, cache.size(), maxSize);
  }

  /**
   * Gets hit count for the cache.
   *
   * @return the number of cache hits
   * @since 0.2.1
   */
  public int getHitCount() {
    return hits.get();
  }

  /**
   * Gets miss count for the cache.
   *
   * @return the number of cache misses
   * @since 0.2.1
   */
  public int getMissCount() {
    return misses.get();
  }

  /**
   * Gets hit rate for the cache.
   *
   * @return the cache hit rate as a percentage
   * @since 0.2.1
   */
  public double getHitRate() {
    int total = hits.get() + misses.get();
    if (total == 0) return 0.0;
    return (double) hits.get() / total * 100;
  }
}