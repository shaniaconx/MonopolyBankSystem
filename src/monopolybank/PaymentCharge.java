package monopolybank;

import java.util.regex.Matcher;
import static monopolybank.Constants.*;

public class PaymentCharge extends MonopolyCode{
    private int amount;
    private final Terminal terminal;

    /**
     * Constructor que crea un objeto PaymentCharge.
     * 
     * @param code      El código en formato de texto que representa el cargo o pago. Debe contener el ID, la descripción y la cantidad de dinero.
     * @param terminal  Terminal para las interacciones de entrada/salida.
     */
    PaymentCharge(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal);

        this.terminal = terminal;
        Matcher moneyFinder = PATTERN.matcher(parseDescription(code)); // Utiliza la expresi?n regular correcta
        if (moneyFinder.find()) {
            String moneyString = moneyFinder.group();
            this.amount = Integer.parseInt(moneyString.replace("¤", "").trim());
        }
    }

    /**
     * Muestra un resumen de la operación de pago o cargo en el terminal.
     * 
     * @param p      El jugador que realiza o recibe el pago/cargo.
     * @param amount La cantidad de dinero involucrada en la operación.
     */
    private void showSummary(Player p, int amount){
        String playersColor = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        if(amount < 0){
            int positiveAmount = -1*amount;
            terminal.show("payment_charge_payment", this.getDescription(), playersColor, positiveAmount);
        } else if (amount > 0){
            terminal.show("payment_charge_charge", this.getDescription(), playersColor, amount);
        } else {
            terminal.show("payment_charge_noAmount", this.getDescription());
        }
    }

    /**
     * Realiza la operación de pago o cargo para el jugador especificado.
     * 
     * @param p El jugador sobre el cual se realizará la operación.
     * @return Un entero que representa el resultado de la operación.
     */
    @Override
    public int doOperation(Player p){
        this.showSummary(p, this.amount);
        if (this.amount < 0){
            int positiveAmount = -amount;
            int result = acceptCancel(() -> p.pay(positiveAmount, true), true);
            if(result == 0){
                p.traspaseProperties(null);
            }
            return result;
        } else {
            acceptCancel(() -> p.getPaid(this.amount), false);
            return 1;
        }
    }

    /**
     * Extrae y devuelve el ID de una propiedad a partir de una cadena de texto.
     *
     * @param code La cadena de texto que contiene el ID de la propiedad y posiblemente otros datos, separados por punto y coma.
     * @return El ID de la propiedad como un entero.
     * @throws NumberFormatException si la parte del ID en la cadena no es un número entero válido.
     */
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }

    /**
     * Extrae y devuelve la descripción de una propiedad, que a veces puede ser el nombre de la propiedad o la acción de esta, a partir de una cadena de texto.
     *
     * @param code La cadena de texto que contiene los datos.
     * @return La descripcion o nombre de la propiedad como un String.
     */
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
    
}
