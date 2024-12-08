package me.kvdpxne.boujee.text;

import java.util.Map;

public class BasicReplaceableTranslationText
  extends BasicTranslationText
  implements ReplaceableTranslationText {

  private static final long serialVersionUID = 3626337823661723471L;

  public BasicReplaceableTranslationText(final String content) {
    super(content);
  }

  @Override
  public ReplaceableTranslationText replace(
    final String field,
    final Object value
  ) {
    final String newContent = super.content.replace(field, value.toString());
    return new BasicReplaceableTranslationText(newContent);
  }

  @Override
  public ReplaceableTranslationText replace(
    final Map<String, Object> values
  ) {
    String newContent = super.content;
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      newContent = newContent.replace(entry.getKey(), entry.getValue().toString());
    }
    return new BasicReplaceableTranslationText(newContent);
  }
}
