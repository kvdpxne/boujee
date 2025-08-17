/**
 * Provides interfaces for handling multi-segment translatable messages within the Boujee Translation Framework.
 *
 * <p>This package contains interfaces that represent translatable messages which may consist of multiple lines or segments.
 * Unlike single-line text translations (handled in the {@code me.kvdpxne.boujee.content.text} package), message-based
 * translations are designed to handle more complex content structures where content is organized in multiple parts.
 *
 * <p>The primary interfaces in this package include:
 * <ul>
 *   <li>{@link TranslationMessage} - Represents a translatable message
 *   consisting of multiple segments or lines</li>
 *   <li>{@link ReplacableTranslationMessage} - Extends {@code TranslationMessage} with placeholder replacement capabilities</li>
 * </ul>
 *
 * <p>Key features of this package:
 * <ul>
 *   <li>Immutability: All implementations are designed to be unmodifiable (as indicated by {@code @Unmodifiable} annotations)</li>
 *   <li>Content representation: Messages are stored as {@code char[][]} for efficient character-level operations</li>
 *   <li>Placeholder replacement: Supports dynamic replacement of placeholders while maintaining immutability</li>
 *   <li>String conversion: Provides methods to convert internal character array representations to standard Java strings</li>
 * </ul>
 *
 * <p>This package works in conjunction with the translation framework's core components to provide robust multi-segment
 * translation capabilities for applications requiring complex message structures.
 *
 * @see me.kvdpxne.boujee.content.text
 * @see me.kvdpxne.boujee.content.Replaceable
 * @see me.kvdpxne.boujee.content.Translation
 * @since 0.1.0
 */
package me.kvdpxne.boujee.content.message;