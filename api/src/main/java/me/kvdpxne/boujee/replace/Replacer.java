package me.kvdpxne.boujee.replace;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for managing placeholder replacements in text.
 * <p>
 * The {@code Replacer} class allows defining mappings of placeholders to their
 * corresponding replacements, enabling dynamic substitution in strings.
 *
 * @since 0.1.0
 */
public class Replacer {

  /**
   * A map holding the placeholders and their corresponding replacement values.
   * <p>
   * The keys and values are represented as {@code char[]} for efficient string
   * manipulation.
   *
   * @since 0.1.0
   */
  protected final Map<char[], char[]> replacements;

  /**
   * Constructs a new {@code Replacer} instance with the specified initial size
   * for the replacements map.
   *
   * @param initialSize the initial capacity of the replacements map.
   * @since 0.1.0
   */
  public Replacer(
    final int initialSize
  ) {
    this.replacements = new HashMap<>(initialSize);
  }

  /**
   * Constructs a new {@code Replacer} instance with a default initial size of
   * 16 for the replacements map.
   *
   * @since 0.1.0
   */
  public Replacer() {
    this(16);
  }

  /**
   * Adds a replacement mapping to the {@code Replacer}.
   *
   * @param placeholder the placeholder to be replaced, represented as a
   *                    {@code char[]}.
   * @param replacement the replacement value, either as a {@code char[]} or any
   *                    object convertible to a string.
   * @since 0.1.0
   */
  protected void putReplacement(
    final char[] placeholder,
    final Object replacement
  ) {
    this.replacements.put(
      placeholder,
      replacement instanceof char[]
        ? (char[]) replacement
        : replacement.toString().toCharArray()
    );
  }

  /**
   * Adds a placeholder-replacement mapping to the {@code Replacer}.
   *
   * @param placeholder the placeholder string to be replaced.
   * @param replacement the replacement value, either as a string or an object
   *                    convertible to a string.
   * @return the current {@code Replacer} instance for method chaining.
   * @since 0.1.0
   */
  public Replacer with(
    final String placeholder,
    final Object replacement
  ) {
    if (null == placeholder || placeholder.isEmpty() || null == replacement) {
      return this;
    }

    if (placeholder.startsWith("{") && placeholder.endsWith("}")) {
      this.putReplacement(placeholder.toCharArray(), replacement);
      return this;
    }

    final int length = placeholder.length();
    final char[] newPlaceholder = new char[length + 2];

    newPlaceholder[0] = '{';
    newPlaceholder[newPlaceholder.length - 1] = '}';

    System.arraycopy(
      placeholder.toCharArray(),
      1,
      newPlaceholder,
      newPlaceholder.length - 2,
      length
    );

    this.putReplacement(newPlaceholder, replacement);
    return this;
  }

  /**
   * Retrieves the map of all placeholder-replacement mappings.
   *
   * @return the {@code Map} of placeholder-replacement mappings.
   * @since 0.1.0
   */
  public Map<char[], char[]> getReplacements() {
    return this.replacements;
  }

  /**
   * Determines whether this {@code Replacer} is equal to another object.
   *
   * @param o the object to compare for equality.
   * @return {@code true} if the objects are equal; {@code false} otherwise.
   * @since 0.1.0
   */
  @Override
  public final boolean equals(
    final Object o
  ) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Replacer)) {
      return false;
    }

    final Replacer replacer = (Replacer) o;
    return this.replacements.equals(replacer.replacements);
  }

  /**
   * Computes the hash code for this {@code Replacer}.
   *
   * @return the hash code of the {@code Replacer}.
   * @since 0.1.0
   */
  @Override
  public int hashCode() {
    return this.replacements.hashCode();
  }
}
