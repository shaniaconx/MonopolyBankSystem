package monopolybank;

import java.io.Serializable;

abstract class MonopolyCode implements Serializable {
    private int id;
    private String description;
    private static Terminal terminal;

    MonopolyCode(int id, String description, Terminal terminal){
        this.id = id;
        this.description = description;
        MonopolyCode.terminal = terminal;
    }

    @Override
    public String toString() {
        return terminal.getTranslatorManager().getTranslator().translate("mcode_toString", this.id, this.description);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Terminal getTerminal() {
        return terminal;
    }

    public abstract boolean doOperation(Player p);
}
