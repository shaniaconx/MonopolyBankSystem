package monopolybank;

abstract class Property extends MonopolyCode{
    private String className;
    private int price;
    private boolean mortgaged;
    private int mortgageValue;
    private Player owner;

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

    private void showMortgageSummary(){
        if(mortgaged){
            terminal.show("Se va a deshipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
        } else {
            terminal.show("Se va a hipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
        }

    }

    public abstract int getPaymentForRent();

    public abstract void doOwnerOperations();  //todo specified in each child class i think -> super.mortgage
}
