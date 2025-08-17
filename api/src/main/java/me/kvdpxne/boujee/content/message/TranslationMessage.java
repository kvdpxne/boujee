package me.kvdpxne.boujee.content.message;

import me.kvdpxne.boujee.content.Translation;
import me.kvdpxne.boujee.content.text.TranslationText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents a translatable message that may consist of multiple lines or segments.
 *
 * <p>This interface provides a specific implementation of {@link Translation} where the content is represented as
 * a two-dimensional character array ({@code char[][]}). Each top-level array element represents a separate line
 * or segment of the message, while the inner array contains the characters of that segment.
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
 *   <li>Multi-line user interface messages</li>
 *   <li>Formatted content where different segments may have different styling</li>
 *   <li>Messages that need to be processed line-by-line</li>
 *   <li>Situations where character-level operations are required</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * TranslationMessage message = ...; // Obtained from translation service
 *
 * // Process each line of the message
 * for (char[] line : message.getContent()) {
 *     String lineString = new String(line);
 *     System.out.println("Processed line: " + lineString);
 * }
 *
 * // Alternative: Get as String array for simpler string operations
 * for (String line : message.getContentAsString()) {
 *     System.out.println("String line: " + line);
 * }
 * }</pre>
 *
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>Using {@code char[][]} directly is more efficient for character-level operations than {@code String[]}
 *       as it avoids object overhead</li>
 *   <li>The {@code getContentAsString()} method should be used sparingly as it may involve conversion overhead</li>
 *   <li>For frequent character operations, work directly with the {@code char[][]} representation</li>
 * </ul>
 *
 * @see Translation
 * @see TranslationText
 * @since 0.1.0
 */
@Unmodifiable
public interface TranslationMessage
  extends Translation<char[][]> {

  /**
   * Retrieves the message content as a two-dimensional character array.
   *
   * <p>The returned array is structured such that:
   * <ul>
   *   <li>The top-level array contains message segments (typically lines)</li>
   *   <li>Each inner array contains the characters of a single segment</li>
   * </ul>
   *
   * <p>This method returns an unmodifiable view of the content as indicated by the {@code @UnmodifiableView} annotation.
   * While the array itself may be modified in some implementations, consumers should treat it as read-only to maintain
   * the immutability contract of the translation framework.
   *
   * <p><strong>Note:</strong> Unlike {@link #getContentAsString()}, this method provides direct access to the character
   * data without creating intermediate String objects, making it more efficient for character-level processing.
   *
   * @return An unmodifiable view of the two-dimensional character array containing the message content
   * @since 0.1.0
   */
  @UnmodifiableView
  @Override
  char @NotNull [] @NotNull [] getContent();

  /**
   * Retrieves the message content as an array of strings.
   *
   * <p>Each element in the returned array corresponds to a segment (typically a line) of the message.
   * This is a convenience method that converts the internal {@code char[][]} representation to a more
   * familiar {@code String[]} format.
   *
   * <p><strong>Important:</strong> The returned array and its string elements are guaranteed to be unmodifiable
   * as indicated by the {@code @Unmodifiable} annotation. This means:
   * <ul>
   *   <li>The array itself cannot be modified (length is fixed)</li>
   *   <li>Each string in the array is immutable by nature of the String class</li>
   * </ul>
   *
   * <p>This method may involve conversion overhead if the implementation stores data as {@code char[][]},
   * so it should be used judiciously in performance-critical sections.
   *
   * @return An unmodifiable array of strings representing the message content, where each string is a message segment
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  String @NotNull [] getContentAsString();
}
