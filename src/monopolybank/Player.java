package monopolybank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

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
}

public class Player implements Serializable {
    private final Color color;
    private final String name;
    private int balance = 1500;
    private ArrayList<Property> properties;
    private Terminal terminal;

    Player (Color c, String n){
        //todo
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
