package me.kvdpxne.boujee.example;

import me.kvdpxne.boujee.locale.BasicLocaleSource;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.Locales;

public enum EnumSupportedLocale
  implements LocaleSourceProvider {

  EN_US,
  PL_PL;

  @Override
  public LocaleSource getLocaleSource() {
    return new BasicLocaleSource(
      Locales.fromString(this.name())
    );
  }
}
