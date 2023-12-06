package monopolybank;

import java.io.Serializable;

public class MonopolyCode implements Serializable {
    private final int id;
    private final String description;

    MonopolyCode(int id, String description){
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "MonopolyCode{" +
                "Descripción='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void doOperation(Player p){
        //todo personalized code in each child class
    }
}
