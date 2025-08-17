package me.kvdpxne.boujee.locale;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import me.kvdpxne.boujee.DefaultTranslationKey;
import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.exceptions.TranslationKeyNotFoundException;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;

/**
 * Manages translations for a specific locale.
 * <p>
 * This implementation provides efficient access to translation messages and texts
 * with built-in caching for frequently accessed keys, optimized for high-concurrency
 * multiplayer environments.
 *
 * @since 0.1.0
 */
public class BasicLocaleTranslations implements LocaleTranslations {

  /**
   * The locale source associated with these translations.
   */
  private final LocaleSource localeSource;

  /**
   * Map of translation keys to messages.
   */
  private final ConcurrentMap<TranslationKey, TranslationMessage> messages;

  /**
   * Map of translation keys to texts.
   */
  private final ConcurrentMap<TranslationKey, TranslationText> texts;

  /**
   * Cache for frequently accessed messages to improve performance.
   */
  private final TranslationCache<TranslationMessage> messageCache;

  /**
   * Cache for frequently accessed texts to improve performance.
   */
  private final TranslationCache<TranslationText> textCache;

  /**
   * Unmodifiable view of messages collection (cached for performance).
   */
  private Collection<TranslationMessage> unmodifiableMessages;

  /**
   * Unmodifiable view of texts collection (cached for performance).
   */
  private Collection<TranslationText> unmodifiableTexts;

  /**
   * Constructs a new BasicLocaleTranslations instance with custom cache size.
   *
   * @param localeSource the locale source
   * @param messages     the map of translation keys to messages
   * @param texts        the map of translation keys to texts
   * @param cacheSize    the maximum size of the translation cache
   * @since 0.1.0
   */
  public BasicLocaleTranslations(
    final LocaleSource localeSource,
    final Map<TranslationKey, TranslationMessage> messages,
    final Map<TranslationKey, TranslationText> texts,
    final int cacheSize
  ) {
    if (localeSource == null) {
      throw new NullPointerException("Locale source must not be null");
    }

    this.localeSource = localeSource;
    this.messages = new ConcurrentHashMap<>(messages);
    this.texts = new ConcurrentHashMap<>(texts);

    // Initialize caches with specified size
    this.messageCache = new TranslationCache<>(cacheSize);
    this.textCache = new TranslationCache<>(cacheSize);

    // Cache unmodifiable collections for performance
    this.unmodifiableMessages = Collections.unmodifiableCollection(this.messages.values());
    this.unmodifiableTexts = Collections.unmodifiableCollection(this.texts.values());
  }

  /**
   * Constructs a new BasicLocaleTranslations instance.
   *
   * @param localeSource the locale source
   * @param messages     the map of translation keys to messages
   * @param texts        the map of translation keys to texts
   * @since 0.1.0
   */
  public BasicLocaleTranslations(
    final LocaleSource localeSource,
    final Map<TranslationKey, TranslationMessage> messages,
    final Map<TranslationKey, TranslationText> texts
  ) {
    this(localeSource, messages, texts, 1000);
  }

  /**
   * Constructs a new empty BasicLocaleTranslations instance with custom cache size.
   *
   * @param localeSource the locale source
   * @param cacheSize    the maximum size of the translation cache
   * @since 0.1.0
   */
  public BasicLocaleTranslations(final LocaleSource localeSource, final int cacheSize) {
    if (null == localeSource) {
      throw new NullPointerException("Locale source must not be null");
    }

    this.localeSource = localeSource;
    this.messages = new ConcurrentHashMap<>();
    this.texts = new ConcurrentHashMap<>();

    // Initialize caches with specified size
    this.messageCache = new TranslationCache<>(cacheSize);
    this.textCache = new TranslationCache<>(cacheSize);

    // Initialize empty collections
    this.unmodifiableMessages = Collections.emptyList();
    this.unmodifiableTexts = Collections.emptyList();
  }

  /**
   * Constructs a new empty BasicLocaleTranslations instance.
   *
   * @param localeSource the locale source
   * @since 0.1.0
   */
  public BasicLocaleTranslations(final LocaleSource localeSource) {
    this(localeSource, 1000);
  }

  @Override
  public LocaleSource getLocaleSource() {
    return this.localeSource;
  }

  @Override
  public Collection<TranslationMessage> getMessages() {
    return this.unmodifiableMessages;
  }

  @Override
  public Collection<TranslationText> getTexts() {
    return this.unmodifiableTexts;
  }

  @Override
  public TranslationMessage findMessageOrNull(
    final TranslationKeyProvider keyProvider
  ) {
    if (null == keyProvider) {
      throw new NullPointerException("Translation key provider must not be null");
    }

    final TranslationKey key = keyProvider.getTranslationKey();
    if (null == key) {
      throw new NullPointerException("Translation key must not be null");
    }

    if (!DefaultTranslationKey.exists(key)) {
      throw new TranslationKeyNotFoundException("Translation key not found: " + key);
    }

    // Try cache first
    TranslationMessage message = this.messageCache.get(key);
    if (message != null) {
      return message;
    }

    // Find in main storage
    message = this.messages.get(key);
    if (message != null) {
      // Add to cache for future use
      this.messageCache.put(key, message);
    }

    return message;
  }

  @Override
  public TranslationText findTextOrNull(
    final TranslationKeyProvider keyProvider
  ) {
    if (keyProvider == null) {
      throw new NullPointerException("Translation key provider must not be null");
    }

    final TranslationKey key = keyProvider.getTranslationKey();
    if (key == null) {
      throw new NullPointerException("Translation key must not be null");
    }

    // Try cache first
    TranslationText text = textCache.get(key);
    if (text != null) {
      return text;
    }

    // Find in main storage
    text = texts.get(key);
    if (text != null) {
      // Add to cache for future use
      textCache.put(key, text);
    }

    return text;
  }

  @Override
  public int getNumberOfMessages() {
    return this.messages.size();
  }

  @Override
  public int getNumberOfTexts() {
    return this.texts.size();
  }

  @Override
  public void clear() {
    this.messages.clear();
    this.texts.clear();
    this.messageCache.clear();
    this.textCache.clear();

    // Update cached collections
    this.unmodifiableMessages = Collections.emptyList();
    this.unmodifiableTexts = Collections.emptyList();

    if (this.localeSource instanceof BasicLocaleSource) {
      ((BasicLocaleSource) this.localeSource).invalidateCache();
    }
  }

  /**
   * Simple LRU cache implementation for translations.
   *
   * @param <T> the type of translation to cache
   */
  private static class TranslationCache<T> {
    private final int maxSize;
    private final ConcurrentMap<TranslationKey, T> cache;

    public TranslationCache(int maxSize) {
      this.maxSize = maxSize;
      this.cache = new ConcurrentHashMap<>(maxSize);
    }

    public T get(TranslationKey key) {
      return cache.get(key);
    }

    public void put(TranslationKey key, T value) {
      // Maintain cache size
      if (cache.size() >= maxSize) {
        // Simple eviction: remove 10% of entries
        int toRemove = Math.max(1, maxSize / 10);
        cache.keySet().stream()
          .limit(toRemove)
          .forEach(cache::remove);
      }
      cache.put(key, value);
    }

    public void clear() {
      cache.clear();
    }
  }
}