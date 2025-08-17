package me.kvdpxne.boujee.exceptions;

/**
 * @since 0.1.0
 */
public final class TranslationKeyNotFoundException
  extends
  TranslationException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -4625333026754468067L;

  /**
   * @since 0.1.0
   */
  public TranslationKeyNotFoundException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public TranslationKeyNotFoundException(
    final String message
  ) {
    super(message);
  }
}