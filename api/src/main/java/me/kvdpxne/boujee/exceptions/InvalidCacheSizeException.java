package me.kvdpxne.boujee.exceptions;

/**
 * @since 0.1.0
 */
public final class InvalidCacheSizeException
  extends
  TranslationException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = 4220667060216828363L;

  /**
   * @since 0.1.0
   */
  public InvalidCacheSizeException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public InvalidCacheSizeException(
    final String message
  ) {
    super(message);
  }
}