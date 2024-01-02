package monopolybank;

import java.io.Serializable;
import java.util.ArrayList;

enum Color {
    red(1), green(2), blue(3), black(4);
    private final int value;
    Color(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static Color association(int value){
        for (Color c: values()){
            if (c.getValue() == value){
                return c;
            }
        }
        return black;
    }

    @Override
    public String toString() {
        switch (this) {
            case red: return "Rojo";
            case green: return "Verde";
            case blue: return "Azul";
            default: return "Negro";
        }

    }
}

public class Player implements Serializable {
    private final int id;
    private final Color color;
    private int balance = 1500;
    private ArrayList<Property> properties = null;
    private boolean bankrupt;
    private Terminal terminal;

    Player (int id, Terminal terminal){
        this.id = id;
        color = Color.association(id);
        this.balance = 1500;
        this.properties = null;
        this.bankrupt = false;
        this.terminal = terminal;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() { //todo translator??
        return "Jugador " + color.toString() + ": " + "Dinero =" + balance;
    }

    public int getBalance() {
        return balance;
    }

    public void getPaid (int balance) {
        this.balance += balance;
    }

    public boolean pay (int amount, boolean mandatory){
        if (hasEnoughMoney(amount)){
            balance -= amount;
            return true;
        }
        if (!hasEnoughMoney(amount) && mandatory) {
            this.setBankrupt(true);
            while (!hasEnoughMoney(amount) && thereAreThingsToSell()){
                sellActives(this, true);
            }

            if (hasEnoughMoney(amount)){
                setBankrupt(false);
                balance -= amount;
                return true;
            } else {
                return false;
            }
        }
        //!hasEnoughMoney(amount) && !mandatory
        terminal.show("No tienes dinero suficiente.");
        return false;
    }

    private boolean hasEnoughMoney(int amount) {
        return balance - amount > 0;
    }

    public void setBankrupt(boolean state){
        this.bankrupt = state;
    }
    //todo has houses es para street solo -> hacer ifs
    private void sellActives(Player actual, boolean mandatory){
        if (mandatory){
            if (this.getProperties() != null && thereAreThingsToSell()){
                for (Property p: properties){
                    if(p.hasHouses()){
                        //todo when Street class is done
                        //todo sellHouse()
                    }
                    if(!p.getMortgaged()){
                        p.setMortgaged(true);
                        actual.balance += p.getMortgageValue();
                    }
                }
            }
        }
    }

    private boolean thereAreThingsToSell(){
        for (Property p: this.properties){
            if (!p.getMortgaged() || p.hasHouses()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperty(Property p) {
        this.properties.add(p);
        p.setOwner(this);
    }

    //cuando pay == false y bankrupt -> traspasamos
    public void traspaseProperties(Player newOwner){
        for (Property p: this.properties) {
            p.setOwner(newOwner);
        }
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        // Check if o is an instance of Player
        if (!(o instanceof Player)) {
            return false;
        }
        // typecast o to Player so that we can compare
        Player c = (Player) o;

        return getColor().equals(c.getColor());
    }
}
