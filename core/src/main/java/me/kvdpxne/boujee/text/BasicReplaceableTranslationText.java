package me.kvdpxne.boujee.text;

import java.util.Map;
import me.kvdpxne.boujee.replace.Characters;

public class BasicReplaceableTranslationText
  extends BasicTranslationText
  implements ReplaceableTranslationText {

  private static final long serialVersionUID = 3626337823661723471L;

  public BasicReplaceableTranslationText(final char[] content) {
    super(content);
  }

  @Override
  public ReplaceableTranslationText replace(
    final char[] field,
    final char[] value
  ) {
    return new BasicReplaceableTranslationText(
      Characters.replace(super.text, field, value)
    );
  }

  @Override
  public ReplaceableTranslationText replace(
    final Map<char[], char[]> values
  ) {
    char[] newContent = super.text;
    for (final Map.Entry<char[], char[]> entry : values.entrySet()) {
      newContent = Characters.replace(newContent, entry.getKey(), entry.getValue());
    }
    return null;
  }
}
