package me.kvdpxne.boujee;

/**
 * @since 0.1.0
 */
public class TranslationException extends RuntimeException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = 1584418068201427219L;

  /**
   * @since 0.1.0
   */
  public TranslationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public TranslationException(final String message) {
    super(message);
  }
}
