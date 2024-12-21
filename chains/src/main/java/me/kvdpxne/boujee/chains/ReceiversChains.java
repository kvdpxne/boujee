package me.kvdpxne.boujee.chains;

import me.kvdpxne.boujee.receiver.Receiver;

/**
 * A utility class for creating {@link TranslationChains} instances from one or
 * more {@link Receiver} objects.
 * <p>
 * The {@code ReceiversChains} class provides methods for constructing
 * translation chains based on receivers, ensuring that the input is valid and
 * non-null.
 *
 * @since 0.1.0
 */
public final class ReceiversChains {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws AssertionError if an attempt to instantiate this class occurs.
   * @since 0.1.0
   */
  private ReceiversChains() {
    throw new AssertionError("This class is non-instantiable.");
  }

  /**
   * Creates a {@link TranslationChains} instance from an {@link Iterable}
   * collection of {@link Receiver} objects.
   *
   * @param receivers an {@link Iterable} of {@link Receiver} objects.
   * @return a {@link TranslationChains} instance containing the provided
   * receivers.
   * @throws NullPointerException     if the provided collection of receivers is
   *                                  {@code null}.
   * @throws IllegalArgumentException if the provided collection of receivers is
   *                                  empty.
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
   * Creates a {@link TranslationChains} instance from an array of
   * {@link Receiver} objects.
   *
   * @param receivers an array of {@link Receiver} objects.
   * @return a {@link TranslationChains} instance containing the provided
   * receivers.
   * @throws NullPointerException     if the provided array of receivers is
   *                                  {@code null}.
   * @throws IllegalArgumentException if the provided array of receivers is
   *                                  empty.
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
   * Creates a {@link TranslationChains} instance from a single {@link Receiver}
   * object.
   *
   * @param receiver a {@link Receiver} object.
   * @return a {@link TranslationChains} instance containing the provided
   * receiver.
   * @throws NullPointerException if the provided receiver is {@code null}.
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
