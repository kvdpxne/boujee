package me.kvdpxne.boujee;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Functional interface for providing translation keys in a flexible manner.
 *
 * <p>This interface enables the framework to work with translation keys without requiring direct
 * references to specific key instances. It follows the provider pattern to allow for:
 * <ul>
 *   <li>Dynamic key resolution based on runtime conditions</li>
 *   <li>Lazy initialization of keys</li>
 *   <li>Integration with dependency injection frameworks</li>
 *   <li>Testing with mock key providers</li>
 * </ul>
 *
 * <p>As a functional interface, it can be implemented using lambda expressions, method references,
 * or traditional anonymous/inner classes, making it highly adaptable to different application
 * architectures.
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Lambda implementation
 * TranslationKeyProvider welcomeProvider = () -> TranslationKey.of("WELCOME_MESSAGE");
 *
 * // Method reference implementation
 * public class TranslationKeys {
 *     public static TranslationKey welcomeMessage() {
 *         return TranslationKey.of("WELCOME_MESSAGE");
 *     }
 * }
 * TranslationKeyProvider provider = TranslationKeys::welcomeMessage;
 *
 * // Using with the translation service
 * TranslationText text = translationService.findTextOrDefault(
 *     localeProvider,
 *     welcomeProvider
 * );
 * }</pre>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is a functional interface with a single abstract method {@link #getTranslationKey()}</li>
 *   <li>Implementations should be thread-safe as they may be accessed concurrently</li>
 *   <li>The returned {@code TranslationKey} should be immutable and consistent across calls</li>
 *   <li>Providers may cache results for performance, but should handle key lifecycle appropriately</li>
 * </ul>
 *
 * <p><strong>Design Rationale:</strong> The provider pattern decouples the declaration of what
 * translation is needed from the mechanism of how it's obtained, enabling more flexible and
 * testable code.
 *
 * @see TranslationKey
 * @see java.util.function.Supplier
 * @since 0.1.0
 */
@FunctionalInterface
public interface TranslationKeyProvider {

  /**
   * Provides a translation key instance.
   *
   * <p>This method is the core contract of the interface, allowing consumers to obtain a translation
   * key without knowing the specific implementation details. The returned key should remain consistent
   * for the lifetime of the provider instance unless explicitly designed to change.
   *
   * <p><strong>Important:</strong> Implementations must ensure the returned {@code TranslationKey}
   * is unmodifiable as indicated by the {@code @Unmodifiable} annotation. This guarantees that the
   * key cannot be altered after retrieval, maintaining the integrity of the translation system.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * TranslationKeyProvider provider = () -> TranslationKey.of("ERROR_INVALID_INPUT");
   * TranslationKey key = provider.getTranslationKey();
   * System.out.println("Key ordinal: " + key.getOrdinalNumber());
   * }</pre>
   *
   * @return An unmodifiable {@link TranslationKey} instance (never null)
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  TranslationKey getTranslationKey();
}
