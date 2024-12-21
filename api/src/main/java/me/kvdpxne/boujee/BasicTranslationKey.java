package me.kvdpxne.boujee;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a unique translation key used for localization.
 *
 * @since 0.1.0
 */
final class BasicTranslationKey
  implements TranslationKey {

  /**
   * @since 0.1.0
   */
  static final ConcurrentMap<String, TranslationKey> KEYS = new ConcurrentHashMap<>();

  /**
   * @since 0.1.0
   */
  static final AtomicInteger COUNTER = new AtomicInteger();

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
  BasicTranslationKey(final String name, final int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
  }

  /**
   * @param name
   *
   * @since 0.1.0
   */
  BasicTranslationKey(final String name) {
    this(name, COUNTER.getAndIncrement());
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
