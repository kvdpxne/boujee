package me.kvdpxne.boujee.replace;

public class Strings {

  /**
   * @since 0.1.0
   */
  public static boolean containsBrackets(
    final String content
  ) {
    if (null == content || content.isEmpty()) {
      return false;
    }

    int start = 0;
    for (int i = 0; i < content.length(); ++i) {
      if ('{' == content.charAt(i)) {
        start = i;
        continue;
      }

      if ('}' == content.charAt(i)) {
        if (0 != start && (start + 1) != i) {
          return true;
        }
      }
    }

    return false;
  }
}
