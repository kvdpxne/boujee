package me.kvdpxne.boujee;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.LocaleTranslations;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.text.TranslationText;

public class BasicTranslationService
  implements TranslationService {

  /**
   * @since 0.1.0
   */
  private final ConcurrentMap<LocaleSource, LocaleTranslations> translations
    = new ConcurrentHashMap<>();

  /**
   * @since 0.1.0
   */
  private LocaleSource defaultLocaleSource = null;

  /**
   * @since 0.1.0
   */
  private LocaleTranslations defaultLocaleTranslations = null;

  /**
   * @since 0.1.0
   */
  public BasicTranslationService(
    final LocaleSource localeSource
  ) {
    this.defaultLocaleSource = localeSource;
  }

  /**
   * @since 0.1.0
   */
  public BasicTranslationService() {
  }

  /**
   * @since 0.1.0
   */
  public void updateTranslations(
    final Collection<LocaleTranslations> translations
  ) {
    this.translations.clear();

    if (null == this.defaultLocaleSource) {
      for (final LocaleTranslations localeTranslations : translations) {
        this.translations.put(
          localeTranslations.getLocaleSource(),
          localeTranslations
        );
      }
      return;
    }

    for (final LocaleTranslations localeTranslations : translations) {
      final LocaleSource source = localeTranslations.getLocaleSource();
      if (source.equals(this.defaultLocaleSource)) {
        this.translations.put(source, localeTranslations);
        continue;
      }

      this.defaultLocaleTranslations = localeTranslations;
      this.defaultLocaleSource = source;
    }
  }

  @Override
  public Collection<LocaleSource> getLoadedLocaleSources() {
    return Collections.unmodifiableCollection(this.translations.keySet());
  }

  @Override
  public Collection<LocaleTranslations> getLoadedLocaleTranslations() {
    return Collections.unmodifiableCollection(this.translations.values());
  }

  @Override
  public LocaleSource getDefaultLocaleSource() {
    return this.defaultLocaleSource;
  }

  @Override
  public LocaleTranslations getDefaultLocaleTranslations() {
    return this.defaultLocaleTranslations;
  }

  /**
   * @since 0.1.0
   */
  private LocaleSource checkLocaleSource(
    final LocaleSourceProvider localeSourceProvider
  ) {
    if (null == localeSourceProvider) {
      throw new NullPointerException(
        "The passed locale source provider must not be null."
      );
    }

    final LocaleSource localeSource = localeSourceProvider.getLocaleSource();
    if (null == localeSource) {
      throw new RuntimeException("No localeSource found for " + localeSource);
    }

    return localeSource;
  }

  /**
   * @since 0.1.0
   */
  private TranslationKey checkTranslationKey(
    final TranslationKeyProvider translationKeyProvider
  ) {
    if (null == translationKeyProvider) {
      throw new NullPointerException(
        "The passed translation key provider must not be null."
      );
    }

    final TranslationKey translationKey = translationKeyProvider.getTranslationKey();
    if (null == translationKey) {
      throw new NullPointerException(
        "The passed translation key provider cannot provide a key that is a null."
      );
    }

    return translationKey;
  }

  @Override
  public void updateDefaultLocaleSource(
    final LocaleSourceProvider localeSourceProvider
  ) {
    this.defaultLocaleSource = this.checkLocaleSource(localeSourceProvider);
  }

  @Override
  public void updateDefaultLocaleTranslations() {
    if (null == this.defaultLocaleSource) {
      throw new RuntimeException("No translations container found for " + this.defaultLocaleSource);
    }

  }

  @Override
  public LocaleTranslations findLocaleTranslationsOrNull(
    final LocaleSourceProvider localeSourceProvider
  ) {
    return this.translations.get(
      this.checkLocaleSource(localeSourceProvider)
    );
  }

  @Override
  public TranslationMessage findMessageOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    final LocaleTranslations localeTranslations =
      this.findLocaleTranslationsOrNull(localeSourceProvider);
    if (null == localeTranslations) {
      return null;
    }

    return localeTranslations.findMessageOrNull(
      this.checkTranslationKey(translationKeyProvider)
    );
  }

  @Override
  public TranslationText findTextOrNull(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    final LocaleTranslations localeTranslations =
      this.findLocaleTranslationsOrNull(localeSourceProvider);
    if (null == localeTranslations) {
      return null;
    }

    return localeTranslations.findTextOrNull(
      this.checkTranslationKey(translationKeyProvider)
    );
  }

  @Override
  public LocaleTranslations findLocaleTranslationsOrDefault(
    final LocaleSourceProvider localeSourceProvider
  ) {
    final LocaleTranslations localeTranslations =
      this.findLocaleTranslationsOrNull(localeSourceProvider);
    if (null != localeTranslations) {
      return localeTranslations;
    }

    if (null == this.defaultLocaleTranslations) {
      if (null == this.defaultLocaleSource) {
        throw new TranslationException(
          "The default locale collection of translations cannot be null."
        );
      }

      this.updateDefaultLocaleTranslations();
    }

    return this.defaultLocaleTranslations;
  }

  @Override
  public TranslationMessage findMessageOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    return this.findLocaleTranslationsOrDefault(localeSourceProvider)
      .findMessageOrNull(translationKeyProvider);
  }

  @Override
  public TranslationText findTextOrDefault(
    final LocaleSourceProvider localeSourceProvider,
    final TranslationKeyProvider translationKeyProvider
  ) {
    return this.findLocaleTranslationsOrDefault(localeSourceProvider)
      .findTextOrNull(translationKeyProvider);
  }

  @Override
  public int getNumberOfLocales() {
    int count = this.translations.size();
    if (null != this.defaultLocaleSource) {
      ++count;
    }
    return count;
  }

  @Override
  public int getNumberOfMessages() {
    return this.translations.size();
  }

  @Override
  public int getNumberOfTexts() {
    return this.translations.size();
  }

  @Override
  public void clear() {
    this.translations.clear();
    this.defaultLocaleTranslations = null;
    this.defaultLocaleSource = null;
  }
}
