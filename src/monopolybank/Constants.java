package monopolybank;

import java.util.regex.Pattern;

public class Constants {
    // Ruta al archivo de configuración del código de Monopoly.
    public final static String CONFIG_CODE = "config/MonopolyCode.txt";
    // Ruta al directorio donde se almacenan los juegos antiguos.
    public final static String GAMES_PATH = "config/oldGames/";
    // Ruta al archivo que contiene IDs.
    public final static String ID_FILE = "config/IdFile.txt";
    // Ruta base para los archivos de idiomas y mensajes.
    public final static String LANGUAGES_PATH = "languages.messages";
    // ExpresiÃ³n regular para identificar montos de dinero en formato de euros.
    public final static String MONEY_REGULAR = "-?(\\d+)â‚¬";
    // PatrÃ³n compilado a partir de la expresión regular para montos de dinero.
    public final static Pattern PATTERN = Pattern.compile(MONEY_REGULAR);
}
