package monopolybank;
import java.util.Locale;

public class TranslatorManager {
    private Translator currentLanguage;

    /**
     * Constructor de TranslatorManager.
     * Inicializa el traductor con el idioma español como predeterminado.
     */
    public TranslatorManager() { 
        this.currentLanguage = new Translator(new Locale("es"));
    }

    /**
     * Cambia el idioma del traductor actual.
     * Crea un nuevo objeto Translator con el idioma especificado.
     *
     * @param language Código del idioma para el nuevo traductor (por ejemplo, "en" para inglés, "es" para español).
     */
    public void changeLanguage(String language) {
        Locale newLocale = new Locale(language);
        this.currentLanguage = new Translator(newLocale);
    }

     /**
     * Obtiene el traductor actual.
     * 
     * @return El objeto Translator actualmente en uso.
     */
    public Translator getTranslator() {
        return currentLanguage;
    }
}
