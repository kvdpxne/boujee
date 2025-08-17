package me.kvdpxne.boujee.content.message;

import me.kvdpxne.boujee.content.Replaceable;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a translatable message with placeholder replacement capabilities.
 *
 * <p>This interface extends {@link TranslationMessage} to provide functionality for replacing placeholders within
 * multi-segment messages. Implementations of this interface allow for dynamic content substitution while maintaining
 * immutability - each replacement operation returns a new instance rather than modifying the original.
 *
 * <p>Placeholder replacement is particularly useful for:
 * <ul>
 *   <li>Personalizing messages with user-specific data</li>
 *   <li>Inserting dynamic values into translated content</li>
 *   <li>Building complex messages from template structures</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * ReplacableTranslationMessage welcomeMessage = ...; // Obtained from translation service
 *
 * // Replace placeholders in the message
 * ReplacableTranslationMessage personalizedMessage = welcomeMessage
 *     .replace("{username}".toCharArray(), "JohnDoe".toCharArray())
 *     .replace("{count}".toCharArray(), "5".toCharArray());
 * }</pre>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>All implementations must honor the {@code @Unmodifiable} contract</li>
 *   <li>Replacement operations must return new instances rather than modifying the current object</li>
 *   <li>The generic type parameter ensures fluent method chaining for multiple replacements</li>
 * </ul>
 *
 * @see TranslationMessage
 * @see Replaceable
 * @since 0.1.0
 */
@Unmodifiable
public interface ReplacableTranslationMessage
  extends TranslationMessage, Replaceable<ReplacableTranslationMessage> {
}
