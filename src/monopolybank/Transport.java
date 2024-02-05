package monopolybank;

import java.util.ArrayList;

public class Transport extends Property{
    private final ArrayList<Integer> costStaying;

    /**
     * Constructor para la clase Transport..
     *
     * @param code Código que contiene la información necesaria para inicializar la propiedad.
     * @param terminal Terminal que se utiliza para la interacción con el usuario.
     */
    Transport(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, parseMortgageValue(code)*2, false, parseMortgageValue(code));

        costStaying = new ArrayList<>();
        for (int i = 3; i < 7; i++){
            costStaying.add(parseCostStaying(i, code));
        }
    }

    /**
     * Calcula el pago por renta de la propiedad de transporte.
     * El pago depende del número de propiedades de transporte que posea el propietario.
     *
     * @return El costo de la estancia en función del número de propiedades de transporte que posee el propietario.
     */
    @Override
    public int getPaymentForRent(){
        int rent = 0;
        Player actualOwner = this.getOwner();
        for (Property p: actualOwner.getProperties()) {
            if (p instanceof Transport){
                rent++;
            }
        }
        return costStaying.get(rent-1);
    }


}
