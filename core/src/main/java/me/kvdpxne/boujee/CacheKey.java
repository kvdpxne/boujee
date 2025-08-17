package me.kvdpxne.boujee;

import me.kvdpxne.boujee.locale.LocaleSource;

final class CacheKey {

  private final int localeHash;
  private final int translationKeyHash;
  private final boolean isMessage;

  CacheKey(
    final LocaleSource localeSource,
    final TranslationKey translationKey,
    final boolean isMessage
  ) {
    // Using hash codes instead of full objects to reduce memory footprint
    this.localeHash = localeSource.hashCode();
    this.translationKeyHash = translationKey.hashCode();
    this.isMessage = isMessage;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final CacheKey cacheKey = (CacheKey) o;
    return localeHash == cacheKey.localeHash &&
      translationKeyHash == cacheKey.translationKeyHash &&
      isMessage == cacheKey.isMessage;
  }

  @Override
  public int hashCode() {
    // Combine hash codes efficiently
    return (localeHash * 31 + translationKeyHash) * 31 + (isMessage ? 1 : 0);
  }
}
