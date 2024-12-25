package me.kvdpxne.boujee.text;

import java.util.Arrays;

/**
 * @since 0.1.0
 */
public class BasicTranslationText
  implements TranslationText {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = 2118276182344594976L;

  /**
   * @since 0.1.0
   */
  protected final char[] text;

  /**
   * @param text
   * @throws NullPointerException
   *
   * @since 0.1.0
   */
  public BasicTranslationText(
    final char[] text
  ) {
    if (null == text) {
      throw new NullPointerException(
        "The passed 1d character array must not be null."
      );
    }
    this.text = text;
  }

  /**
   * @since 0.1.0
   */
  public BasicTranslationText() {
    this.text = new char[0];
  }

  @Override
  public char[] getContent() {
    final char[] copy = new char[this.text.length];
    System.arraycopy(this.text, 0, copy, 0, this.text.length);
    return copy;
  }

  @Override
  public String getContentAsString() {
    return new String(this.text);
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BasicTranslationText)) {
      return false;
    }

    final BasicTranslationText that = (BasicTranslationText) o;
    return Arrays.equals(this.text, that.text);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(this.text);
  }
}
