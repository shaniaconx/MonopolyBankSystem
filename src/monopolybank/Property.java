package monopolybank;

import java.util.function.Supplier;

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
        terminal.show("Menú de operaciones del propietario.\n¿Qué desea hacer?\n 1. Gestionar Hipoteca\n 2. Cancelar");
        int choice = terminal.read();

        if (choice == 1) {
            this.showMortgageSummary();
            acceptCancel(this::mortgagingOperations, false);
        }else{
            terminal.show("Operación cancelada.");
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
            terminal.show("Se va a deshipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
        } else {
            terminal.show("Se va a hipotecar la propiedad " + this.description + " por parte del jugador " + this.owner.getColor() + " por un importe de " + this.mortgageValue + "€.\n 1.Aceptar\n 2.Cancelar");
        }
    }

    public void showPaymentSummary(int amount, Player p){
        terminal.show("El jugador " + p.getColor() + " usará la propiedad " + this.description + ". Por ello, pagará " + amount + "€ al jugador " + this.getOwner().getColor() +"\n 1.Aceptar\n 2.Cancelar");
    }

    public void showPurchaseSummary(int amount, Player p){
        terminal.show("Se va a realizar la compra de la propiedad " + this.description + " por parte del jugador " + p.getColor() + " por un importe de " + amount + "€.\n 1.Aceptar\n 2.Cancelar");
    }

    public static boolean acceptCancel(Supplier<Boolean> operation, boolean mandatory){
        if (mandatory){
            terminal.show("Esta operación es obligatoria.");
            return operation.get();
        } else {
            int choice;
            do {
                choice = terminal.read();
                switch (choice) {
                    case 1:
                        return operation.get();
                    case 2:
                        terminal.show("Operación cancelada.");
                        return false;
                    default:
                        terminal.show("Por favor, seleccione una de las opciones propuestas.");
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
