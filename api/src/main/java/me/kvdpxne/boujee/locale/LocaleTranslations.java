package me.kvdpxne.boujee.locale;

import java.util.Collection;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Manages collections of locale-specific translations for a particular locale.
 *
 * <p>This interface provides a comprehensive API for accessing, searching, and managing translated content
 * associated with a specific {@link LocaleSource}. It serves as the central repository for all translations
 * within a given locale, organizing them by content type (messages and texts).
 *
 * <p>The key responsibilities of this interface include:
 * <ul>
 *   <li>Storing and retrieving message translations (multi-segment content)</li>
 *   <li>Storing and retrieving text translations (single-segment content)</li>
 *   <li>Providing efficient lookup of translations by key</li>
 *   <li>Maintaining counts of translation entries for monitoring and diagnostics</li>
 *   <li>Supporting bulk operations for translation management</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Working with locale translations
 * LocaleTranslations enUsTranslations = ...; // Obtained from TranslationService
 *
 * // Get all messages for this locale
 * Collection<TranslationMessage> messages = enUsTranslations.getMessages();
 *
 * // Find a specific message by key
 * TranslationMessage greeting = enUsTranslations.findMessageOrNull(
 *     () -> TranslationKey.of("WELCOME_MESSAGE"));
 *
 * // Get count statistics
 * int messageCount = enUsTranslations.getNumberOfMessages();
 * int textCount = enUsTranslations.getNumberOfTexts();
 *
 * // Clear all translations (typically used during reload operations)
 * enUsTranslations.clear();
 * }</pre>
 *
 * <p><strong>Design Rationale:</strong>
 * <ul>
 *   <li><strong>Separation of content types:</strong> Messages and texts are separated to optimize for their
 *       different use cases (multi-segment vs. single-segment content)</li>
 *   <li><strong>Null-safe access:</strong> Provides both "orNull" and "orElseDefault" access patterns to handle
 *       missing translations appropriately</li>
 *   <li><strong>Immutability for views:</strong> Collection views are unmodifiable to prevent accidental modification</li>
 *   <li><strong>Efficient lookups:</strong> Implementations are expected to use efficient data structures for
 *       key-based lookups</li>
 * </ul>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>All collection views should be unmodifiable as indicated by {@code @UnmodifiableView}</li>
 *   <li>The interface itself is marked as {@code @Unmodifiable} to indicate instances should not change state</li>
 *   <li>Implementations should optimize for read-heavy workloads typical in translation systems</li>
 *   <li>Thread safety is essential as multiple threads may access translations concurrently</li>
 * </ul>
 *
 * @see LocaleSource
 * @see TranslationMessage
 * @see TranslationText
 * @see me.kvdpxne.boujee.TranslationService
 * @since 0.1.0
 */
@Unmodifiable
public interface LocaleTranslations {

  /**
   * Retrieves the {@link LocaleSource} associated with this set of translations.
   *
   * <p>This method returns the specific locale configuration that these translations are targeted for.
   * It provides the context needed to understand which language, country, and variant these translations
   * apply to.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * LocaleTranslations translations = ...;
   * System.out.println("Translations for: " + translations.getLocaleSource().getLocalization());
   * }</pre>
   *
   * @return The {@link LocaleSource} representing the locale configuration for these translations (never null)
   * @since 0.1.0
   */
  @NotNull
  LocaleSource getLocaleSource();

  /**
   * Retrieves all {@link TranslationMessage} objects associated with this locale.
   *
   * <p>Translation messages represent multi-segment translatable content, typically used for:
   * <ul>
   *   <li>User interface messages that span multiple lines</li>
   *   <li>Formatted content requiring segmentation</li>
   *   <li>Messages that need to be processed line-by-line</li>
   * </ul>
   *
   * <p><strong>Important:</strong> The returned collection is an unmodifiable view of the internal storage
   * as indicated by the {@code @UnmodifiableView} annotation. This prevents accidental modification of
   * the translation repository.
   *
   * <p><strong>Performance Note:</strong> For large translation sets, consider using the key-based lookup
   * methods rather than iterating through all messages.
   *
   * @return An unmodifiable view of the collection containing all message translations for this locale
   * @since 0.1.0
   */
  @NotNull
  @UnmodifiableView
  Collection<@NotNull TranslationMessage> getMessages();

  /**
   * Retrieves all {@link TranslationText} objects associated with this locale.
   *
   * <p>Translation texts represent single-segment translatable content, typically used for:
   * <ul>
   *   <li>User interface labels and captions</li>
   *   <li>Single-line notifications</li>
   *   <li>Simple text elements without segmentation requirements</li>
   * </ul>
   *
   * <p><strong>Important:</strong> The returned collection is an unmodifiable view of the internal storage
   * as indicated by the {@code @UnmodifiableView} annotation. This prevents accidental modification of
   * the translation repository.
   *
   * <p><strong>Performance Note:</strong> For large translation sets, consider using the key-based lookup
   * methods rather than iterating through all texts.
   *
   * @return An unmodifiable view of the collection containing all text translations for this locale
   * @since 0.1.0
   */
  @NotNull
  @UnmodifiableView
  Collection<@NotNull TranslationText> getTexts();

  /**
   * Searches for a {@link TranslationMessage} using the provided {@link TranslationKeyProvider}.
   *
   * <p>This method attempts to locate a specific message translation based on the key provided by the
   * key provider. If no matching message is found, this method returns {@code null}.
   *
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * TranslationMessage message = localeTranslations.findMessageOrNull(
   *     () -> TranslationKey.of("WELCOME_MESSAGE"));
   * if (message != null) {
   *     // Use the message
   * }
   * }</pre>
   *
   * <p><strong>Implementation Note:</strong> Implementations should provide efficient lookup (typically O(1))
   * using a hash-based data structure.
   *
   * @param translationKeyProvider The provider supplying the translation key to search for (must not be null)
   * @return The {@link TranslationMessage} if found; {@code null} otherwise
   * @throws NullPointerException if translationKeyProvider is null
   * @since 0.1.0
   */
  @Nullable
  TranslationMessage findMessageOrNull(
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Searches for a {@link TranslationText} using the provided {@link TranslationKeyProvider}.
   *
   * <p>This method attempts to locate a specific text translation based on the key provided by the
   * key provider. If no matching text is found, this method returns {@code null}.
   *
   * <p><strong>Usage Pattern:</strong>
   * <pre>{@code
   * TranslationText text = localeTranslations.findTextOrNull(
   *     () -> TranslationKey.of("BUTTON_SUBMIT"));
   * if (text != null) {
   *     // Use the text
   * }
   * }</pre>
   *
   * <p><strong>Implementation Note:</strong> Implementations should provide efficient lookup (typically O(1))
   * using a hash-based data structure.
   *
   * @param translationKeyProvider The provider supplying the translation key to search for (must not be null)
   * @return The {@link TranslationText} if found; {@code null} otherwise
   * @throws NullPointerException if translationKeyProvider is null
   * @since 0.1.0
   */
  @Nullable
  TranslationText findTextOrNull(
    @NotNull TranslationKeyProvider translationKeyProvider
  );

  /**
   * Returns the number of message translations stored in this locale translations object.
   *
   * <p>This method provides a count of all {@link TranslationMessage} objects managed by this instance.
   * The value is guaranteed to be non-negative.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * System.out.println("This locale has " +
   *     localeTranslations.getNumberOfMessages() + " message translations");
   * }</pre>
   *
   * @return The number of message translations, ranging from 0 to Integer.MAX_VALUE
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfMessages();

  /**
   * Returns the number of text translations stored in this locale translations object.
   *
   * <p>This method provides a count of all {@link TranslationText} objects managed by this instance.
   * The value is guaranteed to be non-negative.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * System.out.println("This locale has " +
   *     localeTranslations.getNumberOfTexts() + " text translations");
   * }</pre>
   *
   * @return The number of text translations, ranging from 0 to Integer.MAX_VALUE
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfTexts();

  /**
   * Clears all stored translations, including messages and texts, associated with this locale.
   *
   * <p>This method removes all translation content from this instance, effectively resetting it to
   * an empty state. This is typically used when:
   * <ul>
   *   <li>Reloading translations from a data source</li>
   *   <li>Resetting the translation system during testing</li>
   *   <li>Handling dynamic locale updates</li>
   * </ul>
   *
   * <p><strong>Important:</strong> After calling this method, all previous translation content will be
   * inaccessible through this instance. Any references to previously retrieved translation objects
   * remain valid but are disconnected from the translation system.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Clear and reload translations
   * localeTranslations.clear();
   * translationLoader.loadInto(localeTranslations);
   * }</pre>
   *
   * @since 0.1.0
   */
  void clear();
}
