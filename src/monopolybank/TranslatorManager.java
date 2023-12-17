package monopolybank;

import java.io.BufferedReader;
import java.io.FileReader;

public class TranslatorManager {
    private Translator currentIdiom;

    public Translator getCurrentIdiom() {
        return currentIdiom;
    }
    public void setCurrentIdiom(Translator currentIdiom) {
        this.currentIdiom = currentIdiom;
    }

    public void changeIdiom(String newDictionary) {
        //newDictionary = English, Euskera, Catalan
        if (!newDictionary.equalsIgnoreCase(getCurrentIdiom().getLanguage())){
            Translator newTranslator = new Translator(newDictionary);
            setCurrentIdiom(newTranslator);
        }
    }
}
