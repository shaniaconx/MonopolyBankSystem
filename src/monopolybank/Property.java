package monopolybank;

abstract class Property extends MonopolyCode{
    private String className;
    private int price;
    private boolean mortgaged;
    private int mortgageValue;
    private Player owner;
    private final Terminal terminal;

    /**
     * Constructor para inicializar una propiedad con sus atributos básicos.
     *
     * @param id             El identificador único de la propiedad.
     * @param className      El nombre de la clase de la propiedad.
     * @param description    La descripción de la propiedad.
     * @param terminal       La interfaz de terminal para interactuar con el usuario.
     * @param price          El precio de compra de la propiedad.
     * @param mortgaged      Estado hipotecario inicial de la propiedad.
     * @param mortgageValue  El valor de la hipoteca de la propiedad.
     */
    Property (int id, String className, String description, Terminal terminal, int price, boolean mortgaged, int mortgageValue){
        super(id, description, terminal);
        this.price = price;
        this.mortgaged = mortgaged;
        this.mortgageValue = mortgageValue;
        this.terminal = terminal;
        setOwner(null);
        setClassName(className);
    }

    //Getters y setters de los atributos.
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public boolean getMortgaged(){
        return mortgaged;
    }
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }
    public int getMortgageValue(){
        return mortgageValue;
    }
    private void setClassName(String name){
        this.className = name;
    }
    public int getPrice() {
        return price;
    }

    /**
     * Método abstracto para calcular el pago por alquiler de la propiedad.
     * 
     * @return El monto del alquiler a pagar.
     */
    public abstract int getPaymentForRent();

     /**
     * Realiza la operación correspondiente cuando un jugador interactúa con la propiedad.
     * 
     * @param p El jugador que interactúa con la propiedad.
     * @return Un entero que representa si la operación se ha realizado correctamente.
     */
    @Override
    public int doOperation(Player p) {
        Player actualOwner = this.getOwner();
        if (actualOwner == null){
            this.showPurchaseSummary(this.getPrice(), p);
            int result = acceptCancel(() -> p.pay(this.getPrice(), false), false);
            if (result == 1) {
                this.setOwner(p);
                p.addProperty(this);
            }
            return result;
        } else if (actualOwner.equals(p)) {
            this.doOwnerOperations();
            return 1;
        } else { //!actualOwner.equals(p)
            int rentToPay = this.getPaymentForRent();
            this.showPaymentSummary(rentToPay, p);
            int result = acceptCancel(() -> p.pay(rentToPay, true), true);
            if(result == 1){
                actualOwner.getPaid(rentToPay);
                return 1;
            }else if (result == 0){
                p.traspaseProperties(actualOwner);
                return 0;
            }
            return result;
        }
    }

    /**
     * Muestra las operaciones disponibles para el dueño de la propiedad.
     */
    public void doOwnerOperations(){
        terminal.show("owner_operations_general");
        int choice = terminal.read();

        if (choice == 1) {
            this.showMortgageSummary();
            acceptCancel(this::mortgagingOperations, false);
        }else{
            terminal.show("canceled");
        }
    }

    /**
     * Realiza operaciones de hipoteca en la propiedad.
     * 
     * @return Un entero que representa si la operación de hipoteca se ha realizado correctamente.
     */
    protected int mortgagingOperations (){
        if(this.mortgaged){
            return this.owner.pay(this.mortgageValue, false);
        } else {
            return this.owner.getPaid(this.mortgageValue);
        }
    }

     /**
     * Muestra un resumen de la hipoteca de la propiedad.
     */
    protected void showMortgageSummary(){
        String owner = terminal.getTranslatorManager().getTranslator().translate(this.getOwner().getColor().toString());
        if(mortgaged){
            terminal.show("property_unmortgage",this.getDescription(), owner, this.mortgageValue);
        } else {
            terminal.show("property_mortgage",this.getDescription(), owner, this.mortgageValue);
        }
    }

    /**
     * Muestra un resumen del pago de alquiler.
     * 
     * @param amount La cantidad de alquiler a pagar.
     * @param p El jugador que paga el alquiler.
     */
    public void showPaymentSummary(int amount, Player p){
        String player = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        String owner = terminal.getTranslatorManager().getTranslator().translate(this.getOwner().getColor().toString());
        terminal.show("property_rent_payment", player, this.getDescription(), amount, owner);
    }

    /**
     * Muestra un resumen de la compra de la propiedad.
     *
     * @param amount La cantidad a pagar por la compra de la propiedad.
     * @param p El jugador que está comprando la propiedad.
     */
    public void showPurchaseSummary(int amount, Player p){
        String player = terminal.getTranslatorManager().getTranslator().translate(p.getColor().toString());
        terminal.show("property_purchase_payment", this.getDescription(),player, amount);
    }

    /**
    * Analiza y devuelve el costo de permanencia en la propiedad a partir de un código.
    *
    * @param part Índice de la parte del código a analizar.
    * @param code Código de la propiedad.
    * @return Costo de permanencia.
    */
    protected int parseCostStaying(int part, String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[part]);
    }
 
    /**
    * Analiza y devuelve el nombre de la clase de la propiedad a partir de un código.
    *
    * @param code Código de la propiedad.
    * @return Nombre de la clase.
    */
    protected static String parseClass(String code){
        String[] parts = code.split(";");
        return parts[1];
    }

    /**
    * Analiza y devuelve el valor de hipoteca de la propiedad a partir de un código.
    *
    * @param code Código de la propiedad.
    * @return Valor de hipoteca.
    */
    protected static int parseMortgageValue(String code){
        String[] parts = code.split(";");
        return Integer.parseInt(parts[parts.length - 1]);
    }


    /**
     * Extrae y devuelve el ID de una propiedad a partir de una cadena de texto.
     *
     * @param code La cadena de texto que contiene el ID de la propiedad y posiblemente otros datos, separados por punto y coma.
     * @return El ID de la propiedad como un entero.
     * @throws NumberFormatException si la parte del ID en la cadena no es un número entero válido.
     */
    protected static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }

    /**
     * Extrae y devuelve la descripción de una propiedad, que a veces puede ser el nombre de la propiedad o la acción de esta, a partir de una cadena de texto.
     *
     * @param code La cadena de texto que contiene los datos.
     * @return La descripcion o nombre de la propiedad como un String.
     */
    protected static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
}
