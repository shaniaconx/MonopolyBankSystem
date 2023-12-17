package monopolybank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Translator {
    private String language;
    private Map<String, String> dictionary;

    Translator(String dictionaryFileName){
        dictionary = new HashMap<>();
        setLanguage(dictionaryFileName.replaceFirst("[.]", ""));
        loadDictionary(dictionaryFileName);

    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage(){
        return this.language;
    }

    private void loadDictionary(String dictionaryRoute){
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryRoute))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 2) {
                    dictionary.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String translate(String original){
        return dictionary.get(original);
    }
}
