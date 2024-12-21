package me.kvdpxne.boujee.locale;

import java.util.Collection;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.text.TranslationText;

/**
 * Provides an interface for managing locale-specific translations, including
 * messages and texts.
 * <p>
 * The {@code LocaleTranslations} interface allows retrieval, searching, and
 * clearing of translations associated with a specific locale source.
 *
 * @since 0.1.0
 */
public interface LocaleTranslations {

  /**
   * Retrieves the {@link LocaleSource} associated with this set of
   * translations.
   *
   * @return the {@link LocaleSource} representing the locale configuration.
   * @since 0.1.0
   */
  LocaleSource getLocaleSource();

  /**
   * Retrieves all {@link TranslationMessage} objects associated with this
   * locale.
   * <p>
   * These messages typically represent localized strings for user-facing
   * communication.
   *
   * @return a {@link Collection} of {@link TranslationMessage} objects.
   * @since 0.1.0
   */
  Collection<TranslationMessage> getMessages();

  /**
   * Retrieves all {@link TranslationText} objects associated with this locale.
   * <p>
   * These texts represent larger or more structured pieces of localized
   * content.
   *
   * @return a {@link Collection} of {@link TranslationText} objects.
   * @since 0.1.0
   */
  Collection<TranslationText> getTexts();

  /**
   * Searches for a {@link TranslationMessage} using the provided
   * {@link TranslationKeyProvider}.
   * <p>
   * If no matching message is found, this method returns {@code null}.
   *
   * @param translationKeyProvider the key provider used to locate the message.
   * @return the {@link TranslationMessage} if found; {@code null} otherwise.
   * @since 0.1.0
   */
  TranslationMessage findMessageOrNull(
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * Searches for a {@link TranslationText} using the provided
   * {@link TranslationKeyProvider}.
   * <p>
   * If no matching text is found, this method returns {@code null}.
   *
   * @param translationKeyProvider the key provider used to locate the text.
   * @return the {@link TranslationText} if found; {@code null} otherwise.
   * @since 0.1.0
   */
  TranslationText findTextOrNull(
    final TranslationKeyProvider translationKeyProvider
  );

  /**
   * Clears all stored translations, including messages and texts, associated
   * with this locale.
   * <p>
   * This method is useful for resetting or reinitializing the translations.
   *
   * @since 0.1.0
   */
  void clear();
}
