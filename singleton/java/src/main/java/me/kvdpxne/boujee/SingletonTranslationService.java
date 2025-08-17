package me.kvdpxne.boujee;

/**
 * Singleton implementation of TranslationService with dynamic cache configuration.
 *
 * @since 0.2.0
 */
public final class SingletonTranslationService extends DefaultTranslationService {
  /**
   * @since 0.2.0
   */
  private static volatile SingletonTranslationService instance;

  /**
   * @since 0.2.0
   */
  public static SingletonTranslationService getInstance() {
    if (null == instance) {
      synchronized (SingletonTranslationService.class) {
        if (null == instance) {
          instance = new SingletonTranslationService();
        }
      }
    }
    return instance;
  }

  /**
   * Gets or creates an instance with the specified cache size mode.
   *
   * @param cacheSizeMode the cache size mode
   * @return the singleton instance
   * @since 0.2.0
   */
  public static SingletonTranslationService getInstance(CacheSizeMode cacheSizeMode) {
    if (null == instance) {
      synchronized (SingletonTranslationService.class) {
        if (null == instance) {
          instance = new SingletonTranslationService(cacheSizeMode);
        }
      }
    }
    return instance;
  }

  /**
   * Gets or creates an instance with the specified cache size mode and manual size.
   *
   * @param cacheSizeMode the cache size mode
   * @param manualCacheSize the manual cache size
   * @return the singleton instance
   * @since 0.2.0
   */
  public static SingletonTranslationService getInstance(
    CacheSizeMode cacheSizeMode,
    int manualCacheSize
  ) {
    if (null == instance) {
      synchronized (SingletonTranslationService.class) {
        if (null == instance) {
          instance = new SingletonTranslationService(cacheSizeMode, manualCacheSize);
        }
      }
    }
    return instance;
  }

  /**
   * @since 0.2.0
   */
  private SingletonTranslationService() {
    super(CacheSizeMode.AUTOMATIC);
  }

  /**
   * @since 0.2.0
   */
  private SingletonTranslationService(CacheSizeMode cacheSizeMode) {
    super(cacheSizeMode);
  }

  /**
   * @since 0.2.0
   */
  private SingletonTranslationService(
    CacheSizeMode cacheSizeMode,
    int manualCacheSize
  ) {
    super(cacheSizeMode, manualCacheSize);
  }
}