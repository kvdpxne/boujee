package me.kvdpxne.boujee.message;

import java.util.Arrays;

public class BasicTranslationMessage
  implements TranslationMessage {

  private static final long serialVersionUID = 226473269992833938L;

  protected char[][] message;

  public BasicTranslationMessage(final char[][] message) {
    this.message = message;
  }

  @Override
  public char[][] getContent() {
    return Arrays.copyOf(this.message, this.message.length);
  }

  @Override
  public String[] getContentAsString() {
    final String[] newContent = new String[this.message.length];
    for (int i = 0; i < this.message.length; i++) {
      newContent[i] = new String(this.message[i]);
    }
    return newContent;
  }

  @Override
  public final boolean equals(
    final Object o
  ) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BasicTranslationMessage)) {
      return false;
    }

    final BasicTranslationMessage that = (BasicTranslationMessage) o;
    return Arrays.deepEquals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.message);
  }
}
