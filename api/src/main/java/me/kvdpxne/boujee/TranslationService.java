package me.kvdpxne.boujee;

import java.util.Collection;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;
import me.kvdpxne.boujee.exceptions.MissingTranslationKeyException;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.LocaleTranslations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Central service interface for managing all translation operations within the
 * Boujee Translation Framework.
 * <p>This interface provides a comprehensive API for accessing and managing
 * localized content across multiple locales. It serves as the primary entry
 * point for applications needing to retrieve translations for user interface
 * elements, messages, and other translatable content.
 * <p>Key responsibilities of this interface include:
 * <ul>
 *   <li>Managing the collection of available locales and their translations</li>
 *   <li>Providing access to translation content by locale and key</li>
 *   <li>Handling fallback mechanisms for missing translations</li>
 *   <li>Maintaining statistics about the translation system</li>
 *   <li>Supporting dynamic updates to the translation repository</li>
 * </ul>
 * <p><strong>Usage Pattern:</strong> The service is typically obtained through dependency injection
 * or a service locator, then used to retrieve translations as needed:
 * <pre>{@code
 * // Getting the translation service (implementation-specific)
 * TranslationService translationService = getTranslationService();
 * // Retrieving a text translation with fallback
 * TranslationText buttonText = translationService.findTextOrDefault(
 *     userLocaleProvider,
 *     () -> TranslationKey.of("BUTTON_SUBMIT")
 * );
 * // Getting a message with null safety
 * TranslationMessage welcomeMessage = translationService.findMessageOrNull(
 *     userLocaleProvider,
 *     () -> TranslationKey.of("WELCOME_MESSAGE")
 * );
 * if (welcomeMessage != null) {
 *     // Process the message
 * }
 * }</pre>
 * <p><strong>Key Features:</strong>
 * <ul>
 *   <li><strong>Flexible Locale Handling:</strong> Works with any {@link LocaleSourceProvider} implementation</li>
 *   <li><strong>Null Safety:</strong> Provides both "orNull" and "orElseDefault" access patterns</li>
 *   <li><strong>Thread Safety:</strong> All implementations must be thread-safe for concurrent access</li>
 *   <li><strong>Statistics:</strong> Offers metrics about the translation repository size and composition</li>
 *   <li><strong>Dynamic Updates:</strong> Supports runtime changes to the default locale configuration</li>
 * </ul>
 * <p><strong>Implementation Requirements:</strong>
 * <ul>
 *   <li>All collection views should be unmodifiable as indicated by {@code @UnmodifiableView}</li>
 *   <li>Implementations should optimize for read-heavy workloads typical in translation systems</li>
 *   <li>Thread safety is essential as multiple threads may access translations concurrently</li>
 *   <li>Implementations should provide efficient lookup (typically O(1)) using hash-based structures</li>
 * </ul>
 *
 * @see LocaleSource
 * @see LocaleTranslations
 * @see TranslationText
 * @see TranslationMessage
 * @since 0.1.0
 */
public interface TranslationService {

  /**
   * Retrieves an unmodifiable view of all loaded locale sources.
   * <p>The returned collection contains all {@link LocaleSource} instances
   * that
   * have been loaded into the translation system. Each locale source represents
   * a specific language/country/variant combination supported by the
   * application.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * Collection<LocaleSource> locales = translationService.getLoadedLocaleSources();
   * System.out.println("Supported locales: " + locales.size());
   * for (LocaleSource locale : locales) {
   *     System.out.println("- " + locale.getLocalization());
   * }
   * }</pre>
   * <p><strong>Important:</strong> The returned collection is an unmodifiable
   * view of the internal storage as indicated by the {@code @UnmodifiableView}
   * annotation. This prevents accidental modification of the locale registry.
   *
   * @return An unmodifiable view of the collection containing all loaded locale
   * sources
   * @since 0.1.0
   */
  @NotNull
  @UnmodifiableView
  Collection<@NotNull LocaleSource> getLoadedLocaleSources();

  /**
   * Retrieves an unmodifiable view of all loaded locale translations.
   * <p>The returned collection contains {@link LocaleTranslations} objects for
   * each supported locale. Each object manages the translation content (both
   * messages and texts) for its specific locale.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * Collection<LocaleTranslations> translations = translationService.getLoadedLocaleTranslations();
   * for (LocaleTranslations localeTranslations : translations) {
   *     System.out.println("Locale: " + localeTranslations.getLocaleSource().getLocalization());
   *     System.out.println("Messages: " + localeTranslations.getNumberOfMessages());
   *     System.out.println("Texts: " + localeTranslations.getNumberOfTexts());
   * }
   * }</pre>
   * <p><strong>Important:</strong> The returned collection is an unmodifiable
   * view of the internal storage as indicated by the {@code @UnmodifiableView}
   * annotation. This prevents accidental modification of the translation
   * repository.
   *
   * @return An unmodifiable view of the collection containing all loaded locale
   * translations
   * @since 0.1.0
   */
  @NotNull
  @UnmodifiableView
  Collection<LocaleTranslations> getLoadedLocaleTranslations();

  /**
   * Retrieves the current default locale source, if one has been set.
   * <p>The default locale source is used as a fallback when a specific locale
   * is not available or when no locale provider is specified in translation
   * requests.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * LocaleSource defaultLocale = translationService.getDefaultLocaleSource();
   * if (defaultLocale != null) {
   *     System.out.println("Default locale: " + defaultLocale.getLocalization());
   * } else {
   *     System.out.println("No default locale configured");
   * }
   * }</pre>
   *
   * @return The default {@link LocaleSource} if configured; {@code null}
   * otherwise
   * @since 0.1.0
   */
  @Nullable
  LocaleSource getDefaultLocaleSource();

  /**
   * Retrieves the translations for the default locale, if one has been set.
   * <p>This method provides direct access to the translation content for the
   * default locale, which can be useful for operations that specifically target
   * the default language.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * LocaleTranslations defaultTranslations = translationService.getDefaultLocaleTranslations();
   * if (defaultTranslations != null) {
   *     // Use default translations
   * }
   * }</pre>
   *
   * @return The {@link LocaleTranslations} for the default locale if
   * configured; {@code null} otherwise
   * @since 0.1.0
   */
  @Nullable
  LocaleTranslations getDefaultLocaleTranslations();

  /**
   * Updates the default locale source using the provided locale source
   * provider.
   * <p>This method changes the default locale configuration without affecting
   * existing translations. It's typically used when the application needs to
   * dynamically change the default language based on user preferences or system
   * settings.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Change default locale to French
   * translationService.updateDefaultLocaleSource(
   *     () -> new DefaultLocaleSource("fr-FR", Locale.FRANCE)
   * );
   * }</pre>
   *
   * @param localeSourceProvider The provider supplying the new default locale
   *                             source (must not be null)
   * @throws NullPointerException if localeSourceProvider is null
   * @since 0.1.0
   */
  void updateDefaultLocaleSource(
    @NotNull LocaleSourceProvider localeSourceProvider
  );

  /**
   * Updates the default locale translations to match the current default locale
   * source.
   * <p>This method synchronizes the default translations with the current
   * default locale source. It should be called after changing the default
   * locale source to ensure the translation system is properly configured.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Update default locale and then synchronize translations
   * translationService.updateDefaultLocaleSource(
   *     () -> new DefaultLocaleSource("es-ES", new Locale("es", "ES"))
   * );
   * translationService.updateDefaultLocaleTranslations();
   * }</pre>
   *
   * @since 0.1.0
   */
  void updateDefaultLocaleTranslations();

  /**
   * Searches for the translations associated with the specified locale.
   * <p>This method attempts to locate the translation content for the
   * specified
   * locale. If no matching translations are found, this method returns
   * {@code null}.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * LocaleTranslations translations = translationService.findLocaleTranslationsOrNull(
   *     () -> new DefaultLocaleSource("de-DE", Locale.GERMANY)
   * );
   * if (translations != null) {
   *     // Use German translations
   * }
   * }</pre>
   * <p><strong>Implementation Note:</strong> Implementations should provide
   * efficient lookup (typically O(1)) using a hash-based data structure.
   *
   * @param localeSourceProvider The provider supplying the locale source to
   *                             search for (must not be null)
   * @return The {@link LocaleTranslations} if found; {@code null} otherwise
   * @throws NullPointerException if localeSourceProvider is null
   * @since 0.1.0
   */
  @Nullable
  LocaleTranslations findLocaleTranslationsOrNull(
    @NotNull LocaleSourceProvider localeSourceProvider
  );

  /**
   * Searches for a message translation using the specified locale and
   * translation key.
   * <p>This method attempts to locate a specific message translation for the
   * given locale. If no matching message is found, this method returns
   * {@code null}.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * TranslationMessage message = translationService.findMessageOrNull(
   *     userLocaleProvider,
   *     () -> TranslationKey.of("WELCOME_MESSAGE")
   * );
   * if (message != null) {
   *     // Use the message
   * }
   * }</pre>
   * <p><strong>Implementation Note:</strong> Implementations should provide
   * efficient lookup (typically O(1)) using hash-based data structures.
   *
   * @param localeSourceProvider   The provider supplying the locale source
   *                               (must not be null)
   * @param translationKeyProvider The provider supplying the translation key
   *                               (must not be null)
   * @return The {@link TranslationMessage} if found; {@code null} otherwise
   * @throws NullPointerException if either provider is null
   * @since 0.1.0
   */
  @Nullable
  TranslationMessage findMessageOrNull(
    @NotNull LocaleSourceProvider localeSourceProvider,
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Searches for a text translation using the specified locale and translation
   * key.
   * <p>This method attempts to locate a specific text translation for the
   * given
   * locale. If no matching text is found, this method returns {@code null}.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * TranslationText text = translationService.findTextOrNull(
   *     userLocaleProvider,
   *     () -> TranslationKey.of("BUTTON_SUBMIT")
   * );
   * if (text != null) {
   *     // Use the text
   * }
   * }</pre>
   * <p><strong>Implementation Note:</strong> Implementations should provide
   * efficient lookup (typically O(1)) using hash-based data structures.
   *
   * @param localeSourceProvider   The provider supplying the locale source
   *                               (must not be null)
   * @param translationKeyProvider The provider supplying the translation key
   *                               (must not be null)
   * @return The {@link TranslationText} if found; {@code null} otherwise
   * @throws NullPointerException if either provider is null
   * @since 0.1.0
   */
  @Nullable
  TranslationText findTextOrNull(
    @NotNull LocaleSourceProvider localeSourceProvider,
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Searches for the translations associated with the specified locale, with
   * fallback to default.
   * <p>This method attempts to locate the translation content for the
   * specified
   * locale. If no matching translations are found, it falls back to the default
   * locale translations.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * // Get translations for user's preferred locale, falling back to default
   * LocaleTranslations translations = translationService.findLocaleTranslationsOrDefault(
   *     userLocaleProvider
   * );
   * }</pre>
   * <p><strong>Important:</strong> If neither the requested locale nor the
   * default locale exists, this method will throw an exception as the contract
   * requires a non-null result.
   *
   * @param localeSourceProvider The provider supplying the locale source (must
   *                             not be null)
   * @return The {@link LocaleTranslations} for the specified locale or the
   * default locale
   * @throws NullPointerException  if localeSourceProvider is null
   * @throws IllegalStateException if no translations are available (neither
   *                               requested nor default)
   * @since 0.1.0
   */
  @NotNull
  LocaleTranslations findLocaleTranslationsOrDefault(
    @NotNull LocaleSourceProvider localeSourceProvider
  );

  /**
   * Searches for a message translation using the specified locale and
   * translation key, with fallback.
   * <p>This method attempts to locate a specific message translation for the
   * given locale. If no matching message is found in the requested locale, it
   * falls back to the default locale. If still not found, it may return a
   * placeholder or throw an exception depending on implementation.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * // Get message with fallback to default locale
   * TranslationMessage message = translationService.findMessageOrDefault(
   *     userLocaleProvider,
   *     () -> TranslationKey.of("WELCOME_MESSAGE")
   * );
   * }</pre>
   * <p><strong>Important:</strong> Unlike {@link #findMessageOrNull}, this
   * method guarantees a non-null result, making it suitable for production code
   * where missing translations must be handled.
   *
   * @param localeSourceProvider   The provider supplying the locale source
   *                               (must not be null)
   * @param translationKeyProvider The provider supplying the translation key
   *                               (must not be null)
   * @return The {@link TranslationMessage} from the requested or default locale
   * @throws NullPointerException           if either provider is null
   * @throws MissingTranslationKeyException if no translation exists in any
   *                                        fallback locale
   * @since 0.1.0
   */
  @NotNull
  TranslationMessage findMessageOrDefault(
    @NotNull LocaleSourceProvider localeSourceProvider,
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Searches for a text translation using the specified locale and translation
   * key, with fallback.
   * <p>This method attempts to locate a specific text translation for the
   * given
   * locale. If no matching text is found in the requested locale, it falls back
   * to the default locale. If still not found, it may return a placeholder or
   * throw an exception depending on implementation.
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * // Get text with fallback to default locale
   * TranslationText text = translationService.findTextOrDefault(
   *     userLocaleProvider,
   *     () -> TranslationKey.of("BUTTON_SUBMIT")
   * );
   * }</pre>
   * <p><strong>Important:</strong> Unlike {@link #findTextOrNull}, this method
   * guarantees a non-null result, making it suitable for production code where
   * missing translations must be handled.
   *
   * @param localeSourceProvider   The provider supplying the locale source
   *                               (must not be null)
   * @param translationKeyProvider The provider supplying the translation key
   *                               (must not be null)
   * @return The {@link TranslationText} from the requested or default locale
   * @throws NullPointerException           if either provider is null
   * @throws MissingTranslationKeyException if no translation exists in any
   *                                        fallback locale
   * @since 0.1.0
   */
  @NotNull
  TranslationText findTextOrDefault(
    @NotNull LocaleSourceProvider localeSourceProvider,
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Returns the total number of locales loaded in the translation system.
   * <p>This method provides a count of all distinct locales for which
   * translations have been loaded. The value is guaranteed to be non-negative.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * int localeCount = translationService.getNumberOfLocales();
   * System.out.println("The application supports " + localeCount + " languages");
   * }</pre>
   *
   * @return The number of locales, ranging from 0 to Integer.MAX_VALUE
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfLocales();

  /**
   * Returns the total number of message translations across all locales.
   * <p>This method provides a count of all {@link TranslationMessage} objects
   * managed by the translation system. The value is guaranteed to be
   * non-negative.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * int messageCount = translationService.getNumberOfMessages();
   * System.out.println("Total message translations: " + messageCount);
   * }</pre>
   *
   * @return The number of message translations, ranging from 0 to
   * Integer.MAX_VALUE
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfMessages();

  /**
   * Returns the total number of text translations across all locales.
   * <p>This method provides a count of all {@link TranslationText} objects
   * managed by the translation system. The value is guaranteed to be
   * non-negative.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * int textCount = translationService.getNumberOfTexts();
   * System.out.println("Total text translations: " + textCount);
   * }</pre>
   *
   * @return The number of text translations, ranging from 0 to
   * Integer.MAX_VALUE
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfTexts();

  /**
   * Clears all translation data from the service, including all locales and
   * their content.
   * <p>This method removes all translation content from the system,
   * effectively
   * resetting it to an empty state. This is typically used when:
   * <ul>
   *   <li>Reloading translations from a data source</li>
   *   <li>Resetting the translation system during testing</li>
   *   <li>Handling dynamic locale updates in development environments</li>
   * </ul>
   * <p><strong>Important:</strong> After calling this method, all previous translation content
   * will be inaccessible through this service instance. Any references to previously retrieved
   * translation objects remain valid but are disconnected from the translation system.
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Clear and reload translations
   * translationService.clear();
   * translationLoader.fill("translations/", translationService);
   * }</pre>
   *
   * @since 0.1.0
   */
  void clear();
}
