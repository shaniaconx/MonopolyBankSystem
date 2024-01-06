package monopolybank;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Translator {
    private ResourceBundle resourceBundle;

    Translator(Locale locale){
        this.resourceBundle = ResourceBundle.getBundle("messages",locale);

    }
    public String translate(String key, Object... args) {
        try {
            String message = resourceBundle.getString(key);
            return String.format(message, args);
        } catch (MissingResourceException e) {
            // Retornar la clave misma si no se encuentra la traducción
            return key;
        }
    }
}
