package me.kvdpxne.boujee.receiver;

public interface MinecraftReceiver
  extends Receiver {

  void onChat(final String content);
}
