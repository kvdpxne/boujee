package me.kvdpxne.boujee.io;

import me.kvdpxne.boujee.TranslationService;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.1.0
 */
public interface TranslationFiller {

  /**
   * @since 0.1.0
   */
  void fill(
    @NotNull String path,
    @NotNull TranslationService translationService
  );
}
