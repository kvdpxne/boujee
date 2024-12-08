package me.kvdpxne.boujee.locale;

/**
 * @since 0.1.0
 */
@FunctionalInterface
public interface LocaleSourceProvider {

  /**
   * @since 0.1.0
   */
  LocaleSource getLocaleSource();
}
