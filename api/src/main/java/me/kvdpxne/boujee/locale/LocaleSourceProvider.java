package me.kvdpxne.boujee.locale;

/**
 * Functional interface that defines a provider for {@link LocaleSource}
 * instances.
 * <p>
 * This interface allows for a lambda-friendly and concise way to supply
 * {@link LocaleSource} objects.
 *
 * @since 0.1.0
 */
@FunctionalInterface
public interface LocaleSourceProvider {

  /**
   * Provides an instance of {@link LocaleSource}.
   * <p>
   * The returned instance can then be used to access localization and locale
   * details.
   *
   * @return an instance of {@link LocaleSource}.
   * @since 0.1.0
   */
  LocaleSource getLocaleSource();
}
