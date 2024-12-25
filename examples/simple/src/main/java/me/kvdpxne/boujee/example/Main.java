package me.kvdpxne.boujee.example;

import java.util.Arrays;
import me.kvdpxne.boujee.SingletonTranslationService;
import me.kvdpxne.boujee.TranslationService;
import me.kvdpxne.boujee.io.GsonTranslationFiller;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.locale.LocaleTranslations;

public final class Main {

  public static void printTranslation(
    final LocaleSourceProvider localeSourceProvider
  ) {
    //
    final TranslationService translationService
      = SingletonTranslationService.getInstance();

    //
    final LocaleTranslations localeTranslations
      = translationService.findLocaleTranslationsOrNull(localeSourceProvider);

    //
    final String exampleText = localeTranslations
      .findTextOrNull(EnumTranslationKey.EXAMPLE_TEXT)
      .getContentAsString();

    //
    final String[] exampleMessage = localeTranslations
      .findMessageOrNull(EnumTranslationKey.EXAMPLE_MESSAGE)
      .getContentAsString();

    //
    final String[] helloWorld = localeTranslations
      .findMessageOrNull(EnumTranslationKey.HELLO_WORLD)
      .getContentAsString();

    System.out.println("Raw:");
    System.out.println(exampleText);
    System.out.println(Arrays.toString(exampleMessage));
    System.out.println(Arrays.toString(helloWorld));

//    System.out.println("Formatterd:");
//    final String[] helloWorld = localeTranslations
//      .findMessageOrNull(EnumTranslationKey.HELLO_WORLD);
  }

  public static void main(
    final String[] arguments
  ) {
    //
    final TranslationService translationService
      = SingletonTranslationService.getInstance();

    // Fills the translation service with messages and text.
    GsonTranslationFiller.INSTANCE
      .fill("assets/languages", translationService);

    //
    printTranslation(EnumSupportedLocale.PL_PL);

    //
    printTranslation(EnumSupportedLocale.EN_US);
  }
}
