package monopolybank;
import java.text.MessageFormat;
import java.util.*;

public class TextTerminal extends Terminal{
    private ResourceBundle messages;
    TextTerminal (Locale locale){
        super();
        this.messages = ResourceBundle.getBundle("messages", locale);
    }
    @Override
    public int read() {
        Scanner info = new Scanner(System.in);
        return info.nextInt();
    }

    @Override
    public void show(String key, Object... args) {
        String template = messages.getString(key);
        String message = (args.length > 0) ? MessageFormat.format(template, args) : template;
        System.out.println(message);
    }
}

