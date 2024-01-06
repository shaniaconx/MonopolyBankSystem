package monopolybank;

import java.util.function.Supplier;

abstract class Property extends MonopolyCode{
    private String className;
    private int price;
    private boolean mortgaged;
    private int mortgageValue;
    private Player owner;
    private static Terminal terminal = getTerminal();

    Property (int id, String className, String description, Terminal terminal, int price, boolean mortaged, int mortgageValue){
        super(id, description, terminal);
        setOwner(null);
        setClassName(className);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean getMortgaged(){
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public int getMortgageValue(){
        return mortgageValue;
    }

    private void setClassName(String name){
        this.className = name;
    }
    public String getPropertyClass(){
        return this.className;
    }

    public int getPrice() {
        return price;
    }

    public abstract int getPaymentForRent();

    @Override
    public boolean doOperation(Player p) {
        Player actualOwner = this.getOwner();
        if (actualOwner == null){
            this.showPurchaseSummary(this.getPrice(), p);
            acceptCancel(() -> p.pay(this.getPrice(), false), false);
            return true;
        } else if (actualOwner.equals(p)) {
            this.doOwnerOperations();
            return  true;
        } else { //!actualOwner.equals(p)
            int rentToPay = this.getPaymentForRent();
            boolean result = acceptCancel(() -> p.pay(rentToPay, true), true);
            if(result){
                actualOwner.getPaid(rentToPay);
                return true;
            }else{
                p.traspaseProperties(actualOwner);
                return false;
            }
        }
    }

    public void doOwnerOperations(){
        terminal.show("owner_operations_general");
        int choice = terminal.read();

        if (choice == 1) {
            this.showMortgageSummary();
            acceptCancel(this::mortgagingOperations, false);
        }else{
            terminal.show("canceled");
        }
    }

    private boolean mortgagingOperations (){
        if(this.mortgaged){
            this.owner.pay(this.mortgageValue, false);
        } else {
            this.owner.getPaid(this.mortgageValue);
        }
        return mortgaged;
    }

    private void showMortgageSummary(){
        if(mortgaged){
            terminal.show("property_unmortgage",this.getDescription(), this.owner.getColor(), this.mortgageValue);
        } else {
            terminal.show("property_mortgage",this.getDescription(), this.owner.getColor(), this.mortgageValue);
        }
    }

    public void showPaymentSummary(int amount, Player p){
        terminal.show("property_rent_payment", p.getColor(), this.getDescription(), amount, this.getOwner().getColor());
    }

    public void showPurchaseSummary(int amount, Player p){
        terminal.show("property_purchase_payment", this.getDescription(), p.getColor(), amount);
    }

    public static boolean acceptCancel(Supplier<Boolean> operation, boolean mandatory){
        if (mandatory){
            terminal.show("mandatory");
            return operation.get();
        } else {
            int choice;
            do {
                choice = terminal.read();
                switch (choice) {
                    case 1:
                        return operation.get();
                    case 2:
                        terminal.show("canceled");
                        return false;
                    default:
                        terminal.show("error_choosing");
                }
            }while(choice != 1 || choice != 2);
        }
        return false;
    }

    protected int parseCostStaying(int part, String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[part]);
    }
    protected static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    protected static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
    protected static String parseClass(String code){
        String[] parts = code.split(";");
        return parts[1];
    }
    protected static int parseMortgageValue(String code){
        String[] parts = code.split(";");
        return Integer.parseInt(parts[parts.length - 1]);
    }
}
