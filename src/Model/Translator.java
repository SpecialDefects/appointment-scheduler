package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    private static Locale currentLocale = Locale.ENGLISH;
    private static String language = currentLocale.getLanguage();
    private static ArrayList<String> languages = new ArrayList<String>(Arrays.asList("en", "fr"));
    private static ResourceBundle translator;

    public static String getTranslation(String word) {

        return translator.getString(word);
    }

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
