package me.kvdpxne.boujee.io

import me.kvdpxne.boujee.BasicTranslationService
import me.kvdpxne.boujee.TranslationService

/**
 * @since 0.1.0
 */
object JsonTranslationFiller : TranslationFiller {

  /**
   * @since 0.1.0
   */
  override fun fill(
    path: String,
    translationService: TranslationService
  ) {
    if (translationService is BasicTranslationService) {
      translationService.updateTranslations(InsideJsonReader.read(path))
    }
  }
}