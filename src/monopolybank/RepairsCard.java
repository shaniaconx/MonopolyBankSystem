package monopolybank;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static monopolybank.Constants.*;

public class RepairsCard extends MonopolyCode{
    private int amountHouse;
    private int amountHotel;
    private final Terminal terminal;
    

    /**
     * Constructor de la tarjeta de reparaciones.
     * 
     * Este constructor inicializa una tarjeta de reparaciones, extrayendo los costos asociados con las casas y hoteles
     * a partir de la descripción proporcionada en el código.
     *
     * @param code El código de la tarjeta, que contiene los detalles necesarios.
     * @param terminal La terminal a través de la cual se comunica con el usuario.
     */
    RepairsCard(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal);
        this.terminal = terminal;
        Matcher moneyFinder = PATTERN.matcher(parseDescription(code)); //finds the cuantity used in the description
        int counter = 0;
        while (moneyFinder.find()){
            String moneyString = moneyFinder.group();
            int number = Integer.parseInt(moneyString.replace("â‚¬", "").trim());
                
            if (counter == 0){
                this.amountHouse = number;
            } else if (counter == 1) {
                this.amountHotel = number;
            }
            counter++;
        }
    }

    public int getAmountHouse() {
        return amountHouse;
    }

    public int getAmountHotel() {
        return amountHotel;
    }

    /**
    * Realiza la operación de cobro por casas y hoteles en las propiedades de un jugador.
    *
    * Este método calcula el total a pagar por el jugador en función de la cantidad de casas y hoteles
    * que posee en sus propiedades. Para cada propiedad del tipo Street, se verifica si tiene un hotel
    * o casas construidas, y se suman las cantidades respectivas. Luego se calcula el total a pagar
    * por las casas y hoteles, se muestra un resumen y se procede con la operación de pago.
    *
    * Si el jugador acepta el pago, se realiza el cobro. Si el jugador no puede realizar el pago por no tener dinero,
    * se traspasan sus propiedades al banco.
    *
    * @param p El jugador sobre el cual se realiza la operación.
    * @return Un entero que representa el resultado de la operación, es decir, si se ha realizado exitosamente o
    * si no se ha podido realizar.
    */
    @Override
    public int doOperation(Player p){
        ArrayList<Property> playersProperties = p.getProperties();
        int playerHouses = 0;
        int playerHotels = 0;
        for (Property unit : playersProperties) {
            if (unit instanceof Street) {
                Street street = (Street) unit;
                if (street.isBuiltHotel()) {
                    playerHotels += 1;
                } else {
                    playerHouses += street.getBuiltHouses();
                }
            }
        }
        int totalForHouses = playerHouses * getAmountHouse();
        int totalForHotel = playerHotels * getAmountHotel();
        int totalPayment = totalForHotel + totalForHouses;
        this.showSummary(p, playerHouses, totalForHouses, playerHotels, totalForHotel, totalPayment);

        int result = acceptCancel(() -> p.pay(totalPayment, true), true);
        if(result == 0){
            p.traspaseProperties(null);
        }
        return result;
    }

   
   /**
    * Muestra un resumen de los pagos por reparaciones.
    * 
    * @param p El jugador que realiza el pago.
    * @param playerHouses Número de casas que posee el jugador.
    * @param totalForHouses Coste total de reparaciones para las casas.
    * @param playerHotels Número de hoteles que posee el jugador.
    * @param totalForHotels Coste total de reparaciones para los hoteles.
    * @param totalPayment Pago total que el jugador debe realizar.
    */
    private void showSummary(Player p, int playerHouses, int totalForHouses, int playerHotels, int totalForHotels, int totalPayment){
        String playersColor = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        terminal.show("repairs_payment", this.getDescription(), playersColor, playerHouses, playerHotels, totalForHouses, totalForHotels, totalPayment);
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
