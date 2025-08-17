package me.kvdpxne.boujee.content.message;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import me.kvdpxne.boujee.replace.Characters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a translatable message that allows dynamic replacement of placeholders.
 * <p>
 * This class extends {@link BasicTranslationMessage} to provide replacement functionality
 * while maintaining immutability and high performance in multiplayer environments.
 * <p>
 * All replacement operations return new instances, preserving the original message.
 *
 * @since 0.1.0
 */
@Unmodifiable
public class BasicReplaceableTranslationMessage
  extends BasicTranslationMessage
  implements ReplacableTranslationMessage {

  /**
   * Serial version UID for serialization compatibility.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = -8395546537545631026L;

  /**
   * Constructs a new BasicReplaceableTranslationMessage with the provided content.
   *
   * @param message a 2D character array representing the message content
   * @since 0.1.0
   */
  public BasicReplaceableTranslationMessage(
    final char @NotNull [] @NotNull [] message
  ) {
    super(message);
  }

  @NotNull
  @Override
  public ReplacableTranslationMessage replace(
    final char @NotNull [] field,
    final char @NotNull [] value
  ) {
    // Check if replacement is needed
    if (Characters.isEmpty(field) || Characters.isEmpty(value)) {
      return this;
    }

    // Check if field exists in the message
    boolean needsReplacement = false;
    for (char[] line : this.message) {
      if (Characters.indexOf(line, field, 0) != -1) {
        needsReplacement = true;
        break;
      }
    }

    if (!needsReplacement) {
      return this;
    }

    // Perform replacement
    char[][] newContent = new char[this.message.length][];
    for (int i = 0; i < this.message.length; i++) {
      newContent[i] = Characters.replace(this.message[i], field, value);
    }

    return new BasicReplaceableTranslationMessage(newContent);
  }

  @NotNull
  @Override
  public ReplacableTranslationMessage replace(
    final @NotNull Map<char @NotNull [], char @NotNull []> values
  ) {
    // noinspection ConstantValue
    if (null == values || values.isEmpty()) {
      return this;
    }

    // Check if any replacement is needed
    boolean needsReplacement = false;
    for (Map.Entry<char[], char[]> entry : values.entrySet()) {
      char[] field = entry.getKey();
      if (Characters.isEmpty(field)) {
        continue;
      }

      for (char[] line : this.message) {
        if (Characters.indexOf(line, field, 0) != -1) {
          needsReplacement = true;
          break;
        }
      }

      if (needsReplacement) {
        break;
      }
    }

    if (!needsReplacement) {
      return this;
    }

    // Perform replacements
    char[][] newContent = new char[this.message.length][];
    for (int i = 0; i < this.message.length; i++) {
      char[] line = this.message[i];
      for (Map.Entry<char[], char[]> entry : values.entrySet()) {
        char[] field = entry.getKey();
        char[] value = entry.getValue();
        if (!Characters.isEmpty(field) && !Characters.isEmpty(value)) {
          line = Characters.replace(line, field, value);
        }
      }
      newContent[i] = line;
    }

    return new BasicReplaceableTranslationMessage(newContent);
  }
}