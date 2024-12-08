package me.kvdpxne.boujee.send;

import java.util.List;
import java.util.Map;
import me.kvdpxne.boujee.Translation;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.receiver.Receiver;

public abstract class BaseSendable
  implements Sendable {

  protected final List<Receiver> receivers;
  protected final Map<LocaleSource, Translation<?>> translations;

  protected BaseSendable(
    final List<Receiver> receivers,
    final Map<LocaleSource, Translation<?>> translations
  ) {
    this.receivers = receivers;
    this.translations = translations;
  }

  /**
   * @since 0.1.0
   */
  protected Translation<?> findTranslation(
    final LocaleSource localeSource
  ) {
    final Translation<?> translation = this.translations.get(localeSource);
    if (null == translation) {
      throw new IllegalStateException(
        "No translation was found for the \"" + localeSource.getLocalization()
          + "\" locale source."
      );
    }
    return translation;
  }
}
