package me.kvdpxne.boujee;

import java.util.Collection;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.LocaleTranslations;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.text.TranslationText;

/**
 * @since 0.1.0
 */
public interface TranslationService {

  /**
   * @since 0.1.0
   */
  Collection<LocaleSource> getLoadedLocaleSources();

  /**
   * @since 0.1.0
   */
  Collection<LocaleTranslations> getLoadedLocaleTranslations();

  /**
   * @since 0.1.0
   */
  LocaleSource getDefaultLocaleSource();

  /**
   * @since 0.1.0
   */
  LocaleTranslations getDefaultLocaleTranslations();

  /**
   * @since 0.1.0
   */
  void updateDefaultLocaleSource(
    final LocaleSourceProvider localeSourceProvider
  );

  /**
   * @since 0.1.0
   */
  void updateDefaultLocaleTranslations();

  /**
   * @since 0.1.0
   */
  LocaleTranslations findLocaleTranslationsOrNull(
    final LocaleSourceProvider localeSourceProvider
  );

  /**
   * @since 0.1.0
   */
  TranslationMessage findMessageOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  TranslationText findTextOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  LocaleTranslations findLocaleTranslationsOrDefault(
    final LocaleSourceProvider localeSourceProvider
  );

  /**
   * @since 0.1.0
   */
  TranslationMessage findMessageOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  TranslationText findTextOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * @since 0.1.0
   */
  int getNumberOfLocales();

  /**
   * @since 0.1.0
   */
  int getNumberOfMessages();

  /**
   * @since 0.1.0
   */
  int getNumberOfTexts();

  /**
   * @since 0.1.0
   */
  void clear();
}
