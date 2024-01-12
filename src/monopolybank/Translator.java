package monopolybank;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import static monopolybank.Constants.*;

public class Translator {
    private ResourceBundle resourceBundle;

    Translator(Locale locale){
        resourceBundle = ResourceBundle.getBundle(LANGUAGES_PATH, locale);
    }
    public String translate(String key, Object... args) {
        try {
            String message = resourceBundle.getString(key);
            return String.format(message, args);
        } catch (MissingResourceException e) {
            // if translation not found return key
            return key;
        }
    }
}
