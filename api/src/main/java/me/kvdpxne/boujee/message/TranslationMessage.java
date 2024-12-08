package me.kvdpxne.boujee.message;

import me.kvdpxne.boujee.Translation;

/**
 * Represents a translatable message that may consist of multiple lines or
 * segments.
 * <p>
 * This interface provides a specific implementation of {@link Translation}
 * where the content is represented as a {@code String[]} array.
 * <p>
 * The implementation ensures immutability by returning a copy of the original
 * array in the {@link #getContent()} method. This guarantees that the internal
 * state of the object cannot be modified by external code.
 *
 * @see Translation
 * @since 0.1.0
 */
public interface TranslationMessage
  extends Translation<String[]> {

  /**
   * Retrieves the message content of this translation.
   * <p>
   * The returned {@code String[]} is a copy of the original array. Each array
   * element represents a separate line or segment of the message, ensuring the
   * immutability of this object.
   *
   * @return A copy of the array containing the content of this translation.
   * @since 0.1.0
   */
  @Override
  String[] getContent();
}
