package monopolybank;
import java.util.*;
import java.io.IOException;

public class TextTerminal extends Terminal{
    private TranslatorManager translatorManager;
    TextTerminal (){
        this.translatorManager = new TranslatorManager();
    }
    @Override
    public int read() {
        Scanner info = new Scanner(System.in);
        return info.nextInt();
    }

    @Override
    public void show(String text) {
        TranslatorManager tm = getTranslatorManager();
        Translator t = tm.getCurrentIdiom();
        String toShow = t.translate(text);
        System.out.println(toShow);
    }

    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }
}

