package me.kvdpxne.boujee.content.message;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents a translatable message that may consist of multiple lines or segments.
 * <p>
 * This class provides an immutable representation of a message using char[][] arrays
 * to minimize garbage collection pressure in high-performance environments.
 * <p>
 * All operations that modify content return new instances, preserving immutability.
 *
 * @since 0.1.0
 */
@Unmodifiable
public class BasicTranslationMessage implements TranslationMessage {
  /**
   * Serial version UID for serialization compatibility.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = 226473269992833938L;

  /**
   * The message content as a 2D character array.
   * <p>
   * Each inner array represents a single line or segment of the message.
   * This field is final to ensure immutability.
   *
   * @since 0.1.0
   */
  protected final char[][] message;

  /**
   * Cache for the string representation of the message.
   * <p>
   * Using AtomicReference for thread-safe lazy initialization in high-concurrency environments.
   *
   * @since 0.1.0
   */
  private transient volatile String[] contentAsStringCache;

  /**
   * Constructs a new BasicTranslationMessage with the provided content.
   *
   * @param message a 2D character array representing the message content
   * @throws NullPointerException if message is null or contains null elements
   * @since 0.1.0
   */
  @SuppressWarnings("ConstantValue")
  public BasicTranslationMessage(
    final char @NotNull [] @NotNull [] message
  ) {
    if (null == message) {
      throw new NullPointerException("The message array must not be null.");
    }

    for (final char[] line : message) {
      if (null == line) {
        throw new NullPointerException("Message lines must not be null.");
      }
    }

    this.message = message;
  }

  /**
   * Constructs an empty BasicTranslationMessage.
   *
   * @since 0.1.0
   */
  public BasicTranslationMessage() {
    this(new char[0][]);
  }

  @UnmodifiableView
  @Override
  public char @NotNull [] @NotNull [] getContent() {
    // Create a deep copy to preserve immutability
    final char[][] copy = new char[this.message.length][];
    for (int i = 0; i < this.message.length; i++) {
      copy[i] = Arrays.copyOf(this.message[i], this.message[i].length);
    }
    return copy;
  }

  @NotNull
  @Unmodifiable
  @Override
  public String @NotNull [] getContentAsString() {
    // Lazy initialization with double-checked locking pattern
    String[] cache = this.contentAsStringCache;
    if (cache != null) {
      return cache;
    }

    synchronized (this) {
      cache = this.contentAsStringCache;
      if (cache != null) {
        return cache;
      }

      // Create and cache the string representation
      final String[] result = new String[this.message.length];
      for (int i = 0; i < this.message.length; i++) {
        result[i] = new String(this.message[i]);
      }
      this.contentAsStringCache = result;
      return result;
    }
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BasicTranslationMessage)) {
      return false;
    }
    final BasicTranslationMessage that = (BasicTranslationMessage) o;
    return Arrays.deepEquals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.message);
  }
}