package me.kvdpxne.boujee.locale;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.text.TranslationText;

public class BasicLocaleTranslations
  implements LocaleTranslations {

  private final LocaleSource localeSource;
  private final ConcurrentMap<TranslationKey, TranslationMessage> messages;
  private final ConcurrentMap<TranslationKey, TranslationText> texts;

  public BasicLocaleTranslations(
    final LocaleSource localeSource,
    final Map<TranslationKey, TranslationMessage> messages,
    final Map<TranslationKey, TranslationText> texts
  ) {
    this.localeSource = localeSource;
    this.messages = new ConcurrentHashMap<>();
    this.texts = new ConcurrentHashMap<>();

    this.messages.putAll(messages);
    this.texts.putAll(texts);
  }

  public BasicLocaleTranslations(final LocaleSource localeSource) {
    this.localeSource = localeSource;
    this.messages = new ConcurrentHashMap<>();
    this.texts = new ConcurrentHashMap<>();
  }

  @Override
  public LocaleSource getLocaleSource() {
    return this.localeSource;
  }

  @Override
  public Collection<TranslationMessage> getMessages() {
    return Collections.unmodifiableCollection(this.messages.values());
  }

  @Override
  public Collection<TranslationText> getTexts() {
    return Collections.unmodifiableCollection(this.texts.values());
  }

  @Override
  public TranslationMessage findMessageOrNull(
    final TranslationKeyProvider key
  ) {
    return this.messages.get(key.getTranslationKey());
  }

  @Override
  public TranslationText findTextOrNull(
    final TranslationKeyProvider key
  ) {
    return this.texts.get(key.getTranslationKey());
  }

  @Override
  public void clear() {
    this.messages.clear();
    this.texts.clear();

    if (this.localeSource instanceof BasicLocaleSource) {
      synchronized (this.localeSource) {
        ((BasicLocaleSource) this.localeSource).clear();
      }
    }
  }
}
