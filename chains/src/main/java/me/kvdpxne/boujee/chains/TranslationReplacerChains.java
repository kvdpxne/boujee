package me.kvdpxne.boujee.chains;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import me.kvdpxne.boujee.Translation;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.boujee.replace.Replaceable;
import me.kvdpxne.boujee.replace.Replacer;

public class TranslationReplacerChains {

  protected final List<Receiver> receivers;
  protected final Map<LocaleSource, Translation<?>> translations;

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
  protected void performReplace(
    final Supplier<Replacer> replacerSupplier
  ) {
    if (null == replacerSupplier) {
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
      final Replacer replacer = replacerSupplier.get();

      this.translations.put(
        entry.getKey(),
        (Translation<?>) replaceable.replace(replacer.getReplacements())
      );
    }
  }

  /**
   * @since 0.1.0
   */
  public <T extends SendChoices> T replace(
    final Supplier<Replacer> replacerSupplier
  ) {
    throw new UnsupportedOperationException("");
  }

  /**
   * @since 0.1.0
   */
  public <T extends SendChoices> T skip() {
    throw new UnsupportedOperationException("");
  }
}
