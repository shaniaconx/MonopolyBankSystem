package monopolybank;

import java.util.ArrayList;

public class Service extends Property{
    private final ArrayList<Integer> costStaying;
    private final Terminal terminal;

    Service(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        this.terminal = terminal;
        costStaying = new ArrayList<>();
        for (int i = 3; i < 5; i++){
            costStaying.add(parseCostStaying(i, code));
        }
    }

    @Override
    public int getPaymentForRent(){
        int rent = 0;
        terminal.show("dice_number");
        int num = terminal.read();
        Player actualOwner = this.getOwner();

        if (actualOwner != null && actualOwner.getProperties() != null) {
            for (Property p : actualOwner.getProperties()) {
                if (p instanceof Service) {
                    rent++;
                }
            }
        }

        return num * costStaying.get(rent-1);

    }

}
