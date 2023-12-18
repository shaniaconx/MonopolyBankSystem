package monopolybank;
import java.util.*;

public class TextTerminal extends Terminal{
    TextTerminal (){
        super();
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
}

