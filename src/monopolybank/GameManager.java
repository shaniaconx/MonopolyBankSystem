package monopolybank;

import java.util.Scanner;

public class GameManager {
    private Terminal mainTerminal;

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
