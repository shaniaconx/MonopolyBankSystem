package monopolybank;

import java.util.ArrayList;

public class Service extends Property{
    private final ArrayList<Integer> costStaying;

    Service(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        costStaying = new ArrayList<>();
        for (int i = 3; i < 5; i++){
            costStaying.add(parseCostStaying(i, code));
        }
    }

    @Override
    public int getPaymentForRent(){
        int rent = 0;
        terminal.show("¿Qué número has sacado en los dados?");
        int num = terminal.read();
        Player actualOwner = this.getOwner();
        for (Property p: actualOwner.getProperties()) {
            String actualClass = p.getPropertyClass();
            if (this.getPropertyClass().equals(actualClass)){
                rent++;
            }
        }
        return num*costStaying.get(rent);
    }

}
