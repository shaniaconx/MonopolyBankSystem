package monopolybank;

public class Service extends Property{
    private final int[] costStaying = {4, 10};

    Service(String code, Terminal terminal){
        super(parseId(code), parseClass(code), parseDescription(code), terminal, 75*2, false, 75);
    }
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }

    private static String parseClass(String code){
        String[] parts = code.split(";");
        return parts[1];
    }

    @Override
    public int getPaymentForRent(){
        int rent = 0;
        super.terminal.show("¿Qué número has sacado en los dados?");
        int num = super.terminal.read();
        Player actualOwner = this.getOwner();
        for (Property p: actualOwner.getProperties()) {
            String actualClass = p.getPropertyClass();
            if (this.getPropertyClass().equals(actualClass)){
                rent++;
            }
        }
        return num*costStaying[rent];
    }

    @Override
    public void doOwnerOperations() {

    }

    @Override
    public void doOperation(Player p) {

    }
}
