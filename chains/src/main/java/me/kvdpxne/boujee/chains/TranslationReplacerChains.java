package me.kvdpxne.boujee.chains;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import me.kvdpxne.boujee.Translation;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.boujee.replace.Replaceable;

public final class TranslationReplacerChains {

  private final List<Receiver> receivers;
  private final Map<LocaleSource, Translation<?>> translations;

  TranslationReplacerChains(
    final List<Receiver> receivers,
    final Map<LocaleSource, Translation<?>> translations
  ) {
    this.receivers = receivers;
    this.translations = translations;
  }

  /**
   * @since 0.1.0
   */
  public <T extends SendChoices> T replace(
    final Supplier<Object> replacerSupplier
    ) {
    if (null == replacerSupplier.get()) {
      throw new IllegalArgumentException("Supplier cannot be null");
    }

    for (final Map.Entry<LocaleSource, Translation<?>> entry :
      this.translations.entrySet()
    ) {
      final Translation<?> translation = entry.getValue();
      if (!(translation instanceof Replaceable<?>)) {
        continue;
      }

      final Replaceable<?> replaceable = (Replaceable<?>) translation;
      if (!replaceable.hasPlaceholders()) {
        continue;
      }


    }

    return null;
  }
}
