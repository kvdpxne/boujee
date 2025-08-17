package me.kvdpxne.boujee.content.text;

import me.kvdpxne.boujee.content.Translation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents a translatable text content as a single segment.
 *
 * <p>This interface provides a specific implementation of {@link Translation} where the content is represented as
 * a character array ({@code char[]}). Unlike {@link me.kvdpxne.boujee.content.message.TranslationMessage} which
 * handles multi-segment content, this interface is designed for single-line or simple text translations that
 * don't require segmentation into multiple parts.
 *
 * <p>The implementation ensures immutability through several mechanisms:
 * <ul>
 *   <li>The {@code @Unmodifiable} annotation indicates the object itself cannot be modified</li>
 *   <li>The {@code @UnmodifiableView} annotation on {@code getContent()} indicates the returned array is a view that
 *       should not be modified by callers</li>
 *   <li>Implementations should return defensive copies of internal data structures</li>
 * </ul>
 *
 * <p>This structure is particularly useful for:
 * <ul>
 *   <li>User interface labels, buttons, and captions</li>
 *   <li>Single-line notifications and messages</li>
 *   <li>Error messages and status indicators</li>
 *   <li>Situations where character-level operations are required without the overhead of string objects</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * TranslationText welcomeText = ...; // Obtained from translation service
 *
 * // Process the text content
 * char[] charContent = welcomeText.getContent();
 * for (int i = 0; i < charContent.length; i++) {
 *     // Perform character-level operations
 * }
 *
 * // Or use the string representation for simpler operations
 * String textString = welcomeText.getContentAsString();
 * System.out.println("Welcome message: " + textString);
 * }</pre>
 *
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>Using {@code char[]} directly is more efficient for character-level operations than {@code String}
 *       as it avoids object overhead for individual characters</li>
 *   <li>The {@code getContentAsString()} method should be used when string operations are needed, but may involve
 *       conversion overhead if called repeatedly</li>
 *   <li>For frequent character operations, work directly with the {@code char[]} representation</li>
 *   <li>The immutability design pattern ensures thread safety but may require additional memory for replacement operations</li>
 * </ul>
 *
 * @see Translation
 * @see me.kvdpxne.boujee.content.message.TranslationMessage
 * @since 0.1.0
 */
@Unmodifiable
public interface TranslationText
  extends Translation<char[]> {

  /**
   * Retrieves the text content of this translation as a character array.
   *
   * <p>The returned array contains all characters of the translatable text in sequence. This representation
   * is particularly useful for:
   * <ul>
   *   <li>Character-level processing and manipulation</li>
   *   <li>Efficient memory usage compared to string operations</li>
   *   <li>Low-level text processing where string object overhead is undesirable</li>
   * </ul>
   *
   * <p>This method returns an unmodifiable view of the content as indicated by the {@code @UnmodifiableView} annotation.
   * While the array itself may be modified in some implementations, consumers should treat it as read-only to maintain
   * the immutability contract of the translation framework.
   *
   * <p><strong>Note:</strong> Unlike {@link #getContentAsString()}, this method provides direct access to the character
   * data without creating intermediate String objects, making it more efficient for character-level processing.
   *
   * @return An unmodifiable view of the character array containing the text content
   * @since 0.1.0
   */
  @UnmodifiableView
  @Override
  char @NotNull [] getContent();

  /**
   * Retrieves the text content as a string representation.
   *
   * <p>This is a convenience method that converts the internal {@code char[]} representation to a standard Java String.
   *
   * <p><strong>Important:</strong> The returned string is guaranteed to be unmodifiable as all Java strings are immutable.
   *
   * <p>This method may involve conversion overhead if the implementation stores data as {@code char[]},
   * so it should be used judiciously in performance-critical sections. However, for most UI and display purposes,
   * this method provides the most convenient way to access the translated content.
   *
   * @return An unmodifiable string representing the text content
   * @since 0.1.0
   */
  @NotNull
  @UnmodifiableView
  String getContentAsString();
}
