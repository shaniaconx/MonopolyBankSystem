package monopolybank;

/**
 *
 * @author Shania Manso García
 */
public class MonopolyBank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // El fichero con los codigos se encuentra en "config/MonopolyCode.txt"
        // Los idiomas deben estar en la carpeta "config/languages/"
        // Las partidas antiguas deberán estar en la carpeta "config/oldGames/"

        GameManager manager = new GameManager();
        manager.start();

    }
    
}
