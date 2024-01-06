package monopolybank;
import java.util.Locale;

public class TranslatorManager {
    private Translator currentLanguage;

    public TranslatorManager() { //idioma por defecto es Español
        this.currentLanguage = new Translator(new Locale("es"));
    }

    public void changeLanguage(String language) {
        Locale newLocale = new Locale(language);
        this.currentLanguage = new Translator(newLocale);
    }

    public Translator getTranslator() {
        return currentLanguage;
    }
}
