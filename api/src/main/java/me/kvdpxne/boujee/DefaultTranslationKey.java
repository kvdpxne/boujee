package me.kvdpxne.boujee;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a unique translation key used for localization.
 *
 * @since 0.1.0
 */
public class DefaultTranslationKey
  implements TranslationKey {

  /**
   * @since 0.1.0
   */
  private static final ConcurrentMap<String, TranslationKey> KEYS =
    new ConcurrentHashMap<>(64);

  /**
   * @since 0.1.0
   */
  private static final AtomicInteger COUNTER =
    new AtomicInteger(0);

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -6054827629563125169L;

  private final int ordinal;

  /**
   * Constructs a new {@code TranslationKey}.
   *
   * @param ordinal the unique ordinal number of the translation key.
   * @since 0.1.0
   */
  public DefaultTranslationKey(final int ordinal) {
    this.ordinal = ordinal;
  }

  /**
   * @since 0.1.0
   */
  public DefaultTranslationKey() {
    this(COUNTER.getAndIncrement());
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided
   * content.
   *
   * @param content       the content representing the translation key.
   * @param denyCreations if {@code true}, creation of a new key is denied if it
   *                      does not exist.
   * @return the existing or newly created {@code TranslationKey}.
   * @throws NullPointerException     if the content is {@code null}.
   * @throws IllegalArgumentException if the content is empty.
   * @throws IllegalStateException    if the key does not exist and creation is
   *                                  denied.
   * @since 0.1.0
   */
  static TranslationKey of(
    final String content,
    final boolean denyCreations
  ) {
    if (null == content) {
      throw new NullPointerException("The passed content must not be null.");
    }

    if (content.isEmpty()) {
      throw new IllegalArgumentException("The passed content must not be empty.");
    }

    final String normalizedKey = content.trim().toUpperCase(Locale.ENGLISH);
    if (denyCreations) {
      final TranslationKey key = KEYS.get(normalizedKey);
      if (null == key) {
        throw new IllegalStateException("The \"" + normalizedKey + "\" translation key does not exist.");
      }
      return key;
    }

    return KEYS.computeIfAbsent(
      normalizedKey,
      (String key) -> new DefaultTranslationKey()
    );
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided
   * content. Creation is allowed if the key does not exist.
   *
   * @param content the content representing the translation key.
   * @return the existing or newly created {@code TranslationKey}.
   * @throws NullPointerException     if the content is {@code null}.
   * @throws IllegalArgumentException if the content is empty.
   * @since 0.1.0
   */
  static TranslationKey of(
    final String content
  ) {
    return of(content, false);
  }

  /**
   * Returns the ordinal number of this translation key.
   *
   * @return the ordinal number of the translation key.
   * @since 0.1.0
   */
  public int getOrdinalNumber() {
    return this.ordinal;
  }

  /**
   * Returns this translation key.
   *
   * @return this {@code TranslationKey}.
   * @since 0.1.0
   */
  @Override
  public TranslationKey getTranslationKey() {
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof DefaultTranslationKey)) {
      return false;
    }

    final DefaultTranslationKey that = (DefaultTranslationKey) o;
    return this.ordinal == that.ordinal;
  }

  @Override
  public int hashCode() {
    return this.ordinal;
  }

  @Override
  public String toString() {
    return String.valueOf(this.ordinal);
  }
}
