package monopolybank;

import java.io.Serializable;

abstract class MonopolyCode implements Serializable {
    protected int id;
    protected String description;
    protected static Terminal terminal;

    MonopolyCode(int id, String description, Terminal terminal){
        this.id = id;
        this.description = description;
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return id + ": " + description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public abstract boolean doOperation(Player p);
}
