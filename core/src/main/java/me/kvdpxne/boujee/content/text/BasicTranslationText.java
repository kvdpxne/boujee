package me.kvdpxne.boujee.content.text;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents a translatable text content as a single string.
 * <p>
 * This class provides an immutable representation of text using char[] arrays
 * to minimize garbage collection pressure in high-performance environments.
 * <p>
 * All operations preserve immutability by returning new instances when needed.
 *
 * @since 0.1.0
 */
public class BasicTranslationText implements TranslationText {
  /**
   * Serial version UID for serialization compatibility.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = 2118276182344594976L;

  /**
   * The text content as a character array.
   * <p>
   * This field is final to ensure immutability.
   *
   * @since 0.1.0
   */
  protected final char[] text;

  /**
   * Cache for the string representation of the text.
   * <p>
   * Using volatile for thread-safe lazy initialization.
   *
   * @since 0.1.0
   */
  private transient volatile String contentAsStringCache;

  /**
   * Constructs a new BasicTranslationText with the provided content.
   *
   * @param text a character array representing the text content
   * @throws NullPointerException if text is null
   * @since 0.1.0
   */
  @SuppressWarnings("ConstantValue")
  public BasicTranslationText(
    final char @NotNull [] text
  ) {
    if (null == text) {
      throw new NullPointerException("The text array must not be null.");
    }

    this.text = text;
  }

  /**
   * Constructs an empty BasicTranslationText.
   *
   * @since 0.1.0
   */
  public BasicTranslationText() {
    this(new char[0]);
  }

  @UnmodifiableView
  @Override
  public char @NotNull [] getContent() {
    // Create a copy to preserve immutability
    return Arrays.copyOf(this.text, this.text.length);
  }

  @NotNull
  @Unmodifiable
  @Override
  public String getContentAsString() {
    // Lazy initialization with double-checked locking pattern
    String cache = this.contentAsStringCache;
    if (cache != null) {
      return cache;
    }

    synchronized (this) {
      cache = this.contentAsStringCache;
      if (cache != null) {
        return cache;
      }

      // Create and cache the string representation
      this.contentAsStringCache = new String(this.text);
      return this.contentAsStringCache;
    }
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BasicTranslationText)) {
      return false;
    }
    final BasicTranslationText that = (BasicTranslationText) o;
    return Arrays.equals(this.text, that.text);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.text);
  }
}