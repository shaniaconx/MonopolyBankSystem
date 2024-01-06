package monopolybank;
import java.util.Locale;

abstract class Terminal {
    private TranslatorManager translatorManager;

    public Terminal() {
        this.translatorManager = new TranslatorManager();
    }

    public abstract void show(String key, Object... args);
    public abstract int read();

    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }
}
