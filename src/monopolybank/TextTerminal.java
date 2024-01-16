package monopolybank;
import java.io.Serializable;
import java.util.*;

public class TextTerminal extends Terminal implements Serializable{
    /**
     * Muestra un mensaje traducido en la consola de texto. El mensaje se obtiene
     * utilizando la clave proporcionada y, si es necesario, se formatea con los argumentos.
     * 
     * @param key La clave del mensaje a mostrar. Se utiliza para obtener el mensaje correspondiente del gestor de traducciones.
     * @param args Argumentos opcionales para formatear el mensaje.
     */
    @Override
    public void show(String key, Object... args) {
        String translatedMessage = getTranslatorManager().getTranslator().translate(key, args);
        System.out.println(translatedMessage);
    }

    /**
     * Lee una entrada numérica del usuario a través de la consola de texto.
     * Espera que el usuario introduzca un número entero y lo devuelve.
     * 
     * @return El número entero leído desde la entrada del usuario.
     */
    @Override
    public int read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

