package monopolybank;

import java.io.IOException;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.function.Supplier;

abstract class MonopolyCode implements Serializable {
    private final int id;
    private final String description;
    private final Terminal terminal;

    MonopolyCode(int id, String description, Terminal terminal){
        this.id = id;
        this.description = description;
        this.terminal = terminal;
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

    public Terminal getTerminal() {
        return terminal;
    }

    public abstract int doOperation(Player p);
    public int acceptCancel(Supplier<Integer> operation, boolean mandatory){
        if (mandatory){
            terminal.show("mandatory");
            return operation.get();
        }
        int choice = terminal.read();

        try{
            if (choice == 1){
                return operation.get();
            } else if (choice == 2) {
                terminal.show("cancelled");
                return -1;
            }
        }catch (InputMismatchException e){
            terminal.show("error_choosing");
        }

        return choice; //todo ?
    }
}
