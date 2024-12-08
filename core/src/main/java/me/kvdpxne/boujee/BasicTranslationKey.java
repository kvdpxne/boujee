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
public final class BasicTranslationKey
  implements TranslationKey {

  /**
   * @since 0.1.0
   */
  protected static final ConcurrentMap<String, TranslationKey> TRANSLATION_KEYS = new ConcurrentHashMap<>();

  /**
   * @since 0.1.0
   */
  protected static final AtomicInteger COUNTER = new AtomicInteger();

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -6054827629563125169L;

  private final String name;
  private final int ordinal;

  /**
   * Constructs a new {@code TranslationKey}.
   *
   * @param name    the name of the translation key.
   * @param ordinal the unique ordinal number of the translation key.
   * @since 0.1.0
   */
  public BasicTranslationKey(final String name, final int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
  }

  /**
   * @param name
   *
   * @since 0.1.0
   */
  public BasicTranslationKey(final String name) {
    this(name, COUNTER.getAndIncrement());
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided content.
   *
   * @param content       the content representing the translation key.
   * @param denyCreations if {@code true}, creation of a new key is denied if it does not exist.
   * @return the existing or newly created {@code TranslationKey}.
   * @throws NullPointerException     if the content is {@code null}.
   * @throws IllegalArgumentException if the content is empty.
   * @throws IllegalStateException    if the key does not exist and creation is denied.
   * @since 0.1.0
   */
  public static TranslationKey of(
    final String content,
    final boolean denyCreations
  ) {
    if (null == content) {
      throw new NullPointerException("The passed content must not be null.");
    }

    if (content.isEmpty()) {
      throw new IllegalArgumentException("The passed content must not be empty.");
    }

    final String normalizedKey = content.trim()
      .toUpperCase(Locale.ENGLISH)
      .intern();

    TranslationKey presentValue = TRANSLATION_KEYS.get(normalizedKey);
    if (null != presentValue) {
      return presentValue;
    }

    if (denyCreations) {
      throw new IllegalStateException("The \"" + normalizedKey + "\" translation key does not exist.");
    }

    final TranslationKey newValue = new BasicTranslationKey(normalizedKey, COUNTER.getAndIncrement());
    presentValue = TRANSLATION_KEYS.put(normalizedKey, newValue);

    if (null != presentValue) {
      return presentValue;
    }

    return newValue;
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided content. Creation is allowed if the key does
   * not exist.
   *
   * @param content the content representing the translation key.
   * @return the existing or newly created {@code TranslationKey}.
   * @throws NullPointerException     if the content is {@code null}.
   * @throws IllegalArgumentException if the content is empty.
   * @since 0.1.0
   */
  public static TranslationKey of(
    final String content
  ) {
    return of(content, false);
  }

  /**
   * Returns the name of this translation key.
   *
   * @return the name of the translation key.
   * @since 0.1.0
   */
  public String getName() {
    return this.name;
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

    if (!(o instanceof BasicTranslationKey)) {
      return false;
    }

    final BasicTranslationKey that = (BasicTranslationKey) o;
    return this.ordinal == that.ordinal;
  }

  @Override
  public int hashCode() {
    return this.ordinal;
  }
}
