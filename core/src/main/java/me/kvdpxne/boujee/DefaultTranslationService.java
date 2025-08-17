package me.kvdpxne.boujee;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import me.kvdpxne.boujee.exceptions.InvalidCacheSizeException;
import me.kvdpxne.boujee.exceptions.LocaleNotSupportedException;
import me.kvdpxne.boujee.exceptions.TranslationKeyNotFoundException;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.LocaleTranslations;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;


/**
 * Enhanced implementation of TranslationService with dynamic cache management.
 *
 * @since 0.2.1
 */
public class DefaultTranslationService implements TranslationService {
  private final ConcurrentMap<LocaleSource, LocaleTranslations> translations
    = new ConcurrentHashMap<>();
  private final TranslationCache translationCache;
  private volatile CacheSizeMode cacheSizeMode = CacheSizeMode.DEFAULT;
  private volatile int manualCacheSize = 1000;

  private volatile LocaleSource defaultLocaleSource = null;
  private volatile LocaleTranslations defaultLocaleTranslations = null;

  /**
   * Creates a new translation service with specified default locale and cache
   * size.
   *
   * @param localeSource the default locale source
   * @param cacheSize    maximum number of entries in the translation cache
   * @throws NullPointerException      if localeSource is null
   * @throws InvalidCacheSizeException if cacheSize is invalid
   * @since 0.2.1
   */
  public DefaultTranslationService(
    final LocaleSource localeSource,
    final int cacheSize
  ) {
    if (localeSource == null) {
      throw new NullPointerException("localeSource must not be null");
    }
    this.defaultLocaleSource = localeSource;
    this.translationCache = new TranslationCache(cacheSize);
    this.cacheSizeMode = CacheSizeMode.MANUAL;
    this.manualCacheSize = cacheSize;
  }

  /**
   * Creates a new translation service with specified default locale.
   *
   * @param localeSource the default locale source
   * @throws NullPointerException if localeSource is null
   * @since 0.2.1
   */
  public DefaultTranslationService(final LocaleSource localeSource) {
    this(localeSource, 1000);
  }

  /**
   * Creates a new translation service with specified cache size mode.
   *
   * @param cacheSizeMode the cache size mode
   * @throws NullPointerException if cacheSizeMode is null
   * @since 0.2.1
   */
  public DefaultTranslationService(final CacheSizeMode cacheSizeMode) {
    if (cacheSizeMode == null) {
      throw new NullPointerException("cacheSizeMode must not be null");
    }
    this.translationCache = createCache(cacheSizeMode);
    this.cacheSizeMode = cacheSizeMode;
  }

  /**
   * Creates a new translation service with specified cache size mode and manual
   * size.
   *
   * @param cacheSizeMode   the cache size mode
   * @param manualCacheSize the manual cache size (used when cacheSizeMode is
   *                        MANUAL)
   * @throws NullPointerException      if cacheSizeMode is null
   * @throws InvalidCacheSizeException if manualCacheSize is invalid when
   *                                   required
   * @since 0.2.1
   */
  public DefaultTranslationService(
    final CacheSizeMode cacheSizeMode,
    final int manualCacheSize
  ) {
    if (cacheSizeMode == null) {
      throw new NullPointerException("cacheSizeMode must not be null");
    }

    if (cacheSizeMode == CacheSizeMode.MANUAL && manualCacheSize <= 0) {
      throw new InvalidCacheSizeException("Manual cache size must be greater than zero");
    }

    if (cacheSizeMode == CacheSizeMode.MANUAL) {
      this.translationCache = new TranslationCache(manualCacheSize);
      this.manualCacheSize = manualCacheSize;
    } else {
      this.translationCache = createCache(cacheSizeMode);
    }
    this.cacheSizeMode = cacheSizeMode;
    this.manualCacheSize = manualCacheSize;
  }

  /**
   * Creates a cache based on the specified mode.
   *
   * @param mode the cache size mode
   * @return a new TranslationCache instance
   * @since 0.2.1
   */
  private TranslationCache createCache(CacheSizeMode mode) {
    switch (mode) {
      case AUTOMATIC:
        return new TranslationCache(TranslationCache.calculateDefaultCacheSize());
      case MANUAL:
        return new TranslationCache(manualCacheSize);
      case DEFAULT:
      default:
        return new TranslationCache(1000);
    }
  }

  /**
   * Reconfigures the cache size dynamically.
   *
   * @param cacheSizeMode   the new cache size mode
   * @param manualCacheSize the new manual cache size (used when cacheSizeMode
   *                        is MANUAL)
   * @throws NullPointerException      if cacheSizeMode is null
   * @throws InvalidCacheSizeException if manualCacheSize is invalid when
   *                                   required
   * @since 0.2.1
   */
  public synchronized void reconfigureCache(
    CacheSizeMode cacheSizeMode,
    int manualCacheSize
  ) {
    if (cacheSizeMode == null) {
      throw new NullPointerException("cacheSizeMode must not be null");
    }

    if (cacheSizeMode == CacheSizeMode.MANUAL && manualCacheSize <= 0) {
      throw new InvalidCacheSizeException("Manual cache size must be greater than zero");
    }

    this.cacheSizeMode = cacheSizeMode;
    this.manualCacheSize = manualCacheSize;

    int newSize;
    switch (cacheSizeMode) {
      case AUTOMATIC:
        newSize = TranslationCache.calculateDefaultCacheSize();
        break;
      case MANUAL:
        newSize = manualCacheSize;
        break;
      case DEFAULT:
      default:
        newSize = 1000;
    }

    this.translationCache.setMaxSize(newSize);
  }

  /**
   * Reconfigures the cache size dynamically.
   *
   * @param cacheSizeMode the new cache size mode
   * @throws NullPointerException if cacheSizeMode is null
   * @since 0.2.1
   */
  public void reconfigureCache(CacheSizeMode cacheSizeMode) {
    reconfigureCache(cacheSizeMode, this.manualCacheSize);
  }

  /**
   * Gets statistics about the translation cache performance.
   *
   * @return a string with cache statistics
   * @since 0.2.1
   */
  public String getCacheStatistics() {
    return translationCache.getStatistics();
  }

  /**
   * Gets the current cache size mode.
   *
   * @return the current cache size mode
   * @since 0.2.1
   */
  public CacheSizeMode getCacheSizeMode() {
    return cacheSizeMode;
  }

  /**
   * Gets the manual cache size (used when mode is MANUAL).
   *
   * @return the manual cache size
   * @since 0.2.1
   */
  public int getManualCacheSize() {
    return manualCacheSize;
  }

  /**
   * Clears the translation cache when translations are updated.
   *
   * @since 0.2.1
   */
  public void updateTranslations(
    final Collection<LocaleTranslations> translations
  ) {
    if (translations == null) {
      throw new NullPointerException("translations must not be null");
    }

    this.translations.clear();
    if (null == this.defaultLocaleSource) {
      for (final LocaleTranslations localeTranslations : translations) {
        if (localeTranslations == null) {
          continue;
        }
        this.translations.put(
          localeTranslations.getLocaleSource(),
          localeTranslations
        );
      }
      return;
    }

    boolean defaultFound = false;
    for (final LocaleTranslations localeTranslations : translations) {
      if (localeTranslations == null) {
        continue;
      }

      final LocaleSource source = localeTranslations.getLocaleSource();
      if (source.equals(this.defaultLocaleSource)) {
        this.translations.put(source, localeTranslations);
        defaultFound = true;
        continue;
      }
      this.defaultLocaleTranslations = localeTranslations;
      this.defaultLocaleSource = source;
    }

    if (!defaultFound && this.defaultLocaleSource != null) {
      throw new LocaleNotSupportedException(
        "Default locale source not found in provided translations: " +
          this.defaultLocaleSource.getLocalization()
      );
    }

    // Clear cache when translations are updated
    this.translationCache.clear();
  }

  @Override
  public Collection<LocaleSource> getLoadedLocaleSources() {
    return Collections.unmodifiableCollection(this.translations.keySet());
  }

  @Override
  public Collection<LocaleTranslations> getLoadedLocaleTranslations() {
    return Collections.unmodifiableCollection(this.translations.values());
  }

  @Override
  public LocaleSource getDefaultLocaleSource() {
    return this.defaultLocaleSource;
  }

  @Override
  public LocaleTranslations getDefaultLocaleTranslations() {
    return this.defaultLocaleTranslations;
  }

  /**
   * Validates and returns the LocaleSource from the provider.
   *
   * @param localeSourceProvider the provider to check
   * @return the validated LocaleSource
   * @throws NullPointerException        if localeSourceProvider is null
   * @throws LocaleNotSupportedException if the locale source is not supported
   * @since 0.2.1
   */
  private LocaleSource checkLocaleSource(
    final LocaleSourceProvider localeSourceProvider
  ) {
    if (null == localeSourceProvider) {
      throw new NullPointerException(
        "The passed locale source provider must not be null."
      );
    }
    final LocaleSource localeSource = localeSourceProvider.getLocaleSource();
    if (null == localeSource) {
      throw new LocaleNotSupportedException(
        "No locale source found for the requested locale."
      );
    }
    return localeSource;
  }

  /**
   * Validates and returns the TranslationKey from the provider.
   *
   * @param translationKeyProvider the provider to check
   * @return the validated TranslationKey
   * @throws NullPointerException            if translationKeyProvider is null
   * @throws TranslationKeyNotFoundException if the key is not found
   * @since 0.2.1
   */
  private TranslationKey checkTranslationKey(
    final TranslationKeyProvider translationKeyProvider
  ) {
    if (null == translationKeyProvider) {
      throw new NullPointerException(
        "The passed translation key provider must not be null."
      );
    }
    final TranslationKey translationKey = translationKeyProvider.getTranslationKey();
    if (null == translationKey) {
      throw new NullPointerException(
        "The passed translation key provider cannot provide a key that is null."
      );
    }
    return translationKey;
  }

  @Override
  public void updateDefaultLocaleSource(
    final LocaleSourceProvider localeSourceProvider
  ) {
    if (localeSourceProvider == null) {
      throw new NullPointerException("localeSourceProvider must not be null");
    }

    LocaleSource localeSource = localeSourceProvider.getLocaleSource();
    if (localeSource == null) {
      throw new LocaleNotSupportedException("Locale source provider returned null locale source");
    }

    this.defaultLocaleSource = localeSource;
  }

  @Override
  public void updateDefaultLocaleTranslations() {
    if (null == this.defaultLocaleSource) {
      throw new LocaleNotSupportedException(
        "No default locale source configured. Call updateDefaultLocaleSource first."
      );
    }
    final LocaleTranslations translations =
      this.translations.get(this.defaultLocaleSource);
    if (null == translations) {
      throw new LocaleNotSupportedException(
        "No translations found for default locale: " + this.defaultLocaleSource.getLocalization()
      );
    }
    this.defaultLocaleTranslations = translations;
    this.translations.remove(this.defaultLocaleSource);
  }

  @Override
  public LocaleTranslations findLocaleTranslationsOrNull(
    final LocaleSourceProvider localeSourceProvider
  ) {
    return this.translations.get(
      this.checkLocaleSource(localeSourceProvider)
    );
  }

  @Override
  public TranslationMessage findMessageOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    final LocaleSource localeSource = checkLocaleSource(localeSourceProvider);
    final TranslationKey translationKey = checkTranslationKey(translationKeyProvider);
    // Try to get from cache first
    TranslationMessage message = translationCache.getMessage(localeSource, translationKey);
    if (message != null) {
      return message;
    }
    // If not in cache, find it the regular way
    final LocaleTranslations localeTranslations = this.translations.get(localeSource);
    if (localeTranslations == null) {
      return null;
    }
    message = localeTranslations.findMessageOrNull(translationKeyProvider);
    if (message != null) {
      // Add to cache for future use
      translationCache.putMessage(localeSource, translationKey, message);
    }
    return message;
  }

  @Override
  public TranslationText findTextOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    final LocaleSource localeSource = checkLocaleSource(localeSourceProvider);
    final TranslationKey translationKey = checkTranslationKey(translationKeyProvider);
    // Try to get from cache first
    TranslationText text = translationCache.getText(localeSource, translationKey);
    if (text != null) {
      return text;
    }
    // If not in cache, find it the regular way
    final LocaleTranslations localeTranslations = this.translations.get(localeSource);
    if (localeTranslations == null) {
      return null;
    }
    text = localeTranslations.findTextOrNull(translationKeyProvider);
    if (text != null) {
      // Add to cache for future use
      translationCache.putText(localeSource, translationKey, text);
    }
    return text;
  }

  @Override
  public LocaleTranslations findLocaleTranslationsOrDefault(
    final LocaleSourceProvider localeSourceProvider
  ) {
    final LocaleTranslations localeTranslations =
      this.findLocaleTranslationsOrNull(localeSourceProvider);
    if (null != localeTranslations) {
      return localeTranslations;
    }
    if (null == this.defaultLocaleTranslations) {
      if (null == this.defaultLocaleSource) {
        throw new LocaleNotSupportedException(
          "No default locale source configured and no fallback available."
        );
      }
      this.updateDefaultLocaleTranslations();
    }
    return this.defaultLocaleTranslations;
  }

  @Override
  public TranslationMessage findMessageOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    LocaleTranslations localeTranslations = this.findLocaleTranslationsOrDefault(localeSourceProvider);
    TranslationMessage message = localeTranslations.findMessageOrNull(translationKeyProvider);
    if (message == null) {
      throw new TranslationKeyNotFoundException(
        "Translation message not found for key: " + translationKeyProvider.getTranslationKey()
      );
    }
    return message;
  }

  @Override
  public TranslationText findTextOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    LocaleTranslations localeTranslations = this.findLocaleTranslationsOrDefault(localeSourceProvider);
    TranslationText text = localeTranslations.findTextOrNull(translationKeyProvider);
    if (text == null) {
      throw new TranslationKeyNotFoundException(
        "Translation text not found for key: " + translationKeyProvider.getTranslationKey()
      );
    }
    return text;
  }

  @Override
  public int getNumberOfLocales() {
    int count = this.translations.size();
    if (null != this.defaultLocaleSource) {
      ++count;
    }
    return count;
  }

  @Override
  public int getNumberOfMessages() {
    if (null != this.defaultLocaleTranslations) {
      return this.defaultLocaleTranslations.getNumberOfMessages();
    }
    for (final LocaleTranslations translations : this.translations.values()) {
      return translations.getNumberOfMessages();
    }
    return 0;
  }

  @Override
  public int getNumberOfTexts() {
    if (null != this.defaultLocaleTranslations) {
      return this.defaultLocaleTranslations.getNumberOfTexts();
    }
    for (final LocaleTranslations translations : this.translations.values()) {
      return translations.getNumberOfTexts();
    }
    return 0;
  }

  @Override
  public void clear() {
    this.translations.clear();
    this.defaultLocaleTranslations = null;
    this.defaultLocaleSource = null;
    this.translationCache.clear();
  }
}