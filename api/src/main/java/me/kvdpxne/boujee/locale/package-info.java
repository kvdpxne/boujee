/**
 * Provides core interfaces for managing locale-specific translations and localization sources within the Boujee Translation Framework.
 *
 * <p>This package defines the infrastructure for handling different locales, their associated translations, and the mechanisms
 * for retrieving locale-specific content. It establishes the relationship between locale identifiers, translation sources,
 * and the actual translated content.
 *
 * <p>The key components of this package include:
 * <ul>
 *   <li>{@link LocaleSource} - Represents a specific locale with its associated identifier and {@link java.util.Locale} object</li>
 *   <li>{@link LocaleSourceProvider} - Functional interface for providing locale sources in a flexible manner</li>
 *   <li>{@link LocaleTranslations} - Manages collections of translated content for a specific locale</li>
 * </ul>
 *
 * <p>Core design principles of this package:
 * <ul>
 *   <li><strong>Immutability:</strong> Locale sources are designed to be unmodifiable once created</li>
 *   <li><strong>Extensibility:</strong> The provider pattern allows for flexible locale resolution strategies</li>
 *   <li><strong>Organization:</strong> Translations are organized by locale and by content type (messages vs. texts)</li>
 *   <li><strong>Efficiency:</strong> Optimized for quick lookup of translation content by key</li>
 * </ul>
 *
 * <p>This package works in conjunction with:
 * <ul>
 *   <li>{@code me.kvdpxne.boujee.content} - For representing the actual translatable content</li>
 *   <li>{@code me.kvdpxne.boujee} - For translation keys and the main translation service</li>
 *   <li>{@code me.kvdpxne.boujee.exceptions} - For locale-related exception handling</li>
 * </ul>
 *
 * <p><strong>Usage Pattern:</strong> Applications typically use this package through the {@link me.kvdpxne.boujee.TranslationService}
 * interface, which manages collections of {@code LocaleTranslations} objects. Direct usage might involve:
 * <ul>
 *   <li>Creating locale sources for specific language/country combinations</li>
 *   <li>Registering translation content for specific locales</li>
 *   <li>Retrieving localized content based on user preferences</li>
 * </ul>
 *
 * @see me.kvdpxne.boujee.content
 * @see me.kvdpxne.boujee.TranslationService
 * @see me.kvdpxne.boujee.exceptions
 * @since 0.1.0
 */
package me.kvdpxne.boujee.locale;