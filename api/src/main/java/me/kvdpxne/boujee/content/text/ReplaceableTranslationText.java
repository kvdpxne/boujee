package me.kvdpxne.boujee.content.text;

import me.kvdpxne.boujee.content.Replaceable;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a translatable text with placeholder replacement capabilities.
 *
 * <p>This interface extends {@link TranslationText} to provide functionality for replacing placeholders within
 * single-segment text translations. Implementations of this interface allow for dynamic content substitution
 * while maintaining immutability - each replacement operation returns a new instance rather than modifying the original.
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
 * ReplaceableTranslationText greeting = ...; // Obtained from translation service
 *
 * // Replace placeholders in the text
 * ReplaceableTranslationText personalizedGreeting = greeting
 *     .replace("{name}".toCharArray(), "John".toCharArray())
 *     .replace("{time}".toCharArray(), "morning".toCharArray());
 *
 * System.out.println(personalizedGreeting.getContentAsString());
 * // Output might be: "Good morning, John!"
 * }</pre>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>All implementations must honor the {@code @Unmodifiable} contract</li>
 *   <li>Replacement operations must return new instances rather than modifying the current object</li>
 *   <li>The generic type parameter ensures fluent method chaining for multiple replacements</li>
 *   <li>Implementations should efficiently handle replacement operations without unnecessary memory allocations</li>
 * </ul>
 *
 * @see TranslationText
 * @see Replaceable
 * @since 0.1.0
 */
@Unmodifiable
public interface ReplaceableTranslationText
  extends TranslationText, Replaceable<ReplaceableTranslationText> {
}
