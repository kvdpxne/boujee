package me.kvdpxne.boujee.locale;

import java.util.Locale;

/**
 * @since 0.1.0
 */
public interface LocaleSource
  extends LocaleSourceProvider {

  /**
   * @since 0.1.0
   */
  String getLocalization();

  /**
   * @since 0.1.0
   */
  Locale getLocale();
}
