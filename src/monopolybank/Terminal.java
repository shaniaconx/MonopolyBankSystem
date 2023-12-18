package monopolybank;

import java.io.IOException;

abstract class Terminal {
    private TranslatorManager tManager;
    Terminal(){
        TranslatorManager tManager = new TranslatorManager();
    }
    public abstract int read();
    public abstract void show(String s);
    public TranslatorManager getTranslatorManager() {
        return tManager;
    }
}
