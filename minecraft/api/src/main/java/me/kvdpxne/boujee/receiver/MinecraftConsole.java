package me.kvdpxne.boujee.receiver;

/**
 * @since 0.1.0
 */
public interface MinecraftConsole
  extends MinecraftReceiver, DefaultReceiver {

  void onConsoleColored(final char[] content);

  void onConsole(final char[] content);
}
