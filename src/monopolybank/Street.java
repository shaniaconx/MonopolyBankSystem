package monopolybank;

import java.util.ArrayList;

public class Street extends Property {
    private int builtHouses;
    private boolean builtHotel;
    private final int housePrice;
    private final ArrayList<Integer> costStayingWithHouses;
    private final Terminal terminal;

    /**
     * Constructor para la clase Street.
     *
     * @param code      Código que representa la información de la calle.
     * @param terminal  Terminal para interactuar con el usuario.
     */
    Street(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        this.builtHouses = 0;
        this.builtHotel = false;
        this.housePrice = parseHousePrice(code);
        this.terminal = terminal;
        costStayingWithHouses = new ArrayList<>();
        for (int i = 3; i < 9  ; i++){
            costStayingWithHouses.add(parseCostStayingWithHouses(i, code));
        }
    }

    /**
    * Extrae y devuelve el coste a pagar por caer en una calle a partir de especificarle una parte de una cadena de texto.
    * 
    * @param code La cadena de texto que contiene los datos.
    * @return El precio a pagar por caer en la propiedad como un entero.
    * @throws NumberFormatException si la parte coste por caer en la cadena no es un número entero válido.
    */
    private int parseCostStayingWithHouses(int part, String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[part]);
    }

    /**
    * Extrae y devuelve el coste de comprar una casa u hotel a partir de una cadena de texto.
    * 
    * @param code La cadena de texto que contiene los datos.
    * @return El precio de las casas y hoteles de la propiedad como un entero.
    * @throws NumberFormatException si la parte del precio en la cadena no es un número entero válido.
    */
    private int parseHousePrice(String code){
        String[] parts = code.split(";");
        return Integer.parseInt(parts[9]);
    }

    /**
     * Calcula el pago por alquiler basado en el número de casas y si hay hotel construido.
     *
     * @return El pago calculado por alquiler.
     */
    @Override
    public int getPaymentForRent() {
        if (this.builtHotel){
            return this.costStayingWithHouses.get(5);
        } else {
            return this.costStayingWithHouses.get(builtHouses);
        }
    }

    /**
    * Realiza operaciones específicas del propietario en una propiedad de tipo Street.
    * Las operaciones incluyen hipotecar la propiedad, comprar casas/hoteles y vender casas/hoteles.
    * La elección de la operación se realiza mediante una entrada de usuario a través del terminal.
    */
    @Override
    public void doOwnerOperations (){
        terminal.show("owner_operations_street");
        int choice = terminal.read();

        switch (choice) {
            case 1:
                //Mortgaging Operations
                this.showMortgageSummary();
                acceptCancel(this::mortgagingOperations, false);
                break;
            case 2:
                //Buy Houses/Hotels
                this.buyHouses();
                break;
            case 3:
                //Sell Houses/Hotel
                this.sellHouses(false);
                break;
            default:
                terminal.show("canceled");
                break;
        }
    }

    /**
    * Vende casas o un hotel de esta propiedad. 
    * Si la venta es obligatoria (mandatory), se ejecutará sin la opción de cancelar.
    * La cantidad de casas a vender se determina mediante la entrada del usuario y el total 
    * se paga al propietario. Si se vende un hotel, se elimina el hotel de la propiedad.
    *
    * @param mandatory Indica si la venta es obligatoria.
    */
    public void sellHouses(boolean mandatory){
        Player owner = getOwner();

        if(this.builtHotel){
            terminal.show("sell_hotel", this.getDescription(), this.housePrice);

            int done = acceptCancel(() -> owner.getPaid(this.housePrice), mandatory);
            if(done == 1){
                setBuiltHotel(false);
            }
        } else if (this.hasBuildings()){
            terminal.show("number_houses_toSell", this.builtHouses, this.getDescription());
            int quantity;
            do {
                quantity = terminal.read();
                if(quantity <= builtHouses){
                    terminal.show("number_of_houses_error");
                }
            }while (quantity <= builtHouses);

            int totalPrice = this.housePrice * quantity;
            terminal.show("sell_houses", quantity, totalPrice);
            int done = acceptCancel(() -> owner.getPaid(totalPrice), mandatory);
            if(done == 1){
                setBuiltHouses(builtHouses-quantity);
            }
        }
    }

    /**
    * Permite al propietario comprar casas o un hotel para esta propiedad.
    * La compra de casas está limitada a un máximo de 4, y luego se puede optar por un hotel.
    * La decisión de comprar y la cantidad se determinan mediante la entrada del usuario.
    */
    private void buyHouses(){
        Player owner = getOwner();
        if (!builtHotel && builtHouses == 4){
            terminal.show("buy_hotel", this.getDescription(), this.housePrice);
            int done = acceptCancel(() -> owner.pay(this.housePrice, false), false);
            if (done == 1){
                this.setBuiltHotel(true);
                this.setBuiltHouses(0);
            }
        } else if (!this.builtHotel && builtHouses < 4){
            terminal.show("number_houses_toBuy", this.builtHouses, this.getDescription());
            int quantity;
            do {
                quantity = terminal.read();
                if((quantity + builtHouses) > 4){
                    terminal.show("number_of_houses_error");
                }
            }while ((quantity + builtHouses) > 4);

            int totalHousePrice = housePrice * quantity;
            terminal.show("buy_houses", quantity, totalHousePrice);
            int done = acceptCancel(() -> owner.pay(totalHousePrice, false), false);
            if (done == 1){
                this.setBuiltHouses(quantity + builtHouses);
            }
        }
    }

    /**
    * Verifica si el jugador tiene un hotel y si no tiene verifica si el jugador posee alguna casa.
    *
    * @return true si el jugador tiene un hotel o alguna casa, false en caso contrario.
    */
    public boolean hasBuildings(){
        if (this.builtHotel) {
            return true;
        }
        return this.builtHouses > 0;
    }
    
    /**
    * Verifica si el jugador tiene un hotel .
    *
    * @return true si el jugador tiene un hotel, false en caso contrario.
    */
    public boolean isBuiltHotel() {
        return builtHotel;
    }

    //Getters y setters de atributos.
    public void setBuiltHouses(int builtHouses) {
        this.builtHouses = builtHouses;
    }

    public void setBuiltHotel(boolean builtHotel) {
        this.builtHotel = builtHotel;
    }

    public int getBuiltHouses() {
        return builtHouses;
    }

}
