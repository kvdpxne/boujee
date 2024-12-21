package me.kvdpxne.boujee.replace;

import java.util.Map;
import me.kvdpxne.boujee.message.ReplacableTranslationMessage;
import me.kvdpxne.boujee.text.ReplaceableTranslationText;

/**
 * Represents an entity that allows dynamic replacement of placeholders in its
 * content. This interface is designed to work with objects like
 * {@link ReplaceableTranslationText} and {@link ReplacableTranslationMessage},
 * enabling modification of specific parts of their content without altering the
 * original object.
 * <p>
 * The implementation ensures immutability by creating a new instance with the
 * replaced content instead of modifying the original instance. This allows for
 * multiple replacements on the same base object without affecting its original
 * state.
 *
 * @param <T> The type of the replaceable object itself, used for fluent method
 *            chaining.
 * @see ReplaceableTranslationText
 * @see ReplacableTranslationMessage
 * @since 0.1.0
 */
public interface Replaceable<T extends Replaceable<T>> {

  /**
   * Replaces a single placeholder in the content with the specified value.
   * <p>
   * The replacement is applied to a copy of the original content, ensuring the
   * original object remains unchanged. The placeholder to be replaced is
   * identified by the specified {@code field} (e.g., "{name}") and substituted
   * with the provided {@code value}.
   *
   * @param field The placeholder to be replaced.
   * @param value The value to replace the placeholder with.
   * @return A new instance of the object with the specified replacement
   * applied.
   * @since 0.1.0
   */
  T replace(final char[] field, final char[] value);

  /**
   * Replaces multiple placeholders in the content with their corresponding
   * values.
   * <p>
   * The replacements are applied to a copy of the original content, ensuring
   * the original object remains unchanged. Each entry in the {@code values} map
   * represents a placeholder and its associated replacement value.
   *
   * @param values A map where keys represent placeholders (e.g., "{name}") and
   *               values represent the replacements.
   * @return A new instance of the object with the specified replacements
   * applied.
   * @since 0.1.0
   */
  T replace(final Map<char[], char[]> values);
}
