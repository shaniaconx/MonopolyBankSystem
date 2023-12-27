package monopolybank;

public class Transport extends Property{
    private final int[] costStaying = {25, 50, 75, 100};

    Transport(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, 100*2, false, 100);
    }
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
    private static String parseClass(String code){
        String[] parts = code.split(";");
        return parts[1];
    }

    private void showPaymentSummary(int amount, Player p){
        terminal.show("El jugador " + p.getColor() + " usará la propiedad " + this.description + ". Por ello, pagará " + amount + "€ al jugador " + this.getOwner().getColor() +"\n 1.Aceptar\n 2.Cancelar");
    }

    private void showPurchaseSummary(int amount, Player p){
        terminal.show("Se va a realizar la compra de la propiedad " + this.description + " por parte del jugador " + p.getColor() + " por un importe de " + amount + "€.\n 1.Aceptar\n 2.Cancelar");
        int choice = terminal.read();
        do {
            switch (choice) {
                case 1:
                    //accept
                    break;
                case 2:
                    //cancel
                    break;
                default:
                    terminal.show("Por favor, seleccione una de las opciones propuestas.");
            }
        }while (choice != 1 || choice != 2);
    }

    @Override
    public int getPaymentForRent(){
        int rent = 0;
        Player actualOwner = this.getOwner();
        for (Property p: actualOwner.getProperties()) {
            String actualClass = p.getPropertyClass();
            if (this.getPropertyClass().equals(actualClass)){
                rent++;
            }
        }
        return costStaying[rent];
    }

    @Override
    public void doOwnerOperations() {
        terminal.show("¿Quieres hipotecar la propiedad?\n 1.Si\n 2.No");
        int choice = terminal.read();
        if(choice == 1){
            setMortgaged(true);
        }
    }

    @Override
    public void doOperation(Player p) {
        Player actualOwner = this.getOwner();
        if (actualOwner == null){
            p.pay(this.getPrice(), false);
        } else if (actualOwner.equals(p)) {
            this.doOwnerOperations();
        } else { //!actualOwner.equals(p)
            int rentToPay = this.getPaymentForRent();
            p.pay(rentToPay, true);
        }
    }
}
