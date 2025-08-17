package me.kvdpxne.boujee.locale;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a specific locale configuration with associated localization information.
 *
 * <p>This interface extends {@link LocaleSourceProvider} to provide direct access to locale-specific data while
 * maintaining the ability to serve as a provider for itself. It defines the contract for objects that represent
 * a specific language/country/variant combination used in the translation framework.
 *
 * <p>A locale source typically contains:
 * <ul>
 *   <li>A localization string (e.g., "en-US", "fr-FR") that uniquely identifies the locale</li>
 *   <li>A corresponding {@link java.util.Locale} object for Java platform compatibility</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Creating and using a locale source
 * LocaleSource enUs = new DefaultLocaleSource("en-US", Locale.US);
 *
 * System.out.println("Localization: " + enUs.getLocalization());
 * System.out.println("Language: " + enUs.getLocale().getLanguage());
 *
 * // Using as a provider
 * LocaleSourceProvider provider = enUs;
 * LocaleSource sameSource = provider.getLocaleSource();
 * }</pre>
 *
 * <p><strong>Key Features:</strong>
 * <ul>
 *   <li>Immutability: Once created, a locale source cannot be modified</li>
 *   <li>Self-providing: A locale source can act as its own provider</li>
 *   <li>Java Locale integration: Direct access to standard Java locale objects</li>
 *   <li>Simple string-based identification: Human-readable locale identifiers</li>
 * </ul>
 *
 * <p><strong>Implementation Requirements:</strong>
 * <ul>
 *   <li>All implementations must be thread-safe</li>
 *   <li>The {@code @Unmodifiable} annotation must be honored in all implementations</li>
 *   <li>Equality should be based on the localization string for consistent behavior</li>
 *   <li>Implementations should cache any derived values for performance</li>
 * </ul>
 *
 * @see LocaleSourceProvider
 * @see java.util.Locale
 * @since 0.1.0
 */
@Unmodifiable
public interface LocaleSource
  extends LocaleSourceProvider {

  /**
   * Retrieves the localization string associated with this locale source.
   *
   * <p>The localization string is typically in the format "language-country-variant" (e.g., "en-US", "fr-FR")
   * and serves as a human-readable identifier for the locale. This string is used throughout the framework
   * for:
   * <ul>
   *   <li>File naming conventions for translation resources</li>
   *   <li>URL parameters in web applications</li>
   *   <li>Configuration settings</li>
   *   <li>Debugging and logging</li>
   * </ul>
   *
   * <p><strong>Important:</strong> The returned string is guaranteed to be unmodifiable as indicated by the
   * {@code @Unmodifiable} annotation. This ensures the stability of the locale identifier throughout its usage.
   *
   * @return An unmodifiable string representing the localization identifier (never null)
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  String getLocalization();

  /**
   * Retrieves the Java {@link java.util.Locale} object associated with this locale source.
   *
   * <p>This method provides integration with the standard Java localization framework, allowing the translation
   * system to work seamlessly with other Java APIs that require locale information. The returned Locale object
   * can be used for:
   * <ul>
   *   <li>Date and number formatting</li>
   *   <li>Collation and sorting</li>
   *   <li>Resource bundle loading</li>
   *   <li>Language-specific behavior in other libraries</li>
   * </ul>
   *
   * <p><strong>Important:</strong> The returned Locale object is unmodifiable as all Java Locale objects are immutable.
   * This ensures consistent behavior when the locale is used across different parts of an application.
   *
   * @return An unmodifiable {@link java.util.Locale} object representing the locale configuration (never null)
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  Locale getLocale();
}
