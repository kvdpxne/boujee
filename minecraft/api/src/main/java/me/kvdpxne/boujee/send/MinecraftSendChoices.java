package me.kvdpxne.boujee.send;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import me.kvdpxne.boujee.Translation;
import me.kvdpxne.boujee.chains.SendChoices;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.boujee.text.TranslationText;

/**
 * @since 0.1.0
 */
public class MinecraftSendChoices
  implements SendChoices {

  protected final List<Receiver> receivers;
  protected final Map<LocaleSource, Translation<?>> translations;

  /**
   * @since 0.1.0
   */
  public MinecraftSendChoices(
    final List<Receiver> receivers,
    final Map<LocaleSource, Translation<?>> translations
  ) {
    this.receivers = receivers;
    this.translations = translations;
  }

  /**
   * @since 0.1.0
   */
  public Translation<?> original() {
    if (1 != this.receivers.size() || 1 != this.translations.size()) {
      throw new IllegalStateException(
        "Previously passed receiver and translation collections must have "
          + "exactly 1 receiver and translation each."
      );
    }

    final LocaleSource source = this.receivers.get(0).getLocaleSource();
    final Translation<?> translation = this.translations.get(source);

    if (null == translation) {
      throw new IllegalStateException(
        "No translation was found for the \"" + source.getLocalization()
          + "\" locale source."
      );
    }

    return translation;
  }

  /**
   * @since 0.1.0
   */
  public <T extends Serializable> T raw() {
    final Translation<?> translation = this.original();

    if (translation instanceof TranslationText) {
      return (T) ((TranslationText) translation).getContent();
    }

    if (translation instanceof TranslationMessage) {
      return (T) ((TranslationMessage) translation).getContent();
    }

    throw new UnsupportedOperationException(
      "The translation is not an instance of TranslationText or "
        + "TranslationMessage."
    );
  }

  /**
   * @since 0.1.0
   */
  public ToChat useChat(final String prefix) {
    return new ToChat(this.receivers, this.translations, prefix);
  }

  /**
   * @since 0.1.0
   */
  public ToChat useChat() {
    return this.useChat(null);
  }
}
