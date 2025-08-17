package me.kvdpxne.boujee.locale;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Represents a locale source that provides localization information.
 * <p>
 * This implementation efficiently caches the parsed Locale object while ensuring
 * thread safety in high-concurrency environments typical for multiplayer servers.
 *
 * @since 0.1.0
 */
public class BasicLocaleSource implements LocaleSource {

  /**
   * The raw localization string (e.g., "en_US", "pl-PL").
   */
  private final String localization;

  /**
   * Thread-safe cache for the parsed Locale object.
   * <p>
   * Using AtomicReference instead of WeakReference for better performance in
   * multiplayer environments where locales are frequently accessed.
   */
  private transient final AtomicReference<Locale> localeCache =
    new AtomicReference<>(null);

  /**
   * Constructs a new BasicLocaleSource with the specified localization string.
   *
   * @param localization the localization string (e.g., "en_US", "pl-PL")
   * @throws NullPointerException if localization is null
   * @since 0.1.0
   */
  public BasicLocaleSource(final String localization) {
    if (localization == null) {
      throw new NullPointerException("Localization string must not be null");
    }
    this.localization = localization;
  }

  /**
   * Constructs a new BasicLocaleSource with the specified Locale object.
   *
   * @param locale the Locale object to use
   * @throws NullPointerException if locale is null
   * @since 0.1.0
   */
  public BasicLocaleSource(final Locale locale) {
    if (locale == null) {
      throw new NullPointerException("Locale must not be null");
    }
    this.localization = locale.toString();
    this.localeCache.set(locale);
  }

  @NotNull
  @Unmodifiable
  @Override
  public String getLocalization() {
    return this.localization;
  }

  @NotNull
  @Unmodifiable
  @Override
  public Locale getLocale() {
    // Try fast path first (cache hit)
    Locale cachedLocale = localeCache.get();
    if (cachedLocale != null) {
      return cachedLocale;
    }

    // Double-checked locking pattern for thread-safe initialization
    synchronized (this) {
      cachedLocale = localeCache.get();
      if (cachedLocale != null) {
        return cachedLocale;
      }

      try {
        cachedLocale = Locales.fromString(this.localization);
        localeCache.set(cachedLocale);
        return cachedLocale;
      } catch (Exception e) {
        throw new IllegalArgumentException(
          "Invalid locale format: " + this.localization, e);
      }
    }
  }

  /**
   * Clears the cached Locale object.
   * <p>
   * This method is primarily for internal use when locale data might change
   * significantly (rare in most applications).
   *
   * @since 0.1.0
   */
  public void invalidateCache() {
    localeCache.set(null);
  }

  @Override
  public LocaleSource getLocaleSource() {
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BasicLocaleSource that = (BasicLocaleSource) o;
    return localization.equals(that.localization);
  }

  @Override
  public int hashCode() {
    return localization.hashCode();
  }

  @Override
  public String toString() {
    return "BasicLocaleSource{" +
      "localization='" + localization + '\'' +
      '}';
  }
}