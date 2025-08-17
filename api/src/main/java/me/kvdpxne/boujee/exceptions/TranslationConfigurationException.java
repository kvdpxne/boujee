package me.kvdpxne.boujee.exceptions;

/**
 * @since 0.1.0
 */
public final class TranslationConfigurationException
  extends
  TranslationException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -3046247823829693502L;

  /**
   * @since 0.1.0
   */
  public TranslationConfigurationException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public TranslationConfigurationException(
    final String message
  ) {
    super(message);
  }
}