package me.kvdpxne.boujee.example;

import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.TranslationKeyProvider;

public enum EnumTranslationKey
  implements TranslationKeyProvider {

  EXAMPLE_TEXT,
  EXAMPLE_MESSAGE,
  HELLO_WORLD;

  @Override
  public TranslationKey getTranslationKey() {
    return TranslationKey.of(this.name());
  }
}
