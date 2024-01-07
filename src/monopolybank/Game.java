package monopolybank;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

import static monopolybank.Constants.*;

public class Game implements Serializable {
    //List <MonopolyCode> codes = null;
    private Map<Integer, MonopolyCode> codes = null;
    private Map<Integer, Player> players = null;
    private Terminal terminal;

    Game (Terminal terminal){
        this.terminal = terminal;
        this.createPlayers();
        this.loadMonopolyCodes(CONFIG_CODE);
    }

    private void loadMonopolyCodes (String fileName){
        try {
            this.codes = new HashMap<Integer, MonopolyCode>();
            Scanner myReader = new Scanner(fileName);

            while (myReader.hasNextLine()) {
                String actualLine = myReader.nextLine();
                String type = getCodeClass(actualLine);

                MonopolyCode newCode = null;

                switch (type) {
                    case "STREET":
                        newCode = new Street(actualLine, terminal);
                        break;
                    case "PAYMENT_CHARGE_CARD":
                        newCode = new PaymentCharge(actualLine, terminal);
                        break;
                    case "TRANSPORT":
                        newCode = new Transport(actualLine, terminal);
                        break;
                    case "SERVICE":
                        newCode = new Service(actualLine, terminal);
                        break;
                    case "REPAIRS_CARD":
                        newCode = new RepairsCard(actualLine, terminal);
                        break;
                    default:
                        terminal.show("code_not_found");
                }

                int key = newCode.getId();
                this.codes.put(key, newCode);
            }
            myReader.close();
        } catch (FileNotFoundException e){
            terminal.show("load_files_error");
            e.printStackTrace();
        }
    }
    private String getCodeClass(String line){
        String[] parts = line.split(";");
        return parts[1];
    }

    public void play(){
        while (players.size() > 1) {
            terminal.show("card_code");
            int cardCode = terminal.read();

            terminal.show("player_color");
            int playerCode = terminal.read();

            Player actualPlayer = players.get(playerCode);
            MonopolyCode actualCard = codes.get(cardCode);

            boolean result = actualCard.doOperation(actualPlayer);
            if (!result){
                removePlayer(playerCode);
            }
        }
        //end game
        Map.Entry<Integer, Player> winnerEntry = players.entrySet().iterator().next();
        Player winner = winnerEntry.getValue();
        String winnerColor = winner.getColor().toString();
        terminal.show("winner", winnerColor);
    }

    private void createPlayers(){
        int numPlayers;
        do {
            terminal.show("number_of_players");
            numPlayers = terminal.read();
            if (numPlayers == 1 || numPlayers > 4){
                terminal.show("number_of_players_error");
            }
        } while (numPlayers == 1 || numPlayers > 4);

        for (int i = 0; i < numPlayers; i++){
            int playerId;
            do{
                terminal.show("select_color");
                playerId = terminal.read();
                if (players.containsKey(playerId)){
                    terminal.show("color_choosen");
                }
            }while (players.containsKey(playerId));
            players.put(playerId, new Player(playerId, this.terminal));
        }
    }

    private void removePlayer(int playerId){
        this.players.remove(playerId);
        Player eliminated = players.get(playerId);
        String colorEliminated = terminal.getTranslatorManager().getTranslator().translate(eliminated.getColor().toString());
        terminal.show("player_elimination", colorEliminated);
    }
}
