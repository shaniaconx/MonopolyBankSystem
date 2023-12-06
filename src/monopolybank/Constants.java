package monopolybank;

import java.util.regex.Pattern;

public class Constants {
    public final static String CONFIG_CODE = "config/MonopolyCode.txt";
    public final static String MONEY_REGULAR = "-?\\d+€";
    public final static Pattern PATTERN = Pattern.compile(MONEY_REGULAR);
}
