package me.kvdpxne.boujee.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.kvdpxne.boujee.DefaultTranslationKey;
import me.kvdpxne.boujee.content.Translation;
import me.kvdpxne.boujee.TranslationKey;
import me.kvdpxne.boujee.content.message.BasicTranslationMessage;
import me.kvdpxne.boujee.content.message.TranslationMessage;
import me.kvdpxne.boujee.content.text.BasicTranslationText;
import me.kvdpxne.boujee.content.text.TranslationText;

import java.util.*;

public final class FlattenGson {

  private FlattenGson() {}

  private static TranslationText toText(final JsonPrimitive json) {
    Objects.requireNonNull(json);
    if (!json.isJsonPrimitive() || !json.getAsJsonPrimitive().isString()) {
      throw new IllegalArgumentException("Expected a JSON string for SingleMessage, but received a different type.");
    }

    final String rawText = json.getAsString();
    if (rawText == null || rawText.trim().isEmpty()) {
      throw new IllegalArgumentException("SingleMessage content cannot be blank.");
    }

    return new BasicTranslationText(rawText.toCharArray());
  }

  private static TranslationMessage toMessage(final JsonArray json) {
    Objects.requireNonNull(json);
    if (json.isEmpty()) {
      throw new IllegalArgumentException("Expected a non-empty JSON array for MultipleMessages.");
    }

    final char[][] messages = new char[json.size()][];
    for (int i = 0; i < json.size(); i++) {
      final JsonElement element = json.get(i);
      if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
        throw new IllegalArgumentException("Expected all elements in JSON array to be strings.");
      }
      messages[i] = element.getAsString().toCharArray();
    }

    return new BasicTranslationMessage(messages);
  }

  private static String toKey(final String key, final String previous) {
    Objects.requireNonNull(key);
    final String upperKey = key.toUpperCase();
    if (previous == null || previous.isEmpty()) {
      return upperKey;
    }
    return previous + "_" + upperKey;
  }

  private static Map<TranslationKey, Translation<?>> flattenJsonObject(
    final JsonObject json,
    final String previousKey,
    final Map<TranslationKey, Translation<?>> map
  ) {
    for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
      final String key = entry.getKey();
      final JsonElement element = entry.getValue();
      final String newKey = toKey(key, previousKey);

      if (element.isJsonObject()) {
        flattenJsonObject(element.getAsJsonObject(), newKey, map);
        continue;
      }

      if (element.isJsonPrimitive()) {
        map.put(DefaultTranslationKey.of(newKey), toText(element.getAsJsonPrimitive()));
        continue;
      }

      if (element.isJsonArray()) {
        map.put(DefaultTranslationKey.of(newKey), toMessage(element.getAsJsonArray()));
      }
    }
    return map;
  }

  public static Map<TranslationKey, Translation<?>> flatten(final JsonElement root) {
    Objects.requireNonNull(root);
    final Map<TranslationKey, Translation<?>> map = new HashMap<>();

    if (root.isJsonObject()) {
      return flattenJsonObject(root.getAsJsonObject(), "", map);
    } else {
      throw new RuntimeException("Unsupported root json type.");
    }
  }
}