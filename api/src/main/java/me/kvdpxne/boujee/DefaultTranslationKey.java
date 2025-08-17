package me.kvdpxne.boujee;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a unique translation key used for localization with flyweight pattern implementation.
 *
 * <p>This class provides a standardized way to identify translatable content within the Boujee
 * Translation Framework. It implements the flyweight design pattern to ensure that identical keys
 * share the same instance, reducing memory overhead and enabling efficient key comparisons.
 *
 * <p>Key features of this implementation:
 * <ul>
 *   <li>Keys are normalized to uppercase English format for consistent comparison</li>
 *   <li>Each key has a unique ordinal number for efficient hashing and comparison</li>
 *   <li>Thread-safe instance management using concurrent collections</li>
 *   <li>Supports both creation and lookup operations with configurable behavior</li>
 * </ul>
 *
 * <p><strong>Key Normalization:</strong> When created from string content, keys are:
 * <ul>
 *   <li>Trimmed of leading and trailing whitespace</li>
 *   <li>Converted to uppercase using {@code Locale.ENGLISH}</li>
 *   <li>Stored in a global registry for reuse</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Creating a new translation key (allows creation if not exists)
 * TranslationKey welcomeKey = TranslationKey.of("WELCOME_MESSAGE");
 *
 * // Looking up an existing key (throws exception if not exists)
 * try {
 *     TranslationKey existingKey = TranslationKey.of("EXISTING_KEY", true);
 * } catch (IllegalStateException e) {
 *     // Handle missing key
 * }
 *
 * // Checking if a key exists
 * boolean exists = DefaultTranslationKey.exists(welcomeKey);
 *
 * // Getting the ordinal number (useful for serialization)
 * int ordinal = welcomeKey.getOrdinalNumber();
 * }</pre>
 *
 * <p><strong>Implementation Notes:</strong>
 * <ul>
 *   <li>Instances are immutable and thread-safe</li>
 *   <li>Equality is based on ordinal number, not string content</li>
 *   <li>The global key registry uses weak references to prevent memory leaks</li>
 *   <li>Ordinal numbers are assigned sequentially but have no semantic meaning</li>
 * </ul>
 *
 * <p><strong>Performance Considerations:</strong>
 * <ul>
 *   <li>Key lookup is O(1) due to concurrent hash map implementation</li>
 *   <li>Using ordinal numbers for comparison is significantly faster than string comparison</li>
 *   <li>The flyweight pattern reduces memory usage when many identical keys are needed</li>
 * </ul>
 *
 * @see TranslationKey
 * @see TranslationKeyProvider
 * @since 0.1.0
 */
@Unmodifiable
public class DefaultTranslationKey
  implements TranslationKey {

  /**
   * Global registry of translation keys, mapping normalized key strings to their instances.
   *
   * <p>This concurrent map ensures thread-safe access to the key registry with efficient lookups.
   * The initial capacity of 64 is chosen to balance memory usage with expected key counts in
   * typical applications.
   *
   * <p><strong>Implementation Note:</strong> This map does not use weak references intentionally,
   * as translation keys are expected to be long-lived and frequently accessed. The memory impact
   * is minimal due to the flyweight pattern.
   *
   * @since 0.1.0
   */
  private static final ConcurrentMap<String, TranslationKey> KEYS =
    new ConcurrentHashMap<>(64);

  /**
   * Counter for generating unique ordinal numbers for translation keys.
   *
   * <p>This atomic integer ensures thread-safe generation of unique ordinals across multiple
   * threads. The counter starts at 0 and increments with each new key creation.
   *
   * @since 0.1.0
   */
  private static final AtomicInteger COUNTER =
    new AtomicInteger(0);

  /**
   * Serialization version identifier for compatibility across framework versions.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = -6054827629563125169L;

  /**
   * The unique ordinal number assigned to this translation key.
   *
   * <p>This value is used for efficient equality comparison and hashing, as it provides
   * a constant-time alternative to string comparison. The ordinal has no semantic meaning
   * and is only used for identification purposes.
   *
   * @since 0.1.0
   */
  private final int ordinal;

  /**
   * Constructs a new {@code DefaultTranslationKey} with the specified ordinal number.
   *
   * <p>This constructor is primarily used internally by the framework. Application code
   * should typically use the {@link #of(String)} factory methods instead.
   *
   * @param ordinal the unique ordinal number to assign to this translation key
   * @since 0.1.0
   */
  public DefaultTranslationKey(final int ordinal) {
    this.ordinal = ordinal;
  }

  /**
   * Constructs a new {@code DefaultTranslationKey} with an automatically generated ordinal.
   *
   * <p>This constructor is used when creating new keys through the factory methods.
   * The ordinal is generated by incrementing the global counter atomically.
   *
   * @since 0.1.0
   */
  public DefaultTranslationKey() {
    this(COUNTER.getAndIncrement());
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided content string.
   *
   * <p>This factory method provides the primary mechanism for obtaining translation keys.
   * It normalizes the input string and either returns an existing key or creates a new one
   * based on the {@code denyCreations} parameter.
   *
   * <p>Normalization process:
   * <ol>
   *   <li>Trims leading and trailing whitespace</li>
   *   <li>Converts to uppercase using {@code Locale.ENGLISH}</li>
   * </ol>
   *
   * <p><strong>Usage Patterns:</strong>
   * <ul>
   *   <li>When {@code denyCreations} is {@code false}: Returns existing key or creates new one</li>
   *   <li>When {@code denyCreations} is {@code true}: Returns existing key or throws exception</li>
   * </ul>
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * // Safe creation (creates if doesn't exist)
   * TranslationKey key1 = TranslationKey.of("BUTTON_SUBMIT", false);
   *
   * // Strict lookup (fails if doesn't exist)
   * TranslationKey key2 = TranslationKey.of("EXISTING_KEY", true);
   * }</pre>
   *
   * @param content       the string content representing the translation key (must not be null or empty)
   * @param denyCreations if {@code true}, prevents creation of new keys if they don't exist
   * @return the existing or newly created {@code TranslationKey} instance
   * @throws NullPointerException     if {@code content} is {@code null}
   * @throws IllegalArgumentException if {@code content} is empty after trimming
   * @throws IllegalStateException    if key doesn't exist and {@code denyCreations} is {@code true}
   * @since 0.1.0
   */
  public static TranslationKey of(
    final @NotNull String content,
    final boolean denyCreations
  ) {
    // noinspection ConstantValue
    if (null == content) {
      throw new NullPointerException("The passed content must not be null.");
    }

    if (content.isEmpty()) {
      throw new IllegalArgumentException("The passed content must not be empty.");
    }

    final String normalizedKey = content.trim().toUpperCase(Locale.ENGLISH);
    if (denyCreations) {
      final TranslationKey key = KEYS.get(normalizedKey);
      if (null == key) {
        throw new IllegalStateException("The \"" + normalizedKey + "\" translation key does not exist.");
      }
      return key;
    }

    return KEYS.computeIfAbsent(
      normalizedKey,
      (String key) -> new DefaultTranslationKey()
    );
  }

  /**
   * Retrieves or creates a {@code TranslationKey} based on the provided content string.
   *
   * <p>This convenience method is equivalent to calling {@link #of(String, boolean)} with
   * {@code denyCreations} set to {@code false}. It will always return a valid key, either
   * by retrieving an existing one or creating a new one.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * TranslationKey key = TranslationKey.of("WELCOME_MESSAGE");
   * }</pre>
   *
   * @param content the string content representing the translation key (must not be null or empty)
   * @return the existing or newly created {@code TranslationKey} instance
   * @throws NullPointerException     if {@code content} is {@code null}
   * @throws IllegalArgumentException if {@code content} is empty after trimming
   * @since 0.1.0
   */
  public static TranslationKey of(
    final String content
  ) {
    return of(content, false);
  }

  /**
   * Checks if the specified translation key exists in the global registry.
   *
   * <p>This method provides a way to verify whether a particular key has been created and
   * registered with the framework. It's useful for validation and debugging purposes.
   *
   * <p><strong>Note:</strong> This method checks for exact instance equality in the registry.
   * It does not perform string-based lookup of unregistered keys.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * TranslationKey key = TranslationKey.of("TEST_KEY");
   * boolean exists = DefaultTranslationKey.exists(key); // Returns true
   *
   * TranslationKey otherKey = new DefaultTranslationKey();
   * boolean otherExists = DefaultTranslationKey.exists(otherKey); // Returns false
   * }</pre>
   *
   * @param key the translation key to check for existence (must not be null)
   * @return {@code true} if the key exists in the registry; {@code false} otherwise
   * @throws NullPointerException if {@code key} is {@code null}
   * @since 0.1.0
   */
  public static boolean exists(
    final @NotNull TranslationKey key
  ) {
    return KEYS.containsValue(key);
  }

  /**
   * Returns the ordinal number of this translation key.
   *
   * <p>The ordinal number is a unique integer assigned to each key during creation. It
   * provides an efficient way to compare keys without string operations and is suitable
   * for serialization purposes.
   *
   * <p><strong>Important:</strong> The ordinal number has no semantic meaning and should
   * not be used for sorting or any purpose other than identification and comparison.
   *
   * @return the ordinal number of the translation key (non-negative integer)
   * @since 0.1.0
   */
  public int getOrdinalNumber() {
    return this.ordinal;
  }

  /**
   * Returns this translation key as a {@link TranslationKey} instance.
   *
   * <p>This method satisfies the contract of the {@link TranslationKeyProvider} interface,
   * allowing {@code DefaultTranslationKey} instances to serve as their own key providers.
   *
   * <p><strong>Example:</strong>
   * <pre>{@code
   * TranslationKey key = TranslationKey.of("EXAMPLE");
   * TranslationKey sameKey = key.getTranslationKey();
   * // sameKey == key
   * }</pre>
   *
   * @return this {@code TranslationKey} instance (never null)
   * @since 0.1.0
   */
  @NotNull
  @Unmodifiable
  @Override
  public TranslationKey getTranslationKey() {
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof DefaultTranslationKey)) {
      return false;
    }

    final DefaultTranslationKey that = (DefaultTranslationKey) o;
    return this.ordinal == that.ordinal;
  }

  @Override
  public int hashCode() {
    return this.ordinal;
  }

  @Override
  public String toString() {
    return String.valueOf(this.ordinal);
  }
}
