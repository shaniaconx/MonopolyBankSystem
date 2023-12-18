package monopolybank;

public class Transport extends Property{
    private final int[] costStaying = {25, 50, 75, 100};

    Transport(String code, Terminal terminal){
        super(parseId(code), parseDescription(code), terminal, 100*2, false, 100);
    }
    private static int parseId(String code){
        String [] parts = code.split(";");
        return Integer.parseInt(parts[0]);
    }
    private static String parseDescription(String code) {
        String[] parts = code.split(";");
        return parts[2];
    }
}
