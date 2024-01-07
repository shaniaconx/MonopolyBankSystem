package monopolybank;

import java.util.ArrayList;

public class Transport extends Property{
    private final ArrayList<Integer> costStaying;

    Transport(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        costStaying = new ArrayList<>();
        for (int i = 3; i < 7; i++){
            costStaying.add(parseCostStaying(i, code));
        }
    }

    @Override
    public int getPaymentForRent(){
        int rent = 0;
        Player actualOwner = this.getOwner();
        for (Property p: actualOwner.getProperties()) {
            String actualClass = p.getPropertyClass();
            if (this.getPropertyClass().equals(actualClass)){
                rent++;
            }
        }
        return costStaying.get(rent);
    }


}
