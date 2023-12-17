package monopolybank;

import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{

    private int amount;

    PaymentCharge(String code){
        super(parseId(code), parseDescription(code));

        Matcher moneyFounder = PATTERN.matcher(code); //finds the cuantity used in the description
        if (moneyFounder.find()) {
            String amountDesc = moneyFounder.group();
            this.amount = Integer.parseInt(amountDesc.replaceAll("[^\\d.]", ""));
        }
    }
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }

    @Override
    public void doOperation(Player p){
        //todo player identifier needed
    }
}
