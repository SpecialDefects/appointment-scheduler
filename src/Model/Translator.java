package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a Translator
 * Uses properties to determine translations
 */
public class Translator {
    /** users locale **/
    private static Locale currentLocale = Locale.ENGLISH;
    /** get language from locale **/
    private static String language = currentLocale.getLanguage();
    /** available languages for translation **/
    private static ArrayList<String> languages = new ArrayList<String>(Arrays.asList("en", "fr"));
    /** resourcebundle to reference for translations **/
    private static ResourceBundle translator;

    /**
     * @param word word to translate
     * @return translated word
     */
    public static String getTranslation(String word) {
        return translator.getString(word);
    }

    /**
     * sets locale
     * @param locale locale to set
     */
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        language = currentLocale.getLanguage();
        /** if language is not supported, default to english **/
        if (!languages.contains(language)) {
            currentLocale = Locale.ENGLISH;
            language = "en";
        }
        translator = ResourceBundle.getBundle("ResourceBundles/Language_" + language);
    }
}
