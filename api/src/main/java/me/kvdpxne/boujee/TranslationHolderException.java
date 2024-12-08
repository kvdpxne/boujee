package me.kvdpxne.boujee;

public class TranslationHolderException extends RuntimeException {

  private static final long serialVersionUID = -570446505515279670L;

  private final Translation<?> translation;

  public TranslationHolderException(final Translation<?> translation) {
    this.translation = translation;
  }

  public Translation<?> getTranslation() {
    return this.translation;
  }
}
