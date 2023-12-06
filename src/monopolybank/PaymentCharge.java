package monopolybank;

import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{

    private int amount = 0;
    private final String action;

    PaymentCharge(String code){


        String [] parts = code.split(";");
        this.action = parts[2];

        Matcher moneyFounder = PATTERN.matcher(action); //finds the cuantity used in the description
        if (moneyFounder.find()) {
            String amountDesc = moneyFounder.group();
            this.amount = Integer.parseInt(amountDesc.replaceAll("[^\\d.]", ""));
        }
    }


}
