package monopolybank;

public class Property extends MonopolyCode{
    private final int price;
    private boolean mortaged = false;
    private final int mortageValue;
    private Player owner;

    Property (int id, String description, int price, int mortageValue, Player owner){
        super(id, description);
        this.price = price;
        this.mortageValue = mortageValue;
        this.owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void doOwnerOperations(){
        //todo specified in each child class i think -> super.mortage
    }
    public void getPaymentForRent(){
        //todo specific in each child class
    }

    private void showMortageSummary(){
        //mensaje de hipotecar o deshipotecar
    }
}
