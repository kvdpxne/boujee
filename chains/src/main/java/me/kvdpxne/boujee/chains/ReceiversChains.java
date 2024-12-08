package me.kvdpxne.boujee.chains;

import me.kvdpxne.boujee.receiver.Receiver;

/**
 * @since 0.1.0
 */
public final class ReceiversChains {

  /**
   * @since 0.1.0
   */
  public static TranslationChains receivers(
    final Iterable<Receiver> receivers
  ) {
    if (null == receivers) {
      throw new NullPointerException(
        "The passed collection of receivers must not be null."
      );
    }

    int size = 0;
    for (final Receiver ignored : receivers) {
      ++size;
    }

    if (0 == size) {
      throw new IllegalArgumentException(
        "The passed collection of receivers must not be empty."
      );
    }

    final Receiver[] receiversArray = new Receiver[size];
    for (final Receiver receiver : receivers) {
      receiversArray[--size] = receiver;
    }

    return new TranslationChains(receiversArray);
  }

  /**
   * @since 0.1.0
   */
  public static TranslationChains receivers(
    final Receiver[] receivers
  ) {
    if (null == receivers) {
      throw new NullPointerException(
        "The passed collection of receivers must not be null."
      );
    }

    if (0 == receivers.length) {
      throw new IllegalArgumentException(
        "The passed collection of receivers must not be empty."
      );
    }

    return new TranslationChains(receivers);
  }

  /**
   * @since 0.1.0
   */
  public static TranslationChains receiver(
    final Receiver receiver
  ) {
    if (null == receiver) {
      throw new NullPointerException(
        "The passed receiver must not be null."
      );
    }

    return new TranslationChains(new Receiver[]{receiver});
  }
}
