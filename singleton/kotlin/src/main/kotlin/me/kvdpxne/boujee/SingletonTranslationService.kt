package me.kvdpxne.boujee

import java.util.Locale
import me.kvdpxne.boujee.locale.BasicLocaleSource

/**
 * @since 0.1.0
 */
object SingletonTranslationService : BasicTranslationService(
  BasicLocaleSource(Locale.getDefault())
)