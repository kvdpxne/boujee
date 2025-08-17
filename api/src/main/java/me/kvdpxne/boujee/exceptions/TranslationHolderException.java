package me.kvdpxne.boujee.exceptions;

import me.kvdpxne.boujee.content.Translation;

/**
 * @since 0.1.0
 */
public class TranslationHolderException
  extends RuntimeException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -570446505515279670L;

  /**
   * @since 0.1.0
   */
  private final Translation<?> translation;

  /**
   * @since 0.1.0
   */
  public TranslationHolderException(
    final Translation<?> translation
  ) {
    this.translation = translation;
  }

  /**
   * @since 0.1.0
   */
  public Translation<?> getTranslation() {
    return this.translation;
  }
}
