package me.kvdpxne.boujee;

import java.io.Serializable;
import java.util.Locale;

/**
 * @since 0.1.0
 */
public interface TranslationKey
  extends TranslationKeyProvider, Serializable {

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

    final String normalizedKey = content.trim()
      .toUpperCase(Locale.ENGLISH)
      .intern();

    TranslationKey presentValue = BasicTranslationKey.KEYS.get(normalizedKey);
    if (null != presentValue) {
      return presentValue;
    }

    if (denyCreations) {
      throw new IllegalStateException("The \"" + normalizedKey + "\" translation key does not exist.");
    }

    final TranslationKey newValue = new BasicTranslationKey(normalizedKey);
    presentValue = BasicTranslationKey.KEYS.put(normalizedKey, newValue);

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
  static TranslationKey of(
    final String content
  ) {
    return of(content, false);
  }

  /**
   * @since 0.1.0
   */
  String getName();

  /**
   * @since 0.1.0
   */
  int getOrdinalNumber();
}
