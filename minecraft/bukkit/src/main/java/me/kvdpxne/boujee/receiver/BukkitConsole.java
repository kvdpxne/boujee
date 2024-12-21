package me.kvdpxne.boujee.receiver;

import me.kvdpxne.boujee.locale.LocaleSource;

public class BukkitConsole
  implements MinecraftConsole {

  @Override
  public void onConsoleColored(final char[] content) {
    this.onChat(content);
  }

  @Override
  public void onConsole(final char[] content) {

  }

  @Override
  public void onChat(final char[] content) {

  }

  @Override
  public LocaleSource getLocaleSource() {
    return null;
  }
}
