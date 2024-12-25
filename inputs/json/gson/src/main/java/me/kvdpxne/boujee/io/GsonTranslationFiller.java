package me.kvdpxne.boujee.io;

import me.kvdpxne.boujee.BasicTranslationService;
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
    if (translationService instanceof BasicTranslationService) {
//      ((BasicTranslationService) translationService).updateTranslations();
    }
  }
}
