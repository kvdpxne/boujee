package me.kvdpxne.boujee.message;

import java.util.Arrays;
import me.kvdpxne.boujee.message.TranslationMessage;

public class BasicTranslationMessage
  implements TranslationMessage {

  private static final long serialVersionUID = 226473269992833938L;

  protected String[] message;

  public BasicTranslationMessage(final String[] message) {
    this.message = message;
  }

  @Override
  public String[] getContent() {
    return Arrays.copyOf(this.message, this.message.length);
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BasicTranslationMessage)) {
      return false;
    }

    final BasicTranslationMessage that = (BasicTranslationMessage) o;
    return Arrays.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(message);
  }
}
