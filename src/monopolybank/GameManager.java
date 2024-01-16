package monopolybank;

import java.io.*;
import static monopolybank.Constants.*;

public class GameManager {
    private static int actualGameId;
    private Terminal mainTerminal;

    /**
     * Constructor de GameManager.
     * Carga el último ID de juego guardado y lo incrementa para el nuevo juego.
     */
    GameManager(){
        int lastGameId = loadLastGameId();
        actualGameId = lastGameId++;
        saveLastGameId();
    }

     /**
     * Guarda el ID del último juego en un archivo.
     */
    public static void saveLastGameId() {
        try (PrintWriter out = new PrintWriter(ID_FILE)) {
            out.println(actualGameId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga el último ID de juego desde un archivo.
     *
     * @return El último ID de juego.
     */
    public static int loadLastGameId() {
        int lastGameId;
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE))) {
            lastGameId = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            lastGameId = 0;
        }
        return lastGameId;
    }

    /**
     * Obtiene el ID actual del juego.
     *
     * @return El ID actual del juego.
     */
    public static int getActualGameId(){
        return actualGameId;
    }

    /**
     * Inicia el juego configurando el idioma y preguntando al usuario si desea continuar un juego guardado o iniciar uno nuevo.
     */
    public void start(){
        this.mainTerminal = new TextTerminal();
        mainTerminal.show("select_language");
        int choice = mainTerminal.read();
        switch (choice){
            case 1:
                mainTerminal.getTranslatorManager().changeLanguage("es");
                break;
            case 2:
                mainTerminal.getTranslatorManager().changeLanguage("en");
                break;
            case 3:
                mainTerminal.getTranslatorManager().changeLanguage("cat");
                break;
            case 4 :
                mainTerminal.getTranslatorManager().changeLanguage("eusk");
                break;
            default:
                mainTerminal.show("default_language");
                mainTerminal.getTranslatorManager().changeLanguage("es");
                break;
        }

        int option = 0;
        while (option != 1 || option != 2) {
            option = askForResumeGame();
            switch (option) {
                case 1:
                    if(showSavedGames()){
                        mainTerminal.show("load_game");
                        int gameId = mainTerminal.read();
                        Game loadedGame = loadGame(gameId);
                        if (loadedGame != null){
                            loadedGame.play();
                        }
                    }
                    break;
                case 2:
                    Game nuevo = new Game(mainTerminal);
                    nuevo.play();
                    break;
                default:
                    mainTerminal.show("error_choosing");
            }
        }


    }
    
     /**
     * Pregunta al usuario si desea reanudar un juego guardado.
     *
     * @return La opción seleccionada por el usuario.
     */
    private int askForResumeGame(){
        mainTerminal.show("game_options");
        return mainTerminal.read();
    }

    /**
     * Guarda un juego en un archivo.
     *
     * @param game El juego a guardar.
    * @param id El ID del juego a guardar.
    */
    public static void saveGame(Game game, int id){
        String gamePath = GAMES_PATH + id + ".ser";

        try(FileOutputStream fileOutputStream = new FileOutputStream(gamePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Carga un juego guardado desde un archivo.
    *
    * @param gameId El ID del juego a cargar.
    * @return El juego cargado, o null si ocurre un error.
    */
    public Game loadGame(int gameId){
        String gamePath = GAMES_PATH + gameId + ".ser";
        Game game = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(gamePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            game = (Game) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return game;
    }

    /**
    * Muestra los juegos guardados disponibles.
    *
    * @return true si hay juegos guardados disponibles, false en caso contrario.
    */
    private boolean showSavedGames(){
        File folder = new File (GAMES_PATH);
        File[] listOfGames = folder.listFiles();

        if(listOfGames == null || listOfGames.length == 0){
            mainTerminal.show("saved_games_not_found");
            return false;
        } else {
            mainTerminal.show("saved_games_title");
            for(File file : listOfGames){
                if (file.isFile()){
                    mainTerminal.show("saved_game", file.getName().replace(".ser", ""));
                }
            }
            return true;
        }
    }
}
