package monopolybank;

import java.util.Scanner;

public class GameManager {
    private final Terminal mainTerminal;

    GameManager(){
        this.mainTerminal = new TextTerminal();
    }
    public void start(){
        int option = 0;
        while (option != 1 || option != 2) {
            option = askForResumeGame();
            switch (option) {
                case 1:
                    Scanner scanner = new Scanner(System.in);
                    mainTerminal.show("load_game");
                    String fileName = scanner.nextLine();
                    String fullFileName = fileName + ".obj"; //".lo que sea"
                    //todo load game file
                    scanner.close();
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
}
