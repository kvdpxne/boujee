package me.kvdpxne.boujee.message;

import java.util.Arrays;

/**
 * @since 0.1.0
 */
public class BasicTranslationMessage
  implements TranslationMessage {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = 226473269992833938L;

  /**
   * @since 0.1.0
   */
  protected final char[][] message;

  /**
   * @param message
   * @throws NullPointerException
   * @since 0.1.0
   */
  public BasicTranslationMessage(
    final char[][] message
  ) {
    if (null == message) {
      throw new NullPointerException(
        "The passed 2d character array must not be null."
      );
    }

    for (final char[] text : message) {
      if (text == null) {
        throw new NullPointerException(
          "The passed 2d character array must not contain 1d character "
            + "arrays that are null."
        );
      }
    }

    this.message = message;
  }

  /**
   * @since 0.1.0
   */
  public BasicTranslationMessage() {
    this.message = new char[0][0];
  }

  @Override
  public char[][] getContent() {
    final char[][] copy = new char[this.message.length][];
    System.arraycopy(this.message, 0, copy, 0, this.message.length);
    return copy;
  }

  @Override
  public String[] getContentAsString() {
    final String[] newContent = new String[this.message.length];
    for (int i = 0; i < this.message.length; ++i) {
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
