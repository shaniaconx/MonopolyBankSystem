package monopolybank;

public class Property extends MonopolyCode{
    private final int price;
    private boolean mortaged;
    private final int mortageValue;
    private Player owner;

    Property (String code){

        super(0, ""); //initialize or else can't split string first

        String [] parts = code.split(";");
        super.setId(Integer.parseInt(parts[0]));
        super.setDescription(parts[2]);

        this.price = price;
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
