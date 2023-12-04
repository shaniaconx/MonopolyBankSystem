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
        this.codes = new ArrayList<>();
        Scanner fichero = new Scanner(FILE_CODES);

        while (fichero.hasNextLine()){
            String reading = fichero.nextLine();
            String[] parts = reading.split(";");
            String type = parts[1];

            switch (type){
                case "STREET":
                    break;
                case "PAYMENT_CHARGE_CARD":
                    break;
                case "TRANSPORT":
                    break;
                case "SERVICE":
                    break;
                case "REPAIRS_CARD":
                    break;
            }

        }
    }

    private String getCodeClass(String line){
        //todo once array is delimeted
    }
}
