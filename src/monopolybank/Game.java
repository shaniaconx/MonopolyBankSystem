package monopolybank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static monopolybank.Constants.*;

public class Game implements Serializable {
    List <MonopolyCode> codes = null;
    Map<Color, Player> players = null;

    Game (){
        this.loadMonopolyCodes(CONFIG_CODE);
    }

    private void loadMonopolyCodes (String fileName){
        this.codes = new ArrayList<>();
        Scanner file = new Scanner(fileName);

        while (file.hasNextLine()){
            String actualLine = file.nextLine();
            String[] parts = actualLine.split(";");
            String type = parts[1];

            MonopolyCode newCode = null;
            switch (type){
                case "STREET":
                    newCode = new Street(actualLine);
                    break;
                case "PAYMENT_CHARGE_CARD":
                    newCode = new PaymentChargeCard(actualLine);
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
            this.codes.add(newCode);
        }
        file.close();
    }
    private String getCodeClass(String line){
        //todo once array is delimeted
    }
}
