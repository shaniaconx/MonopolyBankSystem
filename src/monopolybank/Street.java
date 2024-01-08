package monopolybank;

import java.util.ArrayList;

public class Street extends Property {
    private int builtHouses;
    private boolean builtHotel;
    private final int housePrice;
    private final ArrayList<Integer> costStayingWithHouses;
    private static final Terminal terminal = getTerminal();


    Street(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        this.builtHouses = 0;
        this.builtHotel = false;
        this.housePrice = parseHousePrice(code);

        costStayingWithHouses = new ArrayList<>();
        for (int i = 3; i < 9  ; i++){
            costStayingWithHouses.add(parseCostStayingWithHouses(i, code));
        }
    }

    private int parseCostStayingWithHouses(int part, String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[part]);
    }

    private int parseHousePrice(String code){
        String[] parts = code.split(";");
        return Integer.parseInt(parts[9]);
    }

    @Override
    public int getPaymentForRent() {
        return 0; //todo once buyHouses is finished
    }

    @Override
    public void doOwnerOperations (){
        terminal.show("owner_operations_street");
        int choice = terminal.read();

        switch (choice) {
            case 1:
                //Mortgaging Operations
                this.showMortgageSummary();
                acceptCancel(this::mortgagingOperations, false);
                break;
            case 2:
                //Buy Houses/Hotels

                break;
            case 3:
                //Sell Houses/Hotels

                break;
            default:
                terminal.show("canceled");
                break;
        }
    }

    public void sellHouses(boolean mandatory){
        Player owner = getOwner();

        if(this.builtHotel){
            terminal.show("sell_hotel");
            boolean done = acceptCancel(() -> owner.getPaid(this.housePrice), mandatory);

            if(done){
                setBuiltHotel(false);
            }
        } else {
            terminal.show("number_houses_toSell");
            int cuantity = terminal.read();
            boolean done = acceptCancel(() -> owner.getPaid(this.housePrice * cuantity), mandatory);

            if(done){
                setBuiltHouses(builtHouses-cuantity);
            }
        }
    }

    public boolean hasBuildings(){
        if (this.builtHotel) {
            return true;
        }
        return this.builtHouses > 0;
    }

    public void setBuiltHouses(int builtHouses) {
        this.builtHouses = builtHouses;
    }

    public void setBuiltHotel(boolean builtHotel) {
        this.builtHotel = builtHotel;
    }
}
