package me.kvdpxne.boujee.text;

public class BasicTranslationText
  implements TranslationText {

  private static final long serialVersionUID = 2118276182344594976L;

  protected final String content;

  public BasicTranslationText(final String content) {
    this.content = content;
  }

  @Override
  public String getContent() {
    return this.content;
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
    return this.content.equals(that.content);
  }

  @Override
  public int hashCode() {
    return this.content.hashCode();
  }
}
