package monopolybank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

enum Color {
    red,
    green,
    blue,
    black
}

public class Player implements Serializable {
    private final Color color;
    private final String name;
    private int balance = 1500;
    private ArrayList<Property> properties;
    private Terminal terminal;

    public Player (Color c, String n){
        this.color = c;
        this.name = n;
    }

    @Override
    public String toString() {
        return "Player{" +
                "color=" + color +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public void pay (int amount, boolean mandatory){

    }

    public void setBankrupt(){}

    private void sellActives(Player target, boolean mandatory){}

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperty(Property p) {
        this.properties.add(p);
        p.setOwner(this);
    }

    public void traspaseProperties(Player newOwner){
        for (Property p: this.properties) {
            p.setOwner(newOwner);
        }
    }
}
