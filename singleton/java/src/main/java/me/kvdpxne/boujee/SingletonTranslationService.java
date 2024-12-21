package me.kvdpxne.boujee;

import java.util.Locale;
import me.kvdpxne.boujee.locale.BasicLocaleSource;

/**
 * @since 0.1.0
 */
public final class SingletonTranslationService
  extends BasicTranslationService {

  /**
   * @since 0.1.0
   */
  private static SingletonTranslationService instance;

  /**
   * @since 0.1.0
   */
  public static SingletonTranslationService getInstance() {
    if (null == instance) {
      instance = new SingletonTranslationService();
    }
    return instance;
  }

  /**
   * @since 0.1.0
   */
  private SingletonTranslationService() {
    super(new BasicLocaleSource(Locale.getDefault()));
  }
}
