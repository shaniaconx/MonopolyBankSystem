package monopolybank;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Enumeración que representa los colores disponibles.
 */
enum Color {
    red(1), green(2), blue(3), black(4);
    private final int value;
    
    /**
     * Constructor para el enum Color.
     *
     * @param value El valor numérico asociado con el color.
     */
    Color(int value){
        this.value = value;
    }
    
    /**
     * Obtiene el valor asociado con el color.
     *
     * @return El valor numérico del color.
     */
    public int getValue(){
        return value;
    }
    
    /**
     * Asocia un valor numérico con un color.
     *
     * @param value El valor numérico para buscar el color correspondiente.
     * @return El Color asociado con el valor numérico dado.
     */
    public static Color association(int value){
        for (Color c: values()){
            if (c.getValue() == value){
                return c;
            }
        }
        return black;
    }

    @Override
    public String toString() {
        return switch (this) {
            case red -> "red";
            case green -> "green";
            case blue -> "blue";
            default -> "black";
        };

    }
}

/**
 * Clase que representa a un jugador en el juego.
 */
public class Player implements Serializable {
    private final int id;
    private final Color color;
    private int balance;
    private ArrayList<Property> properties;
    private boolean bankrupt;
    private final Terminal terminal;

    /**
     * Constructor para la clase Player.
    *
    * @param id El identificador único del jugador.
    * @param terminal La terminal utilizada para interactuar con el jugador.
    */
    Player (int id, Terminal terminal){
        this.id = id;
        color = Color.association(id);
        this.balance = 1500;
        this.properties = new ArrayList<>();
        this.bankrupt = false;
        this.terminal = terminal;
    }

    //Getters y setters.
    public Color getColor() {
        return color;
    }
    public int getBalance() {
        return balance;
    }
    public void setBankrupt(boolean state){
        this.bankrupt = state;
    }
    public ArrayList<Property> getProperties() {
        return properties;
    }
    
    /**
    * Muestra la información del jugador en la terminal.
    */
    public void stringPlayerInfo() {
        String colorTranslated = terminal.getTranslatorManager().getTranslator().translate(getColor().toString());
        terminal.show("player_info", colorTranslated, getBalance());
        this.showProperties();
    }

    /**
    * Aumenta el balance del jugador con el monto especificado.
    *
    * @param balance El monto a añadir al balance del jugador.
    * @return Siempre retorna 1, pues siempre se es pagado si el jugador que paga ha podido pagar.
    */
       public int getPaid (int balance) {
        this.balance += balance;
        return 1;
    }

    /**
    * Intenta pagar un monto especificado. Si no es posible, maneja la bancarrota.
    *
    * @param amount El monto a pagar.
    * @param mandatory Indica si el pago es obligatorio.
    * @return 1 si el pago se realizó con éxito, 0 si el jugador se declaró en bancarrota, -1 si no puede pagar.
    */
    public int pay (int amount, boolean mandatory){
        if (hasEnoughMoney(amount)){
            balance -= amount;
            return 1;
        }
        if (!hasEnoughMoney(amount) && mandatory) {
            this.setBankrupt(true);
            while (!hasEnoughMoney(amount) && thereAreThingsToSell()){
                sellActives(this);
            }

            if (hasEnoughMoney(amount)){
                setBankrupt(false);
                balance -= amount;
                return 1;
            } else {
                return 0;
            }
        }
        //!hasEnoughMoney(amount) && !mandatory
        terminal.show("no_money");
        return -1;
    }

    /**
    * Verifica si el jugador tiene suficiente dinero.
    *
    * @param amount El monto a verificar.
    * @return true si el jugador tiene suficiente dinero, false en caso contrario.
    */
    private boolean hasEnoughMoney(int amount) {
        return balance - amount > 0;
    }

    /**
    * Vende activos del jugador para obtener liquidez.
    *
    * @param actual El jugador que está vendiendo los activos.
    */
    private void sellActives(Player actual){
        if (this.getProperties() != null && thereAreThingsToSell()){
            terminal.show("sell_actives_question");
            int propertyId= terminal.read();
            for (Property p: properties){
                if (propertyId == p.getId()){
                    if (p instanceof Street streetProperty) {
                        if (streetProperty.hasBuildings()) {
                            streetProperty.sellHouses(true);
                        }
                    }
                    if(!p.getMortgaged()){
                        p.setMortgaged(true);
                        actual.balance += p.getMortgageValue();
                    }
                }
            }
        }
    }

    /**
    * Verifica si hay cosas que el jugador puede vender.
    *
    * @return true si hay activos para vender, false en caso contrario.
    */
    private boolean thereAreThingsToSell(){
        for (Property p: this.properties){
            if (!p.getMortgaged()) {
                return true;
            }
            if(p instanceof Street streetProperty && streetProperty.hasBuildings()){
                return true;
            }
        }
        return false;
    }

    
    /**
     * Añade una propiedad a la lista de propiedades del jugador.
     *
     * @param property La propiedad a añadir.
     */
    public void addProperty(Property property) {
        this.properties.add(property);
    }

    /**
    * Elimina una propiedad de la lista de propiedades del jugador.
    *
    * @param property La propiedad a eliminar.
    */
    public void removeProperty(Property property) {
        properties.remove(property);
    }

    /**
    * Transfiere todas las propiedades del jugador a otro jugador.
    *
    * @param newOwner El nuevo propietario de las propiedades.
    */
    public void traspaseProperties(Player newOwner){
        for (Property p: this.properties) {
            p.setOwner(newOwner);
            newOwner.addProperty(p);
            this.removeProperty(p);
        }
    }

    /**
    * Muestra las propiedades del jugador en la terminal.
    */
    public void showProperties() {
        if (properties.isEmpty()) {
            terminal.show("no_properties");
            return;
        }

        terminal.show("player_properties");
        for (Property property : properties) {
            terminal.show("property", property.getDescription());
        }
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        // Check if o is an instance of Player
        if (!(o instanceof Player)) {
            return false;
        }
        // typecast o to Player so that we can compare
        Player c = (Player) o;

        return getColor().toString().equals(c.getColor().toString());
    }

}
