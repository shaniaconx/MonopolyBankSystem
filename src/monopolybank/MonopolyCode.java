package monopolybank;

import java.io.Serializable;
import java.util.function.Supplier;

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
    public static boolean acceptCancel(Supplier<Boolean> operation, boolean mandatory){
        if (mandatory){
            terminal.show("mandatory");
            return operation.get();
        } else {
            int choice;
            do {
                choice = terminal.read();
                switch (choice) {
                    case 1:
                        return operation.get();
                    case 2:
                        terminal.show("cancelled");
                        return false;
                    default:
                        terminal.show("error_choosing");
                        break;
                }
            }while(choice != 1 || choice != 2);
        }
        return false;
    }
}
