package me.kvdpxne.boujee.content.text;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import me.kvdpxne.boujee.replace.Characters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a translatable text that allows dynamic replacement of
 * placeholders.
 * <p>
 * This class extends {@link BasicTranslationText} to provide replacement
 * functionality while maintaining immutability and high performance in
 * multiplayer environments.
 * <p>
 * All replacement operations return new instances, preserving the original
 * text.
 *
 * @since 0.1.0
 */
@Unmodifiable
public class BasicReplaceableTranslationText
  extends BasicTranslationText
  implements ReplaceableTranslationText {

  /**
   * Serial version UID for serialization compatibility.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = 3626337823661723471L;

  /**
   * Constructs a new BasicReplaceableTranslationText with the provided
   * content.
   *
   * @param content a character array representing the text content
   * @since 0.1.0
   */
  public BasicReplaceableTranslationText(
    final char @NotNull [] content
  ) {
    super(content);
  }

  @NotNull
  @Override
  public ReplaceableTranslationText replace(
    final char @NotNull [] field,
    final char @NotNull [] value
  ) {
    // Check if replacement is needed
    if (Characters.isEmpty(field) || Characters.isEmpty(value)) {
      return this;
    }

    // Check if field exists in the text
    if (Characters.indexOf(this.text, field, 0) == -1) {
      return this;
    }

    // Perform replacement
    char[] newContent = Characters.replace(this.text, field, value);
    return new BasicReplaceableTranslationText(newContent);
  }

  @NotNull
  @Override
  public ReplaceableTranslationText replace(
    final @NotNull Map<char @NotNull [], char @NotNull []> values
  ) {
    //noinspection ConstantValue
    if (null == values || values.isEmpty()) {
      return this;
    }

    // Check if any replacement is needed
    boolean needsReplacement = false;
    for (Map.Entry<char[], char[]> entry : values.entrySet()) {
      char[] field = entry.getKey();
      if (!Characters.isEmpty(field) &&
        Characters.indexOf(this.text, field, 0) != -1) {
        needsReplacement = true;
        break;
      }
    }

    if (!needsReplacement) {
      return this;
    }

    // Perform replacements
    char[] newContent = this.text;
    for (Map.Entry<char[], char[]> entry : values.entrySet()) {
      char[] field = entry.getKey();
      char[] value = entry.getValue();
      if (!Characters.isEmpty(field) && !Characters.isEmpty(value)) {
        newContent = Characters.replace(newContent, field, value);
      }
    }

    return new BasicReplaceableTranslationText(newContent);
  }
}