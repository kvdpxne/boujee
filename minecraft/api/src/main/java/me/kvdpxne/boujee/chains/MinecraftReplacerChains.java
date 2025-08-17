package me.kvdpxne.boujee.chains;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import me.kvdpxne.boujee.content.Replacer;
import me.kvdpxne.boujee.content.Translation;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.boujee.send.MinecraftSendChoices;

public class MinecraftReplacerChains
  extends TranslationReplacerChains {

  public MinecraftReplacerChains(
    final List<Receiver> receivers,
    final Map<LocaleSource, Translation<?>> translations
  ) {
    super(receivers, translations);
  }

  @Override
  public <T extends SendChoices> T replace(
    final Supplier<Replacer> replacerSupplier
  ) {
    super.performReplace(replacerSupplier);
    // noinspection unchecked
    return (T) new MinecraftSendChoices(super.receivers, super.translations);
  }

  @Override
  public <T extends SendChoices> T skip() {
    // noinspection unchecked
    return (T) new MinecraftSendChoices(super.receivers, super.translations);
  }
}
