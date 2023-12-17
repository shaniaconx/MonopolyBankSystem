package monopolybank;

public class Property extends MonopolyCode{
    private final int price;
    private boolean mortaged;
    private final int mortageValue;
    private Player owner;

    Property (int price, boolean mortaged, int mortageValue){
        super(id);
        super(description);

        this.price = mortageValue*2;
        this.mortaged = false;
        this.mortageValue = mortageValue;
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
