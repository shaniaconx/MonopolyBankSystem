package monopolybank;

import java.io.Serializable;

public class MonopolyCode implements Serializable {
    private int id;
    private String description;
    private Terminal terminal;

    MonopolyCode(int id, String description){
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "MonopolyCode: ID: " + id +
                "Descripción: " + description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void doOperation(Player p){
        //todo personalized code in each child class
    }
}
