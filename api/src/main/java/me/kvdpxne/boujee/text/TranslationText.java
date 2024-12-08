package me.kvdpxne.boujee.text;

import me.kvdpxne.boujee.Translation;

/**
 * Represents a translatable text content.
 * <p>
 * This interface provides a specific implementation of {@link Translation}
 * where the content is always a {@link String}.
 * <p>
 * This is useful for single-line or simple text translations that do not
 * require segmentation into multiple parts.
 *
 * @see Translation
 * @since 0.1.0
 */
public interface TranslationText
  extends Translation<String> {

  /**
   * Retrieves the text content of this translation.
   * <p>
   * The returned {@link String} represents the full translatable text, which is
   * typically a single line or paragraph of text.
   *
   * @return The text content of this translation.
   * @since 0.1.0
   */
  @Override
  String getContent();
}
