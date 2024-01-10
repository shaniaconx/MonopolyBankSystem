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
        if (this.builtHotel){
            return this.costStayingWithHouses.get(5);
        } else {
            return this.costStayingWithHouses.get(builtHouses);
        }
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
                this.buyHouses();
                break;
            case 3:
                //Sell Houses/Hotel
                this.sellHouses(false);
                break;
            default:
                terminal.show("canceled");
                break;
        }
    }

    public void sellHouses(boolean mandatory){
        Player owner = getOwner();

        if(this.builtHotel){
            terminal.show("sell_hotel", this.getDescription(), this.housePrice);

            boolean done = acceptCancel(() -> owner.getPaid(this.housePrice), mandatory);
            if(done){
                setBuiltHotel(false);
            }
        } else if (this.hasBuildings()){
            terminal.show("number_houses_toSell", this.builtHouses, this.getDescription());
            int quantity;
            do {
                quantity = terminal.read();
                if(quantity <= builtHouses){
                    terminal.show("number_of_houses_error");
                }
            }while (quantity <= builtHouses);

            int totalPrice = this.housePrice * quantity;
            terminal.show("sell_houses", quantity, totalPrice);
            boolean done = acceptCancel(() -> owner.getPaid(totalPrice), mandatory);
            if(done){
                setBuiltHouses(builtHouses-quantity);
            }
        }
    }

    private void buyHouses(){
        Player owner = getOwner();
        if (!builtHotel && builtHouses == 4){
            terminal.show("buy_hotel", this.getDescription(), this.housePrice);
            boolean done = acceptCancel(() -> owner.pay(this.housePrice, false), false);
            if (done){
                this.setBuiltHotel(true);
                this.setBuiltHouses(0);
            }
        } else if (!this.builtHotel && builtHouses < 4){
            terminal.show("number_houses_toBuy", this.builtHouses, this.getDescription());
            int quantity;
            do {
                quantity = terminal.read();
                if((quantity + builtHouses) > 4){
                    terminal.show("number_of_houses_error");
                }
            }while ((quantity + builtHouses) > 4);

            int totalHousePrice = housePrice * quantity;
            terminal.show("buy_houses", quantity, totalHousePrice);
            boolean done = acceptCancel(() -> owner.pay(totalHousePrice, false), false);
            if (done){
                this.setBuiltHouses(quantity + builtHouses);
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
