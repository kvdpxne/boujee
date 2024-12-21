package me.kvdpxne.boujee.message;

import java.util.Map;
import me.kvdpxne.boujee.replace.Characters;

public class BasicReplaceableTranslationMessage
  extends BasicTranslationMessage
  implements ReplacableTranslationMessage {

  private static final long serialVersionUID = -8395546537545631026L;

  public BasicReplaceableTranslationMessage(final char[][] message) {
    super(message);
  }

  @Override
  public ReplacableTranslationMessage replace(
    final char[] field,
    final char[] value
  ) {
    final char[][] newContent = super.message;
    for (int i = 0; i < super.message.length; ++i) {
      newContent[i] = Characters.replace(newContent[i], field, value);
    }
    return new BasicReplaceableTranslationMessage(newContent);
  }

  @Override
  public ReplacableTranslationMessage replace(
    final Map<char[], char[]> values
  ) {
    final char[][] newContent = super.message;
    for (int i = 0; i < super.message.length; ++i) {
      char[] newContentLine = newContent[i];
      for (final Map.Entry<char[], char[]> entry : values.entrySet()) {
        newContentLine = Characters.replace(newContentLine, entry.getKey(), entry.getValue());
      }
      newContent[i] = newContentLine;
    }
    return new BasicReplaceableTranslationMessage(newContent);
  }
}
