/**
 * Provides interfaces for handling single-segment translatable text within the Boujee Translation Framework.
 *
 * <p>This package contains interfaces that represent translatable text content which typically consists of a single line
 * or paragraph. Unlike multi-segment messages (handled in the {@code me.kvdpxne.boujee.content.message} package),
 * text-based translations are designed for simpler content structures where the entire translation can be represented
 * as a continuous string.
 *
 * <p>The primary interfaces in this package include:
 * <ul>
 *   <li>{@link TranslationText} - Represents a translatable text as a single segment</li>
 *   <li>{@link ReplaceableTranslationText} - Extends {@code TranslationText} with placeholder replacement capabilities</li>
 * </ul>
 *
 * <p>Key features of this package:
 * <ul>
 *   <li>Immutability: All implementations are designed to be unmodifiable (as indicated by {@code @Unmodifiable} annotations)</li>
 *   <li>Content representation: Text is stored as {@code char[]} for efficient character-level operations</li>
 *   <li>Placeholder replacement: Supports dynamic replacement of placeholders while maintaining immutability</li>
 *   <li>String conversion: Provides methods to convert internal character array representations to standard Java strings</li>
 * </ul>
 *
 * <p>This package is particularly useful for:
 * <ul>
 *   <li>User interface labels and captions</li>
 *   <li>Single-line notifications and messages</li>
 *   <li>Simple text elements that don't require segmentation</li>
 *   <li>Situations where performance of string operations is critical</li>
 * </ul>
 *
 * <p>The text-based translation model works in conjunction with the framework's core components to provide efficient
 * single-segment translation capabilities for applications with straightforward text requirements.
 *
 * @see me.kvdpxne.boujee.content.message
 * @see me.kvdpxne.boujee.content.Replaceable
 * @see me.kvdpxne.boujee.content.Translation
 * @since 0.1.0
 */
package me.kvdpxne.boujee.content.text;