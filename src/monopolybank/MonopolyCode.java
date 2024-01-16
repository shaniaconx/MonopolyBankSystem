package monopolybank;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.function.Supplier;

abstract class MonopolyCode implements Serializable {
    private final int id;
    private final String description;
    private final Terminal terminal;

    /**
     * Constructor de MonopolyCode. Inicializa un código de Monopoly con su ID, descripción y terminal.
     */
    MonopolyCode(int id, String description, Terminal terminal){
        this.id = id;
        this.description = description;
        this.terminal = terminal;
    }

     /**
     * Sobrescribe el método toString para proporcionar una representación en cadena personalizada.
     */
    @Override
    public String toString() {
        return terminal.getTranslatorManager().getTranslator().translate("mcode_toString", this.id, this.description);
    }

    // Getters para los atributos
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * Método abstracto que define una operación a realizar con un jugador.
     */
    public abstract int doOperation(Player p);
    
    /**
     * Maneja la aceptación o cancelación de una operación, permitiendo al jugador elegir.
     *
     * @param operation La operación a ejecutar si se acepta.
     * @param mandatory Indica si la operación es obligatoria.
     * @return El resultado de la operación o -1 si se cancela.
     */
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

        return choice; 
    }

}
