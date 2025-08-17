package me.kvdpxne.boujee.io;

import me.kvdpxne.boujee.DefaultTranslationService;
import me.kvdpxne.boujee.TranslationService;

public class GsonTranslationFiller
  implements TranslationFiller {

  public static final GsonTranslationFiller INSTANCE
    = new GsonTranslationFiller();

  @Override
  public void fill(
    final String path,
    final TranslationService translationService
  ) {
    if (translationService instanceof DefaultTranslationService) {
      ((DefaultTranslationService) translationService).updateTranslations(
        InsideGsonReader.read(path)
      );
    }
  }
}
