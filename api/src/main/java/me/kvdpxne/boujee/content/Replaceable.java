package me.kvdpxne.boujee.content;

import java.util.Map;
import me.kvdpxne.boujee.content.message.ReplacableTranslationMessage;
import me.kvdpxne.boujee.content.text.ReplaceableTranslationText;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity that allows dynamic replacement of placeholders in its content.
 *
 * <p>This interface enables modification of specific parts of translatable content through placeholder replacement
 * while maintaining the immutability contract of the translation framework. Implementations create new instances
 * with the replaced content rather than modifying the original object, allowing for safe chaining of replacement
 * operations without affecting the base translation.
 *
 * <p>Placeholder replacement is a key feature for:
 * <ul>
 *   <li>Personalizing translated content with user-specific data</li>
 *   <li>Inserting dynamic values into static translation templates</li>
 *   <li>Building complex messages from reusable translation components</li>
 * </ul>
 *
 * <p><strong>Usage Pattern:</strong> This interface is typically implemented alongside {@link Translation} in concrete
 * classes that represent translatable content with placeholder support, such as:
 * <ul>
 *   <li>{@link ReplaceableTranslationText} - For single-segment text with placeholders</li>
 *   <li>{@link ReplacableTranslationMessage} - For multi-segment messages with placeholders</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Create a personalized welcome message
 * ReplaceableTranslationText greeting = ...; // Obtained from translation service
 *
 * // Chain multiple replacements fluently
 * ReplaceableTranslationText personalizedGreeting = greeting
 *     .replace("{username}".toCharArray(), "JohnDoe".toCharArray())
 *     .replace("{count}".toCharArray(), "5".toCharArray());
 *
 * System.out.println(personalizedGreeting.getContentAsString());
 * }</pre>
 *
 * <p><strong>Implementation Requirements:</strong>
 * <ul>
 *   <li>All implementations must honor immutability - replacement operations must return new instances</li>
 *   <li>The generic type parameter enables fluent method chaining for multiple replacements</li>
 *   <li>Placeholder matching should be exact (character-by-character) without pattern matching</li>
 *   <li>Implementations should handle overlapping or nested placeholders according to framework conventions</li>
 * </ul>
 *
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>Using character arrays ({@code char[]}) for fields and values avoids string object overhead</li>
 *   <li>Multiple replacements via the map-based method may be more efficient than sequential single replacements</li>
 *   <li>Implementations should optimize for common replacement patterns to minimize memory allocations</li>
 * </ul>
 *
 * @param <T> The concrete type of the implementing class, enabling fluent method chaining
 * @see ReplaceableTranslationText
 * @see ReplacableTranslationMessage
 * @see Translation
 * @since 0.1.0
 */
public interface Replaceable<T extends Replaceable<T>> {

  /**
   * Replaces a single placeholder in the content with the specified value.
   *
   * <p>This method creates a new instance containing the original content with all occurrences of the specified
   * placeholder replaced by the provided value. The original instance remains unchanged, preserving the immutability
   * contract of the translation framework.
   *
   * <p>Placeholder matching is performed as a direct character sequence comparison without pattern matching
   * or case conversion. The replacement is exact and case-sensitive.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Replace "{username}" with "JohnDoe"
   * char[] field = "{username}".toCharArray();
   * char[] value = "JohnDoe".toCharArray();
   * ReplaceableTranslationText result = translation.replace(field, value);
   * }</pre>
   *
   * @param field The placeholder sequence to be replaced (e.g., "{name}".toCharArray())
   * @param value The replacement sequence to substitute for the placeholder
   * @return A new instance of type T with the specified replacement applied
   * @throws NullPointerException if either field or value is null
   * @since 0.1.0
   */
  @NotNull
  T replace(
    char @NotNull [] field,
    char @NotNull [] value
  );

  /**
   * Replaces multiple placeholders in the content with their corresponding values in a single operation.
   *
   * <p>This method creates a new instance containing the original content with all specified replacements applied.
   * The replacements are processed in an implementation-defined order, which may affect results when placeholders
   * overlap or are nested. The original instance remains unchanged.
   *
   * <p>Using this method for multiple replacements may be more efficient than chaining multiple single replacements,
   * as implementations can optimize the replacement process.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * Map<char[], char[]> replacements = new HashMap<>();
   * replacements.put("{username}".toCharArray(), "JohnDoe".toCharArray());
   * replacements.put("{count}".toCharArray(), "5".toCharArray());
   *
   * ReplaceableTranslationText result = translation.replace(replacements);
   * }</pre>
   *
   * @param values A map where each key is a placeholder sequence and the corresponding value is the replacement sequence
   * @return A new instance of type T with all specified replacements applied
   * @throws NullPointerException if values or any of its entries is null
   * @since 0.1.0
   */
  @NotNull
  T replace(
    @NotNull Map<char @NotNull [], char @NotNull []> values
  );
}
