package me.kvdpxne.boujee;

import java.io.Serializable;

/**
 * @since 0.1.0
 */
public interface TranslationKey
  extends TranslationKeyProvider, Serializable {

  /**
   * @since 0.1.0
   */
  String getName();

  /**
   * @since 0.1.0
   */
  int getOrdinalNumber();
}
