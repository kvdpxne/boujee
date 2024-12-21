package me.kvdpxne.boujee;

import java.util.concurrent.TimeUnit;
import me.kvdpxne.boujee.replace.Characters;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 7, time = 1)
@Measurement(iterations = 5, time = 2)
@BenchmarkMode({Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(1)
@Fork(1) // do 3 razy sztuka
public class ReplaceBenchmark {

  static final String SHORT_TEXT = "This is a simple example message.";
  static final String LONGER_TEXT = "This is a longer example message that contains more details and extends beyond a single line to demonstrate the creation of lengthy content.";

  static final String TEXT_3 = "This message contains a {PLACEHOLDER}.";
  static final String TEXT_4 = "This is an example of a longer message that includes a {PLACEHOLDER} to showcase how placeholders can be integrated into detailed content.";
  static final String TEXT_5 = "{PLACEHOLDER} is used here in the beginning.";
  static final String TEXT_6 = "{PLACEHOLDER} is the key element introduced at the start of this lengthy example message to demonstrate its early integration in a more elaborate context.";
  static final String TEXT_7 = "This message concludes with a {PLACEHOLDER}.";
  static final String TEXT_8 = "This is an example of a detailed and extended message that ultimately ends with a specific {PLACEHOLDER}, placed deliberately at the conclusion for emphasis.";

  static final String TEXT_9 = "This message contains a {PLACEHOLDER} and another {PLACEHOLDER}.";
  static final String TEXT_10 = "This is an example of a longer message that includes a {PLACEHOLDER} and additional {PLACEHOLDER} to showcase how placeholders can be integrated into detailed content.";
  static final String TEXT_11 = "{PLACEHOLDER} is used here in the beginning, followed by another {PLACEHOLDER}.";
  static final String TEXT_12 = "{PLACEHOLDER} is the key element introduced at the start of this lengthy example message, followed by another {PLACEHOLDER} for added complexity.";
  static final String TEXT_13 = "This message concludes with a {PLACEHOLDER} and an extra {PLACEHOLDER}.";
  static final String TEXT_14 = "This is an example of a detailed and extended message that ultimately ends with a specific {PLACEHOLDER}, accompanied by another {PLACEHOLDER} for further detail.";

  static final char[] TEXT_CHARACTERS_1 = SHORT_TEXT.toCharArray();
  static final char[] TEXT_CHARACTERS_2 = LONGER_TEXT.toCharArray();

  static final char[] TEXT_CHARACTERS_3 = TEXT_3.toCharArray();
  static final char[] TEXT_CHARACTERS_4 = TEXT_4.toCharArray();
  static final char[] TEXT_CHARACTERS_5 = TEXT_5.toCharArray();
  static final char[] TEXT_CHARACTERS_6 = TEXT_6.toCharArray();
  static final char[] TEXT_CHARACTERS_7 = TEXT_7.toCharArray();
  static final char[] TEXT_CHARACTERS_8 = TEXT_8.toCharArray();

  static final char[] TEXT_CHARACTERS_9 = TEXT_9.toCharArray();
  static final char[] TEXT_CHARACTERS_10 = TEXT_10.toCharArray();
  static final char[] TEXT_CHARACTERS_11 = TEXT_11.toCharArray();
  static final char[] TEXT_CHARACTERS_12 = TEXT_12.toCharArray();
  static final char[] TEXT_CHARACTERS_13 = TEXT_13.toCharArray();
  static final char[] TEXT_CHARACTERS_14 = TEXT_14.toCharArray();

  static final char[] CACHED_PLACEHOLDER = "{PLACEHOLDER}".toCharArray();

  //  ██╗░░░██╗░█████╗░███╗░░██╗██╗██╗░░░░░██╗░░░░░░█████╗░
  //  ██║░░░██║██╔══██╗████╗░██║██║██║░░░░░██║░░░░░██╔══██╗
  //  ╚██╗░██╔╝███████║██╔██╗██║██║██║░░░░░██║░░░░░███████║
  //  ░╚████╔╝░██╔══██║██║╚████║██║██║░░░░░██║░░░░░██╔══██║
  //  ░░╚██╔╝░░██║░░██║██║░╚███║██║███████╗███████╗██║░░██║
  //  ░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝╚══════╝╚══════╝╚═╝░░╚═╝

//  @Benchmark
//  public void vanilla_replace_short_text_without_placeholder() {
//    SHORT_TEXT.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_without_placeholder() {
//    LONGER_TEXT.replace("{PLACEHOLDER}", "NEW_TEST");
//  }

//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholder() {
//    TEXT_3.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholder() {
//    TEXT_4.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholder_at_beginning() {
//    TEXT_5.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholder_at_beginning() {
//    TEXT_6.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholder_at_ending() {
//    TEXT_7.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholder_at_ending() {
//    TEXT_8.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholders() {
//    TEXT_9.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholders() {
//    TEXT_10.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholders_at_beginning() {
//    TEXT_11.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholders_at_beginning() {
//    TEXT_12.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_short_text_with_placeholders_at_ending() {
//    TEXT_13.replace("{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void vanilla_replace_longer_text_with_placeholders_at_ending() {
//    TEXT_14.replace("{PLACEHOLDER}", "NEW_TEST");
//  }

  //
  //  ░█████╗░██████╗░░█████╗░░█████╗░██╗░░██╗███████╗
  //  ██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║░░██║██╔════╝
  //  ███████║██████╔╝███████║██║░░╚═╝███████║█████╗░░
  //  ██╔══██║██╔═══╝░██╔══██║██║░░██╗██╔══██║██╔══╝░░
  //  ██║░░██║██║░░░░░██║░░██║╚█████╔╝██║░░██║███████╗
  //  ╚═╝░░╚═╝╚═╝░░░░░╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝╚══════╝
  //

//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_without_placeholder() {
//    StringUtils.replace(SHORT_TEXT, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_without_placeholder() {
//    StringUtils.replace(LONGER_TEXT, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholder() {
//    StringUtils.replace(TEXT_3, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholder() {
//    StringUtils.replace(TEXT_4, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholder_at_beginning() {
//    StringUtils.replace(TEXT_5, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholder_at_beginning() {
//    StringUtils.replace(TEXT_6, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholder_at_ending() {
//    StringUtils.replace(TEXT_7, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholder_at_ending() {
//    StringUtils.replace(TEXT_8, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholders() {
//    StringUtils.replace(TEXT_9, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholders() {
//    StringUtils.replace(TEXT_10, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholders_at_beginning() {
//    StringUtils.replace(TEXT_11, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholders_at_beginning() {
//    StringUtils.replace(TEXT_12, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_short_text_with_placeholders_at_ending() {
//    StringUtils.replace(TEXT_13, "{PLACEHOLDER}", "NEW_TEST");
//  }
//
//  @Benchmark
//  public void apache_commons_lang3_replace_longer_text_with_placeholders_at_ending() {
//    StringUtils.replace(TEXT_14, "{PLACEHOLDER}", "NEW_TEST");
//  }

  //
  //██████╗░░█████╗░██╗░░░██╗░░░░░██╗███████╗███████╗
  //██╔══██╗██╔══██╗██║░░░██║░░░░░██║██╔════╝██╔════╝
  //██████╦╝██║░░██║██║░░░██║░░░░░██║█████╗░░█████╗░░
  //██╔══██╗██║░░██║██║░░░██║██╗░░██║██╔══╝░░██╔══╝░░
  //██████╦╝╚█████╔╝╚██████╔╝╚█████╔╝███████╗███████╗
  //╚═════╝░░╚════╝░░╚═════╝░░╚════╝░╚══════╝╚══════╝
  //

//  @Benchmark
//  public void boujee_replace_short_text_without_placeholder() {
//    Characters.replace(TEXT_CHARACTERS_1, CACHED_PLACEHOLDER, "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_without_placeholder() {
//    Characters.replace(TEXT_CHARACTERS_2, CACHED_PLACEHOLDER, "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholder() {
//    Characters.replace(TEXT_CHARACTERS_3, CACHED_PLACEHOLDER, "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholder() {
//    Characters.replace(TEXT_CHARACTERS_4, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholder_at_beginning() {
//    Characters.replace(TEXT_CHARACTERS_5, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholder_at_beginning() {
//    Characters.replace(TEXT_CHARACTERS_6, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholder_at_ending() {
//    Characters.replace(TEXT_CHARACTERS_7, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholder_at_ending() {
//    Characters.replace(TEXT_CHARACTERS_8, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholders() {
//    Characters.replace(TEXT_CHARACTERS_9, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholders() {
//    Characters.replace(TEXT_CHARACTERS_10, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholders_at_beginning() {
//    Characters.replace(TEXT_CHARACTERS_11, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholders_at_beginning() {
//    Characters.replace(TEXT_CHARACTERS_12, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_short_text_with_placeholders_at_ending() {
//    Characters.replace(TEXT_CHARACTERS_13, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }
//
//  @Benchmark
//  public void boujee_replace_longer_text_with_placeholders_at_ending() {
//    Characters.replace(TEXT_CHARACTERS_14, CACHED_PLACEHOLDER,
//      "NEW_TEST".toCharArray());
//  }




  @Benchmark
  public int indexOf_v1_short_text_without_placeholder() {
    return Characters._indexOf(TEXT_CHARACTERS_1, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_without_placeholder() {
    return Characters._indexOf(TEXT_CHARACTERS_2, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholder() {
    return Characters._indexOf(TEXT_CHARACTERS_3, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholder() {
    return Characters._indexOf(TEXT_CHARACTERS_4, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholder_at_beginning() {
    return Characters._indexOf(TEXT_CHARACTERS_5, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholder_at_beginning() {
    return Characters._indexOf(TEXT_CHARACTERS_6, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholder_at_ending() {
    return Characters._indexOf(TEXT_CHARACTERS_7, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholder_at_ending() {
    return Characters._indexOf(TEXT_CHARACTERS_8, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholders() {
    return Characters._indexOf(TEXT_CHARACTERS_9, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholders() {
    return Characters._indexOf(TEXT_CHARACTERS_10, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholders_at_beginning() {
    return Characters._indexOf(TEXT_CHARACTERS_11, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholders_at_beginning() {
    return Characters._indexOf(TEXT_CHARACTERS_12, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_short_text_with_placeholders_at_ending() {
    return Characters._indexOf(TEXT_CHARACTERS_13, CACHED_PLACEHOLDER, 0);
  }

  @Benchmark
  public int indexOf_v1_longer_text_with_placeholders_at_ending() {
    return Characters._indexOf(TEXT_CHARACTERS_14, CACHED_PLACEHOLDER, 0);
  }
}
