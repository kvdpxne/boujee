package me.kvdpxne.boujee;

/**
 * Represents an exception that is thrown when a required translation key is
 * missing.
 * <p>
 * The {@code MissingTranslationKeyException} class extends
 * {@link TranslationException} to provide specific context about missing keys
 * in the translation process.
 *
 * @since 0.1.0
 */
public class MissingTranslationKeyException
  extends TranslationException {

  /**
   * A unique identifier for serialization purposes.
   * <p>
   * This value ensures compatibility during serialization and deserialization.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = 2916377213118923176L;

  /**
   * Constructs a new {@code MissingTranslationKeyException} with the specified
   * detail message and cause.
   *
   * @param message a {@link String} providing details about the exception.
   * @param cause   a {@link Throwable} that caused this exception to be
   *                thrown.
   * @since 0.1.0
   */
  public MissingTranslationKeyException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code MissingTranslationKeyException} with the specified
   * detail message.
   *
   * @param message a {@link String} providing details about the exception.
   * @since 0.1.0
   */
  public MissingTranslationKeyException(
    final String message
  ) {
    super(message);
  }
}
