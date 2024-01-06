package monopolybank;
import java.util.*;

public class TextTerminal extends Terminal{
    @Override
    public void show(String key, Object... args) {
        String translatedMessage = getTranslatorManager().getTranslator().translate(key, args);
        System.out.println(translatedMessage);
    }

    @Override
    public int read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

