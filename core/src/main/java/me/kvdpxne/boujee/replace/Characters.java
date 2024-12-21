package me.kvdpxne.boujee.replace;

/**
 * @since 0.1.0
 */
public final class Characters {

  /**
   * @param source
   * @since 0.1.0
   */
  private static boolean _empty(
    final char[] source
  ) {
    return null == source || 0 == source.length;
  }

  /**
   * @param source
   * @param search
   * @param offset
   *
   *
   * @since 0.1.0
   */
  public static int _indexOf(
    final char[] source,
    final char[] search,
    final int offset
  ) {
    if (0 > source.length - offset - search.length + 1) {
      return -1;
    }

    final char first = search[0];
    final int limit = source.length - search.length;

    for (int i = offset; i <= limit; i++) {
      if (source[i] != first) {
        while (++i <= limit && source[i] != first);
      }

      if (i <= limit) {
        int j = i + 1;
        int end = j + search.length - 1;
        for (int k = 1; j < end && source[j] == search[k]; j++, k++);

        if (j == end) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * @param source
   * @param search
   * @param offset
   * @since 0.1.0
   */
  private static int _count(
    final char[] source,
    final char[] search,
    int offset
  ) {
    int count = 0;
    while (-1 != (offset = _indexOf(source, search, offset + search.length))) {
      ++count;
    }
    return count;
  }

  /**
   * @param source
   * @param search
   * @param replacement
   * @since 0.1.0
   */
  public static char[] replace(
    final char[] source,
    final char[] search,
    final char[] replacement,
    final int limit
  ) {
    if (_empty(source) || _empty(search) || _empty(replacement) || 0 == limit) {
      return source;
    }

    int first = _indexOf(source, search, 0);
    if (-1 == first) {
      return source;
    }

    int count = 1;
    if (source.length > first + search.length) {
      count += _count(source, search, first);
    }

    final int space = replacement.length - search.length;
    final char[] buffer = new char[
      source.length + (-1 == limit || limit >= count
        ? count * space
        : limit * space + (count - limit) * search.length)
      ];

    int a = 0;
    int b = 0;
    int start = first;
    int free = 0;

    for (int i = 0; i < count; ++i) {
      System.arraycopy(source, b, buffer, a, first - b);
      System.arraycopy(replacement, 0, buffer, start, replacement.length);

      a = start + replacement.length;
      if (limit == i || (free = buffer.length - a) < replacement.length) {
        break;
      }

      b = first + search.length;
      first = _indexOf(source, search, b);
      if (-1 == first) {
        break;
      }

      start = first + (1 < i ? i * space : space);
    }

    if (0 < free) {
      System.arraycopy(
        source,
        a + count * (search.length - replacement.length),
        buffer,
        a,
        buffer.length - a + Math.max(0, limit)
      );
    }

    return buffer;
  }

  /**
   * @param source
   * @param search
   * @param replacement
   *
   * @since 0.1.0
   */
  public static char[] replace(
    final char[] source,
    final char[] search,
    final char[] replacement
  ) {
    return replace(source, search, replacement, -1);
  }
}
