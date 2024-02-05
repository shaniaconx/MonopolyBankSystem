package monopolybank;

abstract class Terminal {
    private TranslatorManager translatorManager;

    /**
     * Constructor para inicializar la Terminal con un TranslatorManager.
     */
    Terminal() {
        this.translatorManager = new TranslatorManager();
    }

     /**
     * Muestra un mensaje al usuario. La clave del mensaje se especifica mediante 'key',
     * y cualquier argumento adicional se pasa a través de 'args'.
     *
     * @param key La clave del mensaje a mostrar, normalmente utilizada para obtener el mensaje
     *  del gestor de traducciones.
     * @param args Argumentos opcionales que se pueden incluir en el mensaje.
     */
    public abstract void show(String key, Object... args);
    
     /**
     * Lee una entrada del usuario. El tipo específico de entrada y cómo se maneja depende
     * de la implementación en las subclases.
     *
     * @return Devuelve el valor entero leído desde la entrada del usuario.
     */
    public abstract int read();

     /**
     * Obtiene el gestor de traducciones asociado con esta Terminal.
     *
     * @return El objeto TranslatorManager asociado.
     */
    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }
}
