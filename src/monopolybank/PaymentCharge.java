package monopolybank;

import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{

    private int amount;

    PaymentCharge(String code){
        super(0, ""); //initialize or else can't split string first

        String [] parts = code.split(";");
        super.setId(Integer.parseInt(parts[0]));
        super.setDescription(parts[2]);

        Matcher moneyFounder = PATTERN.matcher(super.getDescription()); //finds the cuantity used in the description
        if (moneyFounder.find()) {
            String amountDesc = moneyFounder.group();
            this.amount = Integer.parseInt(amountDesc.replaceAll("[^\\d.]", ""));
        }
    }


}
