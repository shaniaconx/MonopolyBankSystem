package monopolybank;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import static monopolybank.Constants.*;

public class Translator {
    private ResourceBundle resourceBundle;

    /**
     * Constructor de Translator.
     * Inicializa el recurso de internacionalización para un idioma específico.
     * 
     * @param locale El objeto Locale que especifica el idioma y la región para la traducción.
     */
    Translator(Locale locale){
        resourceBundle = ResourceBundle.getBundle(LANGUAGES_PATH, locale);
    }
    
    /**
     * Traduce un mensaje usando una clave dada.
     * Si se proporcionan argumentos adicionales, estos se utilizan para formatear el mensaje.
     * 
     * @param key La clave que se utiliza para buscar el mensaje en el recurso de internacionalización.
     * @param args Argumentos opcionales que se utilizan para formatear el mensaje traducido.
     * @return El mensaje traducido y formateado. Si no se encuentra la traducción, devuelve la clave.
     */
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
