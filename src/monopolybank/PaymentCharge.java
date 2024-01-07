package monopolybank;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{
    private int amount;
    private static Terminal terminal = getTerminal();

    PaymentCharge(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal);

        Matcher moneyFounder = PATTERN.matcher(code); //finds the cuantity used in the description
        if (moneyFounder.find()) {
            String amountDesc = moneyFounder.group();
            this.amount = Integer.parseInt(amountDesc.replaceAll("[^\\d.]", ""));
        }
    }

    private void showSummary(Player p, int amount){
        String playersColor = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        if(amount < 0){
            terminal.show("payment", this.getDescription(), playersColor, amount);
        } else {
            terminal.show("charge", this.getDescription(), playersColor, amount);
        }
    }

    @Override
    public boolean doOperation(Player p){
        showSummary(p, this.amount);
        if (this.amount < 0){
            boolean result = acceptCancel(() -> p.pay(this.amount, true), false);
            if(!result){
                p.traspaseProperties(null);
                return false;
            }
        } else {
            acceptCancel(() -> p.getPaid(this.amount), false);
        }
        return true;
    }
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
}
