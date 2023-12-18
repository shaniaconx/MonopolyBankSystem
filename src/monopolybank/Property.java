package monopolybank;

public class Property extends MonopolyCode{
    private int price;
    private boolean mortgaged;
    private int mortgageValue;
    private Player owner = null;

    Property (int id, String description, Terminal terminal, int price, boolean mortaged, int mortgageValue){
        //todo se hace en las clases hijas
        super(id, description, terminal);
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

    public void doOwnerOperations(){
        //todo specified in each child class i think -> super.mortgage
    }
    public void getPaymentForRent(){
        //todo specific in each child class
    }

    private void showMortgageSummary(){
        //mensaje de hipotecar o deshipotecar
    }

}
