package me.kvdpxne.boujee.locale;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * Utility class for parsing and working with locale strings in a thread-safe,
 * efficient manner.
 * <p>
 * This class provides methods to convert locale strings (like "en_US", "pl-PL")
 * into proper {@link Locale} objects, with built-in caching for optimal
 * performance in multiplayer environments.
 *
 * @since 0.1.0
 */
public final class Locales {

  /**
   * Cache for parsed locales to avoid redundant parsing.
   * <p>
   * This is particularly important in multiplayer environments where the same
   * locales are requested repeatedly by different players.
   */
  private static final ConcurrentMap<String, Locale> LOCALE_CACHE = new ConcurrentHashMap<>(32);

  /**
   * Pattern for matching valid language codes (2-3 letters or 3 digits).
   */
  private static final Pattern LANGUAGE_PATTERN = Pattern.compile("^[a-z]{2,3}$|^[0-9]{3}$");

  /**
   * Pattern for matching valid region codes (2 letters or 3 digits).
   */
  private static final Pattern REGION_PATTERN = Pattern.compile("^[a-z]{2}$|^[0-9]{3}$");

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws AssertionError if an attempt to instantiate this class occurs.
   * @since 0.1.0
   */
  private Locales() {
    throw new AssertionError("This class is non-instantiable.");
  }

  /**
   * Parses a locale string and returns a corresponding {@link Locale} object.
   * <p>
   * The input string can be in various formats:
   * <ul>
   *   <li>"en" - language only</li>
   *   <li>"en_US" or "en-US" - language and region</li>
   *   <li>"zh_Hans_CN" or "zh-Hans-CN" - language, script, and region</li>
   *   <li>"fr__MACOS" - language and variant</li>
   * </ul>
   * <p>
   * This method is case-insensitive, so "EN_US", "en_us", and "En_Us" are treated
   * as equivalent.
   *
   * @param content The locale string to parse. Expected formats include
   *                "language_region", "language-region", or simply "language".
   * @return A {@link Locale} object constructed based on the parsed components.
   * @throws NullPointerException     if the content is {@code null}.
   * @throws IllegalArgumentException if the content is empty or contains invalid components.
   * @since 0.1.0
   */
  public static Locale fromString(final String content) {
    if (content == null) {
      throw new NullPointerException("The passed content must not be null.");
    }

    if (content.isEmpty()) {
      throw new IllegalArgumentException("The passed content must not be empty.");
    }

    // Normalizacja do formatu lowercase dla klucza cache'u
    final String normalizedKey = content.trim().toLowerCase(Locale.ENGLISH);

    try {
      return LOCALE_CACHE.computeIfAbsent(normalizedKey, key -> {
        final String[] parts = key.split("[_-]", 3); // Limit to 3 parts (lang, region, variant)

        // Validate language code (must be present and valid)
        if (parts.length == 0 || !LANGUAGE_PATTERN.matcher(parts[0]).matches()) {
          throw new IllegalArgumentException(
            "Invalid language code '" + (parts.length > 0 ? parts[0] : "") +
              "': must be 2-3 letters or 3 digits"
          );
        }

        final Locale.Builder builder = new Locale.Builder().setLanguage(parts[0]);

        // Add region if present and valid
        if (parts.length >= 2 && REGION_PATTERN.matcher(parts[1]).matches()) {
          // Region powinien być w dużych literach zgodnie z konwencją Java
          builder.setRegion(parts[1].toUpperCase(Locale.ENGLISH));
        }

        // Add variant if present
        if (parts.length >= 3 && !parts[2].isEmpty()) {
          builder.setVariant(parts[2]);
        }

        return builder.build();
      });
    } catch (IllegalArgumentException e) {
      // Usuń niepoprawny klucz z cache'u, aby nie powtarzać procesu
      LOCALE_CACHE.remove(normalizedKey);
      throw e;
    }
  }

  /**
   * Clears the locale cache.
   * <p>
   * This method should be called when locale data might have changed significantly,
   * though this is rare in most applications.
   *
   * @since 0.1.0
   */
  public static void clearCache() {
    LOCALE_CACHE.clear();
  }

  /**
   * Returns the number of cached locales.
   * <p>
   * Useful for monitoring cache size in performance-critical applications.
   *
   * @return The number of locales in the cache
   * @since 0.1.0
   */
  public static int getCacheSize() {
    return LOCALE_CACHE.size();
  }
}