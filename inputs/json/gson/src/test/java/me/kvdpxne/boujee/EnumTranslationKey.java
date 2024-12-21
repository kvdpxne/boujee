package me.kvdpxne.boujee;

public enum EnumTranslationKey
  implements TranslationKeyProvider {

  TEST_ARRAY,
  TEST_PRIMITIVE;

  @Override
  public TranslationKey getTranslationKey() {
    return TranslationKey.of(this.name());
  }
}
