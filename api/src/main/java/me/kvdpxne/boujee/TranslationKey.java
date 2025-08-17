package me.kvdpxne.boujee;

import java.io.Serializable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a unique identifier for translatable content within the Boujee Translation Framework.
 *
 * <p>This interface defines the contract for objects that serve as keys to retrieve specific
 * translations from the translation system. Each translation key uniquely identifies a particular
 * piece of translatable content, regardless of locale or content type.
 *
 * <p>Key characteristics of translation keys:
 * <ul>
 *   <li>Immutable and thread-safe</li>
 *   <li>Serializable for distributed systems and caching</li>
 *   <li>Equatable for efficient comparison and lookup</li>
 *   <li>Assignable a unique ordinal number for performance-sensitive operations</li>
 * </ul>
 *
 * <p><strong>Usage Context:</strong> Translation keys are used throughout the framework to:
 * <ul>
 *   <li>Retrieve specific translations from a locale</li>
 *   <li>Register new translations with the system</li>
 *   <li>Identify missing translations during development</li>
 *   <li>Provide fallback mechanisms when translations are unavailable</li>
 * </ul>
 *
 * <p><strong>Implementation Note:</strong> While applications can create custom implementations,
 * the framework provides {@link DefaultTranslationKey} as a standard implementation that should
 * suffice for most use cases.
 *
 * <p><strong>Example:</strong>
 * <pre>{@code
 * // Creating a key
 * TranslationKey welcomeKey = TranslationKey.of("WELCOME_MESSAGE");
 *
 * // Using a key to retrieve a translation
 * TranslationText welcomeText = translationService.findTextOrDefault(
 *     userLocaleProvider,
 *     () -> welcomeKey
 * );
 * }</pre>
 *
 * @see DefaultTranslationKey
 * @see TranslationKeyProvider
 * @see me.kvdpxne.boujee.TranslationService
 * @since 0.1.0
 */
@Unmodifiable
public interface TranslationKey
  extends TranslationKeyProvider, Serializable {

  /**
   * Returns the ordinal number of this translation key.
   *
   * <p>The ordinal number is a unique, non-negative integer assigned to each key. It provides
   * an efficient way to compare keys without string operations and is suitable for serialization
   * and storage in performance-sensitive contexts.
   *
   * <p><strong>Important:</strong> The ordinal number has no semantic meaning and should not be
   * used for sorting or any purpose other than identification and comparison. Ordinal numbers
   * are assigned sequentially but the sequence may have gaps.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * TranslationKey key = TranslationKey.of("EXAMPLE_KEY");
   * int ordinal = key.getOrdinalNumber();
   * System.out.println("Key ordinal: " + ordinal);
   * }</pre>
   *
   * @return a non-negative integer representing the ordinal number of this translation key
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getOrdinalNumber();
}
