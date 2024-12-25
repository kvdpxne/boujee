package me.kvdpxne.boujee;

/**
 * Represents an exception that occurs during translation-related operations.
 * <p>
 * The {@code TranslationException} class extends {@link RuntimeException} to
 * provide additional context and flexibility for handling errors in translation
 * processes.
 *
 * @since 0.1.0
 */
public class TranslationException
  extends RuntimeException {

  /**
   * A unique identifier for serialization purposes.
   * <p>
   * This value ensures compatibility during serialization and deserialization.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = 1584418068201427219L;

  /**
   * Constructs a new {@code TranslationException} with the specified detail
   * message and cause.
   *
   * @param message a {@link String} providing details about the exception.
   * @param cause   a {@link Throwable} that caused this exception to be
   *                thrown.
   * @since 0.1.0
   */
  public TranslationException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code TranslationException} with the specified detail
   * message.
   *
   * @param message a {@link String} providing details about the exception.
   * @since 0.1.0
   */
  public TranslationException(
    final String message
  ) {
    super(message);
  }
}
