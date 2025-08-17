package me.kvdpxne.boujee.replace;

/**
 * Utility class for efficient character array manipulation without creating String objects.
 * <p>
 * This class provides high-performance methods for searching and replacing text in char[]
 * arrays, which is critical for i18n systems in multiplayer environments where
 * garbage collection pressure must be minimized.
 * <p>
 * All methods operate directly on char[] arrays to avoid unnecessary String object creation.
 *
 * @since 0.1.0
 */
public final class Characters {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws AssertionError if an attempt to instantiate this class occurs.
   * @since 0.1.0
   */
  private Characters() {
    throw new AssertionError("This class is non-instantiable.");
  }

  /**
   * Checks if the given char array is empty or null.
   *
   * @param source the char array to check
   * @return true if the array is null or has zero length, false otherwise
   * @since 0.1.0
   */
  public static boolean isEmpty(final char[] source) {
    return source == null || source.length == 0;
  }

  /**
   * Finds the index of the first occurrence of a search array within the source array.
   * <p>
   * This implementation uses an optimized Boyer-Moore-Horspool-like algorithm for
   * efficient searching.
   *
   * @param source the source char array to search in
   * @param search the char array to search for
   * @param offset the starting position for the search
   * @return the index of the first occurrence, or -1 if not found
   * @since 0.1.0
   */
  public static int indexOf(final char[] source, final char[] search, final int offset) {
    if (isEmpty(source) || isEmpty(search) || offset >= source.length || offset < 0) {
      return -1;
    }

    final int sourceLength = source.length;
    final int searchLength = search.length;
    final int limit = sourceLength - searchLength;

    if (offset > limit) {
      return -1;
    }

    final char first = search[0];
    for (int i = offset; i <= limit; i++) {
      // Fast skip if first character doesn't match
      if (source[i] != first) {
        continue;
      }

      // Check remaining characters
      int j = 1;
      while (j < searchLength && source[i + j] == search[j]) {
        j++;
      }

      if (j == searchLength) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Counts the number of occurrences of a search array within the source array.
   *
   * @param source the source char array to search in
   * @param search the char array to search for
   * @param offset the starting position for the search
   * @return the number of occurrences found
   * @since 0.1.0
   */
  public static int count(final char[] source, final char[] search, int offset) {
    if (isEmpty(source) || isEmpty(search) || offset < 0) {
      return 0;
    }

    int count = 0;
    while (offset <= source.length - search.length) {
      int index = indexOf(source, search, offset);
      if (index == -1) {
        break;
      }
      count++;
      offset = index + search.length;
    }
    return count;
  }

  /**
   * Replaces all occurrences of a search array with a replacement array in the source array.
   *
   * @param source      the source char array
   * @param search      the char array to search for and replace
   * @param replacement the char array to use as replacement
   * @return a new char array with replacements applied, or the original if no replacements were made
   * @since 0.1.0
   */
  public static char[] replace(final char[] source, final char[] search, final char[] replacement) {
    return replace(source, search, replacement, -1);
  }

  /**
   * Replaces up to 'limit' occurrences of a search array with a replacement array in the source array.
   *
   * @param source      the source char array
   * @param search      the char array to search for and replace
   * @param replacement the char array to use as replacement
   * @param limit       the maximum number of replacements to perform (-1 for unlimited)
   * @return a new char array with replacements applied, or the original if no replacements were made
   * @since 0.1.0
   */
  public static char[] replace(
    final char[] source,
    final char[] search,
    final char[] replacement,
    final int limit
  ) {
    if (isEmpty(source) || isEmpty(search) || isEmpty(replacement) || limit == 0) {
      return source;
    }

    // Find first occurrence
    int firstIndex = indexOf(source, search, 0);
    if (firstIndex == -1) {
      return source;
    }

    // Count total occurrences (up to limit if specified)
    int totalCount = 1;
    if (limit != 1) {
      totalCount += count(source, search, firstIndex + search.length);
      if (limit > 0 && limit < totalCount) {
        totalCount = limit;
      }
    }

    // Calculate new buffer size
    final int searchLength = search.length;
    final int replacementLength = replacement.length;
    final int sizeDifference = replacementLength - searchLength;
    final int newSize = source.length + totalCount * sizeDifference;

    // Create and fill the new buffer
    final char[] buffer = new char[newSize];
    int sourcePos = 0;
    int bufferPos = 0;
    int replacementsDone = 0;

    while (replacementsDone < totalCount) {
      // Copy characters before the match
      int matchIndex = (replacementsDone == 0) ? firstIndex :
        indexOf(source, search, sourcePos);
      if (matchIndex == -1) {
        break;
      }

      int charsToCopy = matchIndex - sourcePos;
      if (charsToCopy > 0) {
        System.arraycopy(source, sourcePos, buffer, bufferPos, charsToCopy);
        bufferPos += charsToCopy;
      }

      // Copy replacement
      System.arraycopy(replacement, 0, buffer, bufferPos, replacementLength);
      bufferPos += replacementLength;

      // Move positions forward
      sourcePos = matchIndex + searchLength;
      replacementsDone++;
    }

    // Copy remaining characters
    if (sourcePos < source.length) {
      int charsLeft = source.length - sourcePos;
      System.arraycopy(source, sourcePos, buffer, bufferPos, charsLeft);
    }

    return buffer;
  }
}