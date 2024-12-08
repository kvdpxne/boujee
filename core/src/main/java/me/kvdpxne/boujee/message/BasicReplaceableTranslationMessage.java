package me.kvdpxne.boujee.message;

import java.util.Map;

public class BasicReplaceableTranslationMessage
  extends BasicTranslationMessage
  implements ReplacableTranslationMessage {

  private static final long serialVersionUID = -8395546537545631026L;

  public BasicReplaceableTranslationMessage(final String[] message) {
    super(message);
  }

  @Override
  public ReplacableTranslationMessage replace(
    final String field,
    final Object value
  ) {
    return null;
  }

  @Override
  public ReplacableTranslationMessage replace(
    final Map<String, Object> values
  ) {
    final int length = super.message.length;
    final String[] newContent = new String[length];
    for (int i = 0; i < length; i++) {
      String newContentLine = newContent[i];
      for (final Map.Entry<String, Object> entry : values.entrySet()) {
        newContentLine = newContentLine.replace(
          entry.getKey(),
          entry.getValue().toString()
        );
      }
      newContent[i] = newContentLine;
    }
    return new BasicReplaceableTranslationMessage(newContent);
  }
}
