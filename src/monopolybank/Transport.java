package monopolybank;

import java.util.ArrayList;

public class Transport extends Property{
    private final ArrayList<Integer> costStaying;

    Transport(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        costStaying = new ArrayList<>();
        for (int i = 3; i < 7; i++){
            costStaying.add(parseCostStaying(i, code));
        }
    }

    private void showPaymentSummary(int amount, Player p){
        terminal.show("El jugador " + p.getColor() + " usará la propiedad " + this.description + ". Por ello, pagará " + amount + "€ al jugador " + this.getOwner().getColor() +"\n 1.Aceptar\n 2.Cancelar");

        int choice = terminal.read();
        do {
            switch (choice) {
                case 1:
                    p.payOtherPlayer(this.getOwner(), amount);
                    break;
                case 2:
                    //cancel
                    break;
                default:
                    terminal.show("Por favor, seleccione una de las opciones propuestas.");
            }
        }while (choice != 1 || choice != 2);
    }

    private void showPurchaseSummary(int amount, Player p){
        terminal.show("Se va a realizar la compra de la propiedad " + this.description + " por parte del jugador " + p.getColor() + " por un importe de " + amount + "€.\n 1.Aceptar\n 2.Cancelar");
        int choice = terminal.read();
        do {
            switch (choice) {
                case 1:
                    p.pay(amount, false);
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
        return costStaying.get(rent);
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

    @Override
    public void acceptCancel(int choice){

    }
}
