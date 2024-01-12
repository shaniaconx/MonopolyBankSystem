package monopolybank;

import java.util.regex.Pattern;

public class Constants {
    public final static String CONFIG_CODE = "config/MonopolyCode.txt";
    public final static String GAMES_PATH = "config/oldGames/";
    public final static String ID_FILE = "config/IdFile.txt";
    public final static String LANGUAGES_PATH = "languages.messages";
    public final static String MONEY_REGULAR = "-?(\\d+)€";
    public final static Pattern PATTERN = Pattern.compile(MONEY_REGULAR);
}
