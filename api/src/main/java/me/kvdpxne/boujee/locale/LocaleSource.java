package me.kvdpxne.boujee.locale;

import java.util.Locale;

/**
 * Provides an interface for retrieving localized content and locale
 * information.
 * <p>
 * The {@code LocaleSource} interface extends {@link LocaleSourceProvider},
 * enabling the retrieval of a {@link LocaleSource} instance as well as specific
 * localization details such as locale strings and {@link Locale} objects.
 *
 * @since 0.1.0
 */
public interface LocaleSource
  extends LocaleSourceProvider {

  /**
   * Retrieves the localization string associated with this source. The
   * localization string typically represents a localized key or value that can
   * be used for internationalization purposes.
   *
   * @return a {@link String} representing the localized content.
   * @since 0.1.0
   */
  String getLocalization();

  /**
   * Retrieves the {@link Locale} object associated with this source. The
   * {@link Locale} provides access to regional and language-specific settings.
   *
   * @return a {@link Locale} object representing the locale configuration.
   * @since 0.1.0
   */
  Locale getLocale();
}
