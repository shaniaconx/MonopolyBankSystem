package monopolybank;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

import static monopolybank.Constants.*;

public class Game implements Serializable {
    //List <MonopolyCode> codes = null;
    private Map<Integer, MonopolyCode> codes = null;
    private Map<Integer, Player> players = null;
    private Terminal terminal;

    Game (Terminal terminal){
        this.terminal = terminal;
        this.createPlayers();
        this.loadMonopolyCodes(CONFIG_CODE);
    }

    private void loadMonopolyCodes (String fileName){
        try {
            this.codes = new HashMap<Integer, MonopolyCode>();
            Scanner myReader = new Scanner(fileName);

            while (myReader.hasNextLine()) {
                String actualLine = myReader.nextLine();
                String type = getCodeClass(actualLine);

                MonopolyCode newCode = null;

                switch (type) {
                    case "STREET":
                        newCode = new Street(actualLine, terminal);
                        break;
                    case "PAYMENT_CHARGE_CARD":
                        newCode = new PaymentCharge(actualLine, terminal);
                        break;
                    case "TRANSPORT":
                        newCode = new Transport(actualLine, terminal);
                        break;
                    case "SERVICE":
                        newCode = new Service(actualLine, terminal);
                        break;
                    case "REPAIRS_CARD":
                        newCode = new RepairsCard(actualLine, terminal);
                        break;
                    default:
                        System.out.println("ERROR, tipo de código no encontrado.");
                }
                int key = newCode.getId();
                this.codes.put(key, newCode);
            }
            myReader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred. Couldn't read file");
            e.printStackTrace();
        }
    }
    private String getCodeClass(String line){
        String[] parts = line.split(";");
        return parts[1];
    }

    public void play(){
        //todo while(moreThanOnePlayer)
        terminal.show("Introduzca código de tarjeta:");
        int cardCode = terminal.read();
        terminal.show("Introduzca código de jugador:\n(rojo = 1, verde = 2, azul = 3, negro = 4)");
        int playerCode = terminal.read();
        Player actualPlayer = players.get(playerCode);//todo falta definicion del mapa
        MonopolyCode actualCard = codes.get(cardCode);
        actualCard.doOperation(actualPlayer);
    }

    private void createPlayers(){
        //todo
    }
}
