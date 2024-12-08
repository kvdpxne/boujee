package me.kvdpxne.boujee.locale;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * @since 0.1.0
 */
public class BasicLocaleSource
  implements LocaleSource {

  /**
   * @since 0.1.0
   */
  private final String localization;

  /**
   * @since 0.1.0
   */
  private volatile Reference<Locale> localeReference;

  /**
   * @since 0.1.0
   */
  public BasicLocaleSource(final String localization) {
    this.localization = localization;
  }

  /**
   * @since 0.1.0
   */
  public BasicLocaleSource(final Locale locale) {
    this.localization = locale.toString();
    this.localeReference = new WeakReference<>(locale);
  }

  @Override
  public String getLocalization() {
    return this.localization;
  }

  @Override
  public Locale getLocale() {
    Reference<Locale> reference = this.localeReference;
    if (null == reference || null == reference.get()) {
      synchronized (this) {
        reference = this.localeReference;
        if (null == reference || null == reference.get()) {
          final Locale referent = Locales.fromString(this.localization);
          this.localeReference = new WeakReference<>(referent);
          return referent;
        }
      }
    }
    return reference.get();
  }

  public synchronized void clear() {
    this.localeReference = null;
  }

  @Override
  public LocaleSource getLocaleSource() {
    return this;
  }
}
