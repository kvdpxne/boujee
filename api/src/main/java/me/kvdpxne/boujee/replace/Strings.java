package me.kvdpxne.boujee.replace;

public final class Strings {

  public static boolean containsPlaceholder(final String placeholder) {
    return placeholder.startsWith("{") && placeholder.endsWith("}");
  }
}
