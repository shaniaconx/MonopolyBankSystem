package monopolybank;

import java.util.function.Supplier;

abstract class Property extends MonopolyCode{
    private String className;
    private int price;
    private boolean mortgaged;
    private int mortgageValue;
    private Player owner;
    private final Terminal terminal;

    Property (int id, String className, String description, Terminal terminal, int price, boolean mortgaged, int mortgageValue){
        super(id, description, terminal);
        this.price = price;
        this.mortgaged = mortgaged;
        this.mortgageValue = mortgageValue;
        this.terminal = terminal;
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

    public int getPrice() {
        return price;
    }

    public abstract int getPaymentForRent();

    @Override
    public int doOperation(Player p) {
        Player actualOwner = this.getOwner();
        if (actualOwner == null){
            this.showPurchaseSummary(this.getPrice(), p);
            int result = acceptCancel(() -> p.pay(this.getPrice(), false), false);
            if (result == 1) {
                this.setOwner(p);
                p.addProperty(this);
            }
            return result;
        } else if (actualOwner.equals(p)) {
            this.doOwnerOperations();
            return 1;
        } else { //!actualOwner.equals(p)
            int rentToPay = this.getPaymentForRent();
            this.showPaymentSummary(rentToPay, p);
            int result = acceptCancel(() -> p.pay(rentToPay, true), true);
            if(result == 1){
                actualOwner.getPaid(rentToPay);
                return 1;
            }else if (result == 0){
                p.traspaseProperties(actualOwner);
                return 0;
            }
            return result;
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

    protected int mortgagingOperations (){
        if(this.mortgaged){
            return this.owner.pay(this.mortgageValue, false);
        } else {
            return this.owner.getPaid(this.mortgageValue);
        }
    }

    protected void showMortgageSummary(){
        String owner = terminal.getTranslatorManager().getTranslator().translate(this.getOwner().getColor().toString());
        if(mortgaged){
            terminal.show("property_unmortgage",this.getDescription(), owner, this.mortgageValue);
        } else {
            terminal.show("property_mortgage",this.getDescription(), owner, this.mortgageValue);
        }
    }

    public void showPaymentSummary(int amount, Player p){
        String player = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        String owner = terminal.getTranslatorManager().getTranslator().translate(this.getOwner().getColor().toString());
        terminal.show("property_rent_payment", player, this.getDescription(), amount, owner);
    }

    public void showPurchaseSummary(int amount, Player p){
        String player = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        terminal.show("property_purchase_payment", this.getDescription(),player, amount);
    }



    protected int parseCostStaying(int part, String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[part]);
    }
    protected static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    protected static String parseClass(String code){
        String[] parts = code.split(";");
        return parts[1];
    }
    protected static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
    protected static int parseMortgageValue(String code){
        String[] parts = code.split(";");
        return Integer.parseInt(parts[parts.length - 1]);
    }
}
