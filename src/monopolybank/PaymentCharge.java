package monopolybank;

import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{
    private int amount;
    private final Terminal terminal;

    PaymentCharge(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal);

        this.terminal = terminal;
        Matcher moneyFinder = PATTERN.matcher(code); //finds the cuantity used in the description
        if (moneyFinder.find()) {
            String moneyString = moneyFinder.group();
            this.amount = Integer.parseInt(moneyString.replace("€", "").trim());
        }
    }

    private void showSummary(Player p, int amount){
        String playersColor = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        if(amount < 0){
            int positiveAmount = -1*amount;
            terminal.show("payment_charge_payment", this.getDescription(), playersColor, positiveAmount);
        } else {
            terminal.show("payment_charge_charge", this.getDescription(), playersColor, amount);
        }
    }

    @Override
    public boolean doOperation(Player p){
        this.showSummary(p, this.amount);
        if (this.amount < 0){
            int positiveAmount = -amount;
            boolean result = acceptCancel(() -> p.pay(positiveAmount, true), false);
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
