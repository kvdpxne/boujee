package me.kvdpxne.boujee;

/**
 * Defines how the cache size should be determined.
 *
 * @since 0.2.0
 */
public enum CacheSizeMode {
    /**
     * Automatically calculate cache size based on available memory (9%-11% range).
     */
    AUTOMATIC,

    /**
     * Use manually specified cache size.
     */
    MANUAL,

    /**
     * Use default cache size (1000 entries).
     */
    DEFAULT
}