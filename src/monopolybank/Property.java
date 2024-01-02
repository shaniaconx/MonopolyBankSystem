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
        int choice;
        if(mortgaged){
            do {
                terminal.show("Se va a deshipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
                choice = terminal.read();
                switch (choice){
                    case 1:
                        this.owner.getPaid(this.mortgageValue);
                        break;
                    case 2:
                        terminal.show("Operación cancelada.");
                        break;
                    default:
                        terminal.show("Por favor, seleccione una de las opciones propuestas.");
                        break;
                }
            }while(choice != 1 || choice !=2);
        } else {
            do {
                terminal.show("Se va a hipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
                choice = terminal.read();
                switch (choice){
                    case 1:
                        this.owner.pay(this.mortgageValue, false);
                        break;
                    case 2:
                        terminal.show("Operación cancelada.");
                        break;
                    default:
                        terminal.show("Por favor, seleccione una de las opciones propuestas.");
                        break;
                }
            }while(choice != 1 || choice!= 2);
        }

    }

    public abstract int getPaymentForRent();

    public void doOwnerOperations(){
        terminal.show("Menú de operaciones del propietario.\n¿Qué desea hacer?\n 1. Gestionar Hipoteca\n 2. Cancelar");
        int choice = terminal.read();

        if (choice == 1) {
            this.showMortgageSummary();
        }else{
            terminal.show("Operación cancelada.");
        }
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
