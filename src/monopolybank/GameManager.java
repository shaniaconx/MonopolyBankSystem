package monopolybank;

import java.util.Scanner;

public class GameManager {
    private final Terminal mainTerminal = new TextTerminal();
    public void start(){
        int option = 0;
        while (option != 1 || option != 2) {
            option = askForResumeGame();
            switch (option) {
                case 1:
                    Scanner scanner = new Scanner(System.in);
                    mainTerminal.show("Introduzca el nombre de la partida que desee cargar:");
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
                    mainTerminal.show("Por favor, seleccione una de las opciones propuestas.");
            }
        }


    }
    private int askForResumeGame(){
        mainTerminal.show("1. Cargar partida\n2. Nueva partida");
        return mainTerminal.read();
    }
}
