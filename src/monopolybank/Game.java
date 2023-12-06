package monopolybank;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

import static monopolybank.Constants.*;

public class Game implements Serializable {
    //List <MonopolyCode> codes = null;
    private Map<Integer, MonopolyCode> codes = null;
    private Map<Color, Player> players = null;
    private Terminal terminal;

    Game (){
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
                        newCode = new Street(actualLine);
                        break;
                    case "PAYMENT_CHARGE_CARD":
                        newCode = new PaymentCharge(actualLine);
                        break;
                    case "TRANSPORT":
                        newCode = new Transport(actualLine);
                        break;
                    case "SERVICE":
                        newCode = new Service(actualLine);
                        break;
                    case "REPAIRS_CARD":
                        newCode = new RepairsCard(actualLine);
                        break;
                    default:
                        System.out.println("ERROR, tipo de código no encontrado.");
                }
                this.codes.put(newCode);
            }
            myReader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error ocurred. Couldn't read file");
            e.printStackTrace();
        }
    }
    private String getCodeClass(String line){
        String[] parts = line.split(";");
        return parts[1];
    }

    private void createPlayers(){}
}
