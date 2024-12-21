package me.kvdpxne.boujee.text;

import java.util.Arrays;

public class BasicTranslationText
  implements TranslationText {

  private static final long serialVersionUID = 2118276182344594976L;

  protected final char[] text;

  public BasicTranslationText(final char[] text) {
    this.text = text;
  }

  @Override
  public char[] getContent() {
    return Arrays.copyOf(this.text, this.text.length);
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
