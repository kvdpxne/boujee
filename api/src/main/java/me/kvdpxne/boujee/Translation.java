package me.kvdpxne.boujee;

import java.io.Serializable;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.text.TranslationText;

/**
 * Represents a translatable entity whose content can be retrieved
 * in a serialized form. This interface is designed to provide flexibility
 * for different types of translatable content.
 *
 * <p>For example:
 * <ul>
 *   <li>{@link TranslationText}: Returns a {@link String}, as the content represents a single line of text.</li>
 *   <li>{@link TranslationMessage}: Returns a {@code String[]}, as the content may represent multiple lines or segments.</li>
 * </ul>
 *
 * @param <T> The type of the content, which must implement {@link Serializable}.
 * @since 0.1.0
 * @see TranslationText
 * @see TranslationMessage
 */
public interface Translation<T extends Serializable>
  extends Serializable {

  /**
   * Retrieves the translatable content associated with this instance.
   *
   * <p>The type of the content depends on the specific implementation:
   * <ul>
   *   <li>{@link TranslationText}: Returns a {@link String}.</li>
   *   <li>{@link TranslationMessage}: Returns a {@code String[]}, as messages may consist of multiple lines or segments.</li>
   * </ul>
   *
   * @return The content of this translation.
   * @since 0.1.0
   */
  T getContent();
}
