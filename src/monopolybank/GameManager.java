package monopolybank;

import java.io.*;
import static monopolybank.Constants.*;

public class GameManager {
    private static int actualGameId;
    private Terminal mainTerminal;

    GameManager(){
        int lastGameId = loadLastGameId();
        actualGameId = lastGameId++;
        saveLastGameId();
    }

    public static void saveLastGameId() {
        try (PrintWriter out = new PrintWriter(ID_FILE)) {
            out.println(actualGameId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadLastGameId() {
        int lastGameId;
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE))) {
            lastGameId = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            lastGameId = 0;
        }
        return lastGameId;
    }

    public static int getActualGameId(){
        return actualGameId;
    }

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
    private int askForResumeGame(){
        mainTerminal.show("game_options");
        return mainTerminal.read();
    }

    public static void saveGame(Game game, int id){
        String gamePath = GAMES_PATH + id + ".ser";

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(gamePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
