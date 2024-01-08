package monopolybank;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static monopolybank.Constants.PATTERN;

public class RepairsCard extends MonopolyCode{
    private int amountHouse;
    private int amountHotel;
    private static final Terminal terminal = getTerminal();

    RepairsCard(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal);

        Matcher moneyFinder = PATTERN.matcher(parseDescription(code)); //finds the cuantity used in the description
        int counter = 0;
        while (moneyFinder.find()){
            int number = Integer.parseInt(moneyFinder.group(1));
            if (counter == 0){
                this.amountHouse = number;
            } else if (counter == 1) {
                this.amountHotel = number;
            }
            counter++;
        }
    }

    public int getAmountHouse() {
        return amountHouse;
    }

    public int getAmountHotel() {
        return amountHotel;
    }

    @Override
    public boolean doOperation(Player p){
        ArrayList<Property> playersProperties = p.getProperties();
        int playerHouses;
        int playerHotels;
        for (Street unit : playersProperties) {
            if(unit.hasHotel){
                playerHotels += 1;
            } else {
                playerHouses += unit.getBuiltHouses;
            }
        }
        int totalForHouses = playerHouses * getAmountHouse();
        int totalForHotel = playerHotels * getAmountHotel();
        int totalPayment = totalForHotel + totalForHouses;
        this.showSummary(p, playerHouses, totalForHouses, playerHotels, totalForHotel, totalPayment);

        boolean result = acceptCancel(() -> p.pay(totalPayment, true), false);
        if(!result){
            p.traspaseProperties(null);
            return false;
        }
        return true;
    }

    private void showSummary(Player p, int playerHouses, int totalForHouses, int playerHotels, int totalForHotels, int totalPayment){
        String playersColor = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        terminal.show("repairs_payment", this.getDescription(), playersColor, playerHouses, playerHotels, totalForHouses, totalForHotels, totalPayment);
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
