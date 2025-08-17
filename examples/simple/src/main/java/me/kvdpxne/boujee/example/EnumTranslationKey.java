package me.kvdpxne.boujee.example;

import me.kvdpxne.boujee.DefaultTranslationKey;
import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.TranslationKeyProvider;

public enum EnumTranslationKey implements TranslationKeyProvider {

  EXAMPLE_TEXT,
  EXAMPLE_MESSAGE,
  HELLO_WORLD;

  private volatile TranslationKey translationKey;

  @Override
  public TranslationKey getTranslationKey() {
    TranslationKey result = this.translationKey;
    if (null != result) {
      return result;
    }
    synchronized (this) {
      result = this.translationKey;
      if (null == result) {
        this.translationKey = result = DefaultTranslationKey.of(this.name());
      }
      return result;
    }
  }
}
