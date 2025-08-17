package me.kvdpxne.boujee.content;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A utility class for managing placeholder replacements in text.
 * <p>
 * The {@code Replacer} class allows defining mappings of placeholders to their
 * corresponding replacements, enabling dynamic substitution in strings without
 * creating intermediate String objects.
 * <p>
 * This implementation is optimized for high-concurrency environments typical
 * in multiplayer servers, with thread-safe operations and minimal garbage
 * collection pressure.
 *
 * @since 0.1.0
 */
public final class Replacer {
  /**
   * The map holding the placeholders and their corresponding replacement values.
   * <p>
   * Using ConcurrentHashMap for thread safety in multiplayer environments.
   */
  private final Map<char[], char[]> replacements;

  /**
   * Constructs a new {@code Replacer} instance with the specified initial size
   * for the replacements map.
   *
   * @param initialSize the initial capacity of the replacements map.
   * @throws IllegalArgumentException if initialSize is negative
   * @since 0.1.0
   */
  public Replacer(final int initialSize) {
    if (initialSize < 0) {
      throw new IllegalArgumentException("Initial size must not be negative");
    }
    this.replacements = new ConcurrentHashMap<>(initialSize);
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
   * Creates an immutable copy of this Replacer.
   *
   * @return a new Replacer instance with the same replacements
   * @since 0.1.0
   */
  public Replacer copy() {
    Replacer copy = new Replacer(this.replacements.size());
    copy.replacements.putAll(this.replacements);
    return copy;
  }

  /**
   * Adds a replacement mapping to the {@code Replacer}.
   *
   * @param placeholder the placeholder to be replaced, represented as a {@code char[]}
   * @param replacement the replacement value as a {@code char[]}
   * @return the current {@code Replacer} instance for method chaining
   * @throws NullPointerException if placeholder or replacement is null
   * @throws IllegalArgumentException if placeholder is empty
   * @since 0.1.0
   */
  public Replacer with(final char[] placeholder, final char[] replacement) {
    if (placeholder == null) {
      throw new NullPointerException("Placeholder must not be null");
    }
    if (0 == placeholder.length) {
      throw new IllegalArgumentException("Placeholder must not be empty");
    }
    if (replacement == null) {
      throw new NullPointerException("Replacement must not be null");
    }

    this.replacements.put(placeholder.clone(), replacement.clone());
    return this;
  }

  /**
   * Adds a replacement mapping to the {@code Replacer}.
   * <p>
   * Automatically ensures the placeholder is wrapped in braces if not already present.
   *
   * @param placeholder the placeholder string to be replaced (with or without braces)
   * @param replacement the replacement value
   * @return the current {@code Replacer} instance for method chaining
   * @throws NullPointerException if placeholder or replacement is null
   * @throws IllegalArgumentException if placeholder is empty
   * @since 0.1.0
   */
  public Replacer with(final String placeholder, final Object replacement) {
    if (placeholder == null) {
      throw new NullPointerException("Placeholder must not be null");
    }
    if (placeholder.isEmpty()) {
      throw new IllegalArgumentException("Placeholder must not be empty");
    }

    // Ensure placeholder has braces
    String normalizedPlaceholder = placeholder;
    if (!placeholder.startsWith("{") || !placeholder.endsWith("}")) {
      normalizedPlaceholder = "{" + placeholder + "}";
    }

    char[] replacementChars;
    if (replacement instanceof char[]) {
      replacementChars = ((char[]) replacement).clone();
    } else {
      replacementChars = replacement.toString().toCharArray();
    }

    return with(normalizedPlaceholder.toCharArray(), replacementChars);
  }

  /**
   * Adds multiple replacement mappings to the {@code Replacer}.
   *
   * @param values a map of placeholder-replacement pairs
   * @return the current {@code Replacer} instance for method chaining
   * @throws NullPointerException if values is null
   * @since 0.1.0
   */
  public Replacer withAll(final Map<char[], char[]> values) {
    if (values == null) {
      throw new NullPointerException("Values map must not be null");
    }

    for (Map.Entry<char[], char[]> entry : values.entrySet()) {
      with(entry.getKey(), entry.getValue());
    }
    return this;
  }

  /**
   * Adds multiple replacement mappings to the {@code Replacer}.
   * <p>
   * Automatically ensures placeholders are wrapped in braces if not already present.
   *
   * @param values a map of placeholder-replacement pairs
   * @return the current {@code Replacer} instance for method chaining
   * @throws NullPointerException if values is null
   * @since 0.1.0
   */
  public Replacer withAll(final Map<String, ?> values, final boolean ensureBraces) {
    if (values == null) {
      throw new NullPointerException("Values map must not be null");
    }

    for (Map.Entry<String, ?> entry : values.entrySet()) {
      String placeholder = entry.getKey();
      if (ensureBraces && (!placeholder.startsWith("{") || !placeholder.endsWith("}"))) {
        placeholder = "{" + placeholder + "}";
      }
      with(placeholder, entry.getValue());
    }
    return this;
  }

  /**
   * Retrieves an unmodifiable view of all placeholder-replacement mappings.
   *
   * @return an unmodifiable map of placeholder-replacement mappings
   * @since 0.1.0
   */
  public Map<char[], char[]> getReplacements() {
    // Create a defensive copy with cloned arrays
    Map<char[], char[]> safeMap = new HashMap<>(replacements.size());
    for (Map.Entry<char[], char[]> entry : replacements.entrySet()) {
      safeMap.put(entry.getKey().clone(), entry.getValue().clone());
    }
    return Collections.unmodifiableMap(safeMap);
  }

  /**
   * Checks if this Replacer has any replacements defined.
   *
   * @return true if no replacements are defined, false otherwise
   * @since 0.1.0
   */
  public boolean isEmpty() {
    return replacements.isEmpty();
  }

  /**
   * Clears all replacement mappings.
   *
   * @since 0.1.0
   */
  public void clear() {
    replacements.clear();
  }

  /**
   * Determines whether this {@code Replacer} is equal to another object.
   *
   * @param o the object to compare for equality
   * @return true if the objects are equal, false otherwise
   * @since 0.1.0
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Replacer replacer = (Replacer) o;
    // Need to compare contents, not references
    if (replacements.size() != replacer.replacements.size()) {
      return false;
    }

    for (Map.Entry<char[], char[]> entry : replacements.entrySet()) {
      char[] key = entry.getKey();
      char[] value = entry.getValue();

      boolean found = false;
      for (Map.Entry<char[], char[]> otherEntry : replacer.replacements.entrySet()) {
        if (java.util.Arrays.equals(key, otherEntry.getKey()) &&
          java.util.Arrays.equals(value, otherEntry.getValue())) {
          found = true;
          break;
        }
      }

      if (!found) {
        return false;
      }
    }

    return true;
  }

  /**
   * Computes the hash code for this {@code Replacer}.
   *
   * @return the hash code of the {@code Replacer}
   * @since 0.1.0
   */
  @Override
  public int hashCode() {
    int result = 1;
    for (Map.Entry<char[], char[]> entry : replacements.entrySet()) {
      result = 31 * result + java.util.Arrays.hashCode(entry.getKey());
      result = 31 * result + java.util.Arrays.hashCode(entry.getValue());
    }
    return result;
  }

  /**
   * Returns a string representation of this Replacer.
   *
   * @return a string representation
   * @since 0.1.0
   */
  @Override
  public String toString() {
    return "Replacer{" +
      "replacements=" + replacements.size() + " entries" +
      '}';
  }
}