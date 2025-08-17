package me.kvdpxne.boujee.io

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.util.Locale
import java.util.stream.Collectors
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import me.kvdpxne.boujee.content.Translation
import me.kvdpxne.boujee.TranslationKey
import me.kvdpxne.boujee.locale.BasicLocaleSource
import me.kvdpxne.boujee.locale.BasicLocaleTranslations
import me.kvdpxne.boujee.locale.LocaleTranslations
import me.kvdpxne.boujee.locale.Locales
import me.kvdpxne.boujee.content.message.TranslationMessage
import me.kvdpxne.boujee.content.text.TranslationText

/**
 * A utility to read locale-specific JSON translation files and parse them into `LocaleMessages`.
 *
 * @since 0.1.0
 */
internal object InsideJsonReader {

  /**
   * Decodes a JSON file from the given path into a `LocaleMessages` object.
   *
   * @param path Path of the JSON file to decode.
   * @return Parsed `LocaleMessages` containing messages for a specific locale.
   * @throws IllegalArgumentException if the file name does not represent a valid locale.
   * @since 0.1.0
   */
  private fun decodeJson(
    path: Path
  ): LocaleTranslations {
    val fileName: String = path.nameWithoutExtension
    val locale: Locale = Locales.fromString(fileName)

    val texts: String = path.readText()
    val rootJson: JsonElement = Json.parseToJsonElement(texts)

    val map: Map<TranslationKey, Translation<*>> = FlattenJson.flatten(rootJson)

    val messages = hashMapOf<TranslationKey, TranslationMessage>()
    val textes = hashMapOf<TranslationKey, TranslationText>()

    for ((key: TranslationKey, value: Translation<*>) in map) {
      when (value) {
        is TranslationMessage -> messages[key] = value
        is TranslationText -> textes[key] = value
      }
    }

    return BasicLocaleTranslations(
      BasicLocaleSource(locale),
      messages,
      textes
    )
  }

  /**
   * Reads and decodes a specific locale's translation file based on the provided name.
   *
   * @param name The name of the locale file to read (without extension).
   * @return The `LocaleMessages` for the specified locale.
   * @throws NoSuchElementException if the translation file is not found.
   * @since 0.1.0
   */
  internal fun read(
    rawPath: String,
    name: String
  ): LocaleTranslations {
    return InsideFiles.files(
      this::class.java.classLoader,
      rawPath
    ) { _, path: Path ->
      Files.walk(path)
        .filter(Files::isRegularFile)
        .filter {
          it.fileName.toString().contains(name)
        }
        .map(this::decodeJson)
        .findFirst()
        .orElseThrow {
          NoSuchElementException("Translation file for locale '$name' not found.")
        }
    }
  }

  /**
   * Reads and decodes all translation files found in the `translations` directory.
   *
   * @return A collection of `LocaleMessages` for all available locales.
   * @since 0.1.0
   */
  internal fun read(
    rawPath: String
  ): Collection<LocaleTranslations> {
    return InsideFiles.files(
      this::class.java.classLoader,
      rawPath
    ) { fileSystem: FileSystem, path: Path ->
      val f: List<LocaleTranslations> = Files.walk(path)
        .filter(Files::isRegularFile)
        .map(this::decodeJson)
        .collect(Collectors.toList())

      fileSystem.close()
      return@files f
    }
  }
}