package me.kvdpxne.boujee.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.nio.file.FileSystem;
import java.util.stream.Stream;
import me.kvdpxne.boujee.locale.Locales;
import me.kvdpxne.boujee.content.Translation;
import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.locale.*;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.TranslationText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public final class InsideGsonReader {

  private InsideGsonReader() {}

  private static LocaleTranslations decodeJson(
    final Path path
  ) {
    final String fileName = path.getFileName().toString();
    final String localeName = fileName.substring(0, fileName.lastIndexOf('.'));
    final Locale locale = Locales.fromString(localeName);

    try (final InputStream in = Files.newInputStream(path);
         final InputStreamReader reader = new InputStreamReader(in)) {
      final JsonElement rootJson = JsonParser.parseReader(reader);
      final Map<TranslationKey, Translation<?>> map = FlattenGson.flatten(rootJson);

      final Map<TranslationKey, TranslationMessage> messages = new HashMap<>();
      final Map<TranslationKey, TranslationText> texts = new HashMap<>();

      for (final Map.Entry<TranslationKey, Translation<?>> entry : map.entrySet()) {
        final Translation<?> value = entry.getValue();
        if (value instanceof TranslationMessage) {
          messages.put(entry.getKey(), (TranslationMessage) value);
        } else if (value instanceof TranslationText) {
          texts.put(entry.getKey(), (TranslationText) value);
        }
      }

      return new BasicLocaleTranslations(
        new BasicLocaleSource(locale),
        messages,
        texts
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static LocaleTranslations read(final String rawPath, final String name) {
    return InsideFiles.files(
      InsideGsonReader.class.getClassLoader(),
      rawPath,
      (fs, basePath) -> {
        try {
          return Files.walk(basePath)
            .filter(Files::isRegularFile)
            .filter(p -> p.getFileName().toString().contains(name))
            .map(InsideGsonReader::decodeJson)
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Translation file for locale '" + name + "' not found."));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    );
  }

  /**
   *
   */
  public static Collection<LocaleTranslations> read(
    final String rawPath
  ) {
    return InsideFiles.files(
      InsideGsonReader.class.getClassLoader(),
      rawPath,
      (final FileSystem __, final Path basePath) -> {
        try (final Stream<Path> stream = Files.walk(basePath)) {
          return stream
            .filter(Files::isRegularFile)
            .map(InsideGsonReader::decodeJson)
            .collect(Collectors.toList());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    );
  }
}