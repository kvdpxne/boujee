package me.kvdpxne.boujee.locale;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Functional interface that defines a provider for {@link LocaleSource} instances.
 *
 * <p>This interface enables flexible strategies for supplying locale information to the translation framework.
 * As a functional interface, it can be implemented using lambda expressions, method references, or traditional
 * anonymous/inner classes, making it highly adaptable to different application architectures.
 *
 * <p>The primary purpose of this interface is to decouple the retrieval of locale information from its usage,
 * allowing for:
 * <ul>
 *   <li>Dynamic locale resolution based on user preferences</li>
 *   <li>Context-specific locale selection (e.g., per request, per user session)</li>
 *   <li>Testing with mock locale sources</li>
 *   <li>Integration with external localization systems</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Lambda implementation for a specific locale
 * LocaleSourceProvider enUsProvider = () ->
 *     new DefaultLocaleSource("en-US", Locale.US);
 *
 * // Method reference implementation
 * public class UserContext {
 *     public LocaleSource getCurrentLocale() { ... }
 * }
 * UserContext context = ...;
 * LocaleSourceProvider userLocaleProvider = context::getCurrentLocale;
 * }</pre>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>This is a functional interface with a single abstract method {@link #getLocaleSource()}</li>
 *   <li>Implementations should be thread-safe as they may be accessed concurrently</li>
 *   <li>The returned {@code LocaleSource} should be immutable to maintain consistency</li>
 *   <li>Providers may cache results for performance, but should handle locale changes appropriately</li>
 * </ul>
 *
 * @see LocaleSource
 * @see java.util.function.Supplier
 * @since 0.1.0
 */
@FunctionalInterface
public interface LocaleSourceProvider {

  /**
   * Provides an instance of {@link LocaleSource} representing a specific locale configuration.
   *
   * <p>This method is the core contract of the interface, allowing consumers to obtain locale information
   * without knowing the specific implementation details. The returned locale source should remain consistent
   * for the lifetime of the provider instance unless explicitly designed to change.
   *
   * <p><strong>Important:</strong> Implementations must ensure the returned {@code LocaleSource} is unmodifiable
   * as indicated by the {@code @Unmodifiable} annotation. This guarantees that the locale information cannot
   * be altered after retrieval, maintaining the integrity of the translation process.
   *
   * @return An unmodifiable {@link LocaleSource} instance representing the locale configuration
   * @throws RuntimeException Any runtime exception that might occur during locale source retrieval
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  LocaleSource getLocaleSource();
}
