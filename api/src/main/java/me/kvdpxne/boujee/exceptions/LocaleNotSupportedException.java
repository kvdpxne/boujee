package me.kvdpxne.boujee.exceptions;

/**
 * @since 0.1.0
 */
public final class LocaleNotSupportedException
  extends
  TranslationException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -2671466268579241007L;

  /**
   * @since 0.1.0
   */
  public LocaleNotSupportedException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public LocaleNotSupportedException(
    final String message
  ) {
    super(message);
  }
}