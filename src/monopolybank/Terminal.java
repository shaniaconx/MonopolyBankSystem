package monopolybank;

public class Terminal {
    Terminal(){};

    public int read(){};
    public void  show(String msg){};
    public TranslatorManager getTranslatorManager(){
        TranslatorManager t = new TranslatorManager();
        return t;
    }
}
