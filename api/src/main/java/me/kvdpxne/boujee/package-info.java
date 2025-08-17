/**
 * Provides core translation management components for the Boujee Translation Framework.
 *
 * <p>This package contains the central interfaces and implementations for managing translation keys,
 * translation services, and the overall translation lifecycle. It serves as the primary entry point
 * for applications using the Boujee Translation Framework to handle internationalization and
 * localization requirements.
 *
 * <p>The key components of this package include:
 * <ul>
 *   <li>{@link TranslationKey} - Represents unique identifiers for translatable content</li>
 *   <li>{@link DefaultTranslationKey} - Standard implementation of translation keys with flyweight pattern</li>
 *   <li>{@link TranslationKeyProvider} - Functional interface for supplying translation keys</li>
 *   <li>{@link TranslationService} - Central interface for managing all translation operations</li>
 * </ul>
 *
 * <p>Core architectural principles of this package:
 * <ul>
 *   <li><strong>Key Management:</strong> Translation keys are managed as flyweight objects to ensure
 *       uniqueness and efficient memory usage</li>
 *   <li><strong>Service-Oriented Design:</strong> The {@code TranslationService} provides a unified API
 *       for all translation operations</li>
 *   <li><strong>Null Safety:</strong> Comprehensive null-safe access patterns with both "orNull" and
 *       "orElseDefault" methods</li>
 *   <li><strong>Extensibility:</strong> Functional interfaces enable flexible integration with
 *       application-specific requirements</li>
 * </ul>
 *
 * <p>This package works in conjunction with:
 * <ul>
 *   <li>{@code me.kvdpxne.boujee.content} - For representing the actual translatable content</li>
 *   <li>{@code me.kvdpxne.boujee.locale} - For locale-specific translation management</li>
 *   <li>{@code me.kvdpxne.boujee.exceptions} - For translation-related exception handling</li>
 *   <li>{@code me.kvdpxne.boujee.io} - For translation resource loading</li>
 * </ul>
 *
 * <p><strong>Usage Pattern:</strong> Applications typically interact with the framework through the
 * {@code TranslationService} interface, which provides methods to:
 * <ul>
 *   <li>Retrieve translations for specific locales</li>
 *   <li>Manage the set of available locales</li>
 *   <li>Access translation content with appropriate fallback mechanisms</li>
 *   <li>Monitor translation statistics and metrics</li>
 * </ul>
 *
 * @see me.kvdpxne.boujee.content
 * @see me.kvdpxne.boujee.locale
 * @see me.kvdpxne.boujee.exceptions
 * @see me.kvdpxne.boujee.io
 * @since 0.1.0
 */
package me.kvdpxne.boujee;