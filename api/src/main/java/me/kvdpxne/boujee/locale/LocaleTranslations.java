package me.kvdpxne.boujee.locale;

import java.util.Collection;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.text.TranslationText;
import me.kvdpxne.boujee.message.TranslationMessage;

/**
 * @since 0.1.0
 */
public interface LocaleTranslations {

  /**
   * @since 0.1.0
   */
  LocaleSource getLocaleSource();

  /**
   * @since 0.1.0
   */
  Collection<TranslationMessage> getMessages();

  /**
   * @since 0.1.0
   */
  Collection<TranslationText> getTexts();

  /**
   * @since 0.1.0
   */
  TranslationMessage findMessageOrNull(
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  TranslationText findTextOrNull(
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  void clear();
}
