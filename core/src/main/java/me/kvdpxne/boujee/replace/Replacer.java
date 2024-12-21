package me.kvdpxne.boujee.replace;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 0.1.0
 */
public class Replacer {

  /**
   * @since 0.1.0
   */
  public final Map<char[], char[]> replacements;

  /**
   * @since 0.1.0
   */
  public Replacer(
    final int initialSize
  ) {
    this.replacements = new HashMap<>(initialSize);
  }

  /**
   * @since 0.1.0
   */
  public Replacer() {
    this(16);
  }

  /**
   * @since 0.1.0
   */
  public Replacer set(
    final String placeholder,
    final Object replacement
  ) {
    this.replacements.put(
      placeholder.toCharArray(),
      replacement instanceof char[]
        ? (char[]) replacement
        : replacement.toString().toCharArray()
    );
    return this;
  }

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

  @Override
  public int hashCode() {
    return this.replacements.hashCode();
  }
}
