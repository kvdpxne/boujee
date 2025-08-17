/**
 * Provides core interfaces for representing and manipulating translatable content within the Boujee Translation Framework.
 *
 * <p>This package defines the fundamental abstractions for handling translatable content in the framework. It establishes
 * the contract for how translation data is structured, accessed, and modified while maintaining immutability principles.
 * The interfaces in this package serve as the foundation for more specialized content types in related packages.
 *
 * <p>The primary interfaces in this package include:
 * <ul>
 *   <li>{@link Translation} - Base interface for all translatable content</li>
 *   <li>{@link Replaceable} - Interface for content that supports placeholder replacement operations</li>
 * </ul>
 *
 * <p>Key architectural principles of this package:
 * <ul>
 *   <li><strong>Immutability:</strong> All content implementations are designed to be unmodifiable, with operations that
 *       appear to modify content actually returning new instances</li>
 *   <li><strong>Character-level representation:</strong> Content is stored as character arrays ({@code char[]}) rather
 *       than strings to enable efficient character-level operations and reduce memory overhead</li>
 *   <li><strong>Extensibility:</strong> The interfaces are designed to support various content structures, from simple
 *       text to multi-segment messages</li>
 *   <li><strong>Type safety:</strong> Generic type parameters ensure proper content typing while maintaining flexibility</li>
 * </ul>
 *
 * <p>This package works in conjunction with:
 * <ul>
 *   <li>{@code me.kvdpxne.boujee.content.text} - For single-segment text translations</li>
 *   <li>{@code me.kvdpxne.boujee.content.message} - For multi-segment message translations</li>
 *   <li>{@code me.kvdpxne.boujee.locale} - For locale-specific translation management</li>
 * </ul>
 *
 * <p><strong>Design Rationale:</strong> The use of character arrays instead of strings provides significant performance
 * benefits for certain operations, particularly when dealing with large volumes of translation data or when performing
 * frequent character-level manipulations. The immutability pattern ensures thread safety and predictable behavior
 * in multi-threaded environments.
 *
 * @see me.kvdpxne.boujee.content.text
 * @see me.kvdpxne.boujee.content.message
 * @see me.kvdpxne.boujee.locale
 * @since 0.1.0
 */
package me.kvdpxne.boujee.content;