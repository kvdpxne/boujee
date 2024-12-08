package me.kvdpxne.boujee;

public class MissingTranslationKeyException
  extends TranslationException {

  private static final long serialVersionUID = 2916377213118923176L;

  public MissingTranslationKeyException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public MissingTranslationKeyException(final String message) {
    super(message);
  }
}
