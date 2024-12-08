package me.kvdpxne.boujee.locale;

import java.util.Locale;

/**
 * @since 0.1.0
 */
public final class Locales {

    /**
     * Parses a locale string and returns a corresponding [Locale] object.
     *
     * The input string should be in the format "language_region" or
     * "language-region", where "language" is a two-letter ISO-639 language
     * code (e.g., "en") and "region" is a two-letter ISO-3166 country/region
     * code (e.g., "US").
     *
     * @param content The locale string to parse. Expected format:
     *               "language_region" or "language-region".
     * @return A [Locale] object constructed based on the parsed language
     *         and region.
     * @throws IllegalArgumentException if the input string is blank or does not
     *         contain both language and region codes.
     *
     * @since 0.1.0
     */
    public static Locale fromString(
      final String content
    ) {
      if (null == content) {
        throw new NullPointerException(
          "The passed content must not be null."
        );
      }

      if (content.isEmpty()) {
        throw new IllegalArgumentException(
          "The passed content must not be empty."
        );
      }

      final String[] parts = content.trim().split("[_-]");
      if (2 >= parts.length) {
        throw new IllegalArgumentException(
          "The locale string must contain both a language and a region."
        );
      }

      final String language = parts[0];
      final String region = parts[1];

      return new Locale.Builder()
        .setLanguage(language)
        .setRegion(region)
        .build();
    }
}
