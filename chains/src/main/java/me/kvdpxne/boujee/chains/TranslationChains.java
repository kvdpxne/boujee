package me.kvdpxne.boujee.chains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.kvdpxne.boujee.content.Translation;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.TranslationService;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.receiver.Receiver;

public final class TranslationChains {

  private final List<Receiver> receivers;
  private final Map<LocaleSource, Translation<?>> translations;

  TranslationChains(
    final Receiver[] receivers
  ) {
    if (null == receivers || 0 == receivers.length) {
      throw new IllegalArgumentException(
        "The passed collection of receivers must not be null or empty."
      );
    }

    final Set<LocaleSource> locales = new HashSet<>();
    for (final Receiver receiver : receivers) {
      locales.add(receiver.getLocaleSource());
    }

    this.receivers = new ArrayList<>(receivers.length);
    this.translations = new HashMap<>(locales.size());
  }

  public TranslationReplacerChains message(
    final TranslationKeyProvider translationKeyProvider,
    final TranslationService translationService
  ) {
    for (final Receiver receiver : this.receivers) {
      this.translations.put(
        receiver.getLocaleSource(),
        translationService.findMessageOrDefault(
          receiver,
          translationKeyProvider
        )
      );
    }
    return new TranslationReplacerChains(this.receivers, this.translations);
  }

  public TranslationReplacerChains text(
    final TranslationKeyProvider translationKeyProvider,
    final TranslationService translationService
  ) {
    for (final Receiver receiver : this.receivers) {
      this.translations.put(
        receiver.getLocaleSource(),
        translationService.findTextOrDefault(
          receiver,
          translationKeyProvider
        )
      );
    }
    return new TranslationReplacerChains(this.receivers, this.translations);
  }
}
