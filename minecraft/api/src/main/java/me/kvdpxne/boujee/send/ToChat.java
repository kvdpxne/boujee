package me.kvdpxne.boujee.send;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.kvdpxne.boujee.Translation;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.message.TranslationMessage;
import me.kvdpxne.boujee.receiver.MinecraftReceiver;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.boujee.text.TranslationText;

public class ToChat extends BaseSendable {

  private final String prefix;

  protected ToChat(
    final List<Receiver> receivers,
    final Map<LocaleSource, Translation<?>> translations,
    final String prefix
  ) {
    super(receivers, translations);
    this.prefix = prefix;
  }

  /**
   * @param receiver
   * @param content
   *
   * @since 0.1.0
   */
  private void send(
    final MinecraftReceiver receiver,
    final char[] content
  ) {
    if (null == this.prefix || this.prefix.isEmpty()) {
      receiver.onChat(content);
      return;
    }

    final int prefixLength = this.prefix.length();
    final char[] newContent = new char[prefixLength + content.length + 1];

    newContent[prefixLength + 1] = ' ';
    System.arraycopy(this.prefix.toCharArray(), 0, newContent, 0, prefixLength);
    System.arraycopy(content, 0, newContent, prefixLength + 2, content.length);

    receiver.onChat(newContent);
  }

  /**
   * @since 0.1.0
   */
  private void send(
    final MinecraftReceiver receiver
  ) {
    final Translation<?> translation =
      super.findTranslation(receiver.getLocaleSource());

    if (translation instanceof TranslationText) {
      final TranslationText text = (TranslationText) translation;
      receiver.onChat(text.getContent());
      return;
    }

    if (translation instanceof TranslationMessage) {
      final TranslationMessage message = (TranslationMessage) translation;
      for (final char[] line : message.getContent()) {
        receiver.onChat(line);
      }
      return;
    }

    throw new UnsupportedOperationException();
  }

  @Override
  public void send() {
    final Iterator<Receiver> iterator = super.receivers.iterator();
    while (iterator.hasNext()) {
      final Receiver receiver = iterator.next();
      if (receiver instanceof MinecraftReceiver) {
        this.send((MinecraftReceiver) receiver);
      }
      iterator.remove();
    }
    super.translations.clear();
  }
}
