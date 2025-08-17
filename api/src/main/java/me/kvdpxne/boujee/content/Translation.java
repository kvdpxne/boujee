package me.kvdpxne.boujee.content;

import java.io.Serializable;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a translatable entity whose content can be retrieved in a serialized form.
 *
 * <p>This interface serves as the foundation for all translatable content in the Boujee Translation Framework.
 * It defines the contract for accessing translation data while supporting different content structures through
 * its generic type parameter. Implementations can represent anything from simple text strings to complex
 * multi-segment messages.
 *
 * <p>The key design principle is that content is represented at the character level using arrays ({@code char[]}
 * or {@code char[][]}) rather than higher-level string objects, enabling more efficient processing and
 * manipulation of translation data.
 *
 * <p>Concrete implementations include:
 * <ul>
 *   <li>{@link TranslationText} - For single-segment text translations (content type: {@code char[]})</li>
 *   <li>{@link TranslationMessage} - For multi-segment message translations (content type: {@code char[][]})</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Working with TranslationText
 * TranslationText singleLine = ...;
 * char[] charContent = singleLine.getContent();
 * String stringContent = new String(charContent);
 *
 * // Working with TranslationMessage
 * TranslationMessage multiLine = ...;
 * char[][] messageContent = multiLine.getContent();
 * for (char[] line : messageContent) {
 *     System.out.println(new String(line));
 * }
 * }</pre>
 *
 * <p><strong>Implementation Requirements:</strong>
 * <ul>
 *   <li>All implementations must be serializable to support distributed systems and caching</li>
 *   <li>Content retrieval methods should return unmodifiable views or copies to maintain immutability</li>
 *   <li>Implementations should provide efficient access to the character data for performance-critical operations</li>
 *   <li>Concrete implementations should document the specific content structure they represent</li>
 * </ul>
 *
 * <p><strong>Design Rationale:</strong> The use of character arrays instead of strings provides several benefits:
 * <ul>
 *   <li>Reduced memory overhead compared to string objects</li>
 *   <li>More efficient character-level operations without intermediate string allocations</li>
 *   <li>Better cache locality for large translation datasets</li>
 *   <li>Direct compatibility with low-level text processing APIs</li>
 * </ul>
 *
 * @param <T> The type of the content, which must implement {@link Serializable}. Typically either
 *            {@code char[]} for single-segment content or {@code char[][]} for multi-segment content.
 * @see TranslationText
 * @see TranslationMessage
 * @see Serializable
 * @since 0.1.0
 */
public interface Translation<T extends Serializable>
  extends Serializable {

  /**
   * Retrieves the translatable content associated with this instance.
   *
   * <p>The specific structure of the returned content depends on the concrete implementation:
   * <ul>
   *   <li>{@link TranslationText} implementations return a {@code char[]} representing a single text segment</li>
   *   <li>{@link TranslationMessage} implementations return a {@code char[][]} where each inner array
   *       represents a separate message segment or line</li>
   * </ul>
   *
   * <p>Implementations should return an unmodifiable view of the content to maintain the immutability contract.
   * Callers should treat the returned content as read-only, even if the implementation returns a mutable array.
   *
   * <p><strong>Performance Note:</strong> Direct access to the character array is more efficient for character-level
   * operations than converting to strings, as it avoids object creation overhead.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Processing content directly as character array
   * char[] content = translation.getContent();
   * for (int i = 0; i < content.length; i++) {
   *     // Perform character-level operations
   *     if (content[i] == '!') {
   *         content[i] = '.';
   *     }
   * }
   * }</pre>
   *
   * @return The content of this translation as a serializable object of type T
   * @since 0.1.0
   */
  @NotNull
  T getContent();
}
