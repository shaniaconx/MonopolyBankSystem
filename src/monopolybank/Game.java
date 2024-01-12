package monopolybank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    private static int gameId;
    private Map<Integer, MonopolyCode> codes;
    private Map<Integer, Player> players;
    private final Terminal terminal;

    Game (Terminal terminal){
        this.terminal = terminal;
        gameId = GameManager.getActualGameId();
        players = createPlayers();
        this.loadMonopolyCodes();
    }

    private void loadMonopolyCodes (){
        try {
            this.codes = new HashMap<Integer, MonopolyCode>();
            File file = new File(Constants.CONFIG_CODE);
            Scanner myReader = new Scanner(file);

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
        loadMonopolyCodes();

        while (players.size() > 1) {
            terminal.show("card_code");
            int cardCode = terminal.read();

            terminal.show("player_code");
            int playerCode = terminal.read();

            Player actualPlayer = players.get(playerCode);
            MonopolyCode actualCard = codes.get(cardCode);

            boolean result = actualCard.doOperation(actualPlayer);
            if (!result){
                removePlayer(playerCode);
            }
            //save game
            terminal.show("save_game_options");
            int saveOption = terminal.read();
            if(saveOption == 1 || saveOption == 2){
                GameManager.saveGame(this, gameId);
                if(saveOption == 2){
                    break;
                }
            }
        }
        //end game
        if (players.size() == 1){
            Map.Entry<Integer, Player> winnerEntry = players.entrySet().iterator().next();
            Player winner = winnerEntry.getValue();
            String winnerColor = winner.getColor().toString();
            terminal.show("winner", winnerColor);
        }
    }

    private Map<Integer, Player> createPlayers(){
        int numPlayers;
        Map<Integer, Player> map = new HashMap<>();
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
                if (map != null && map.containsKey(playerId)){
                    terminal.show("color_chosen");
                }
            }while (map != null && map.containsKey(playerId));
            map.put(playerId, new Player(playerId, this.terminal));
        }
        return map;
    }

    private void removePlayer(int playerId){
        this.players.remove(playerId);
        Player eliminated = players.get(playerId);
        String colorEliminated = terminal.getTranslatorManager().getTranslator().translate(eliminated.getColor().toString());
        terminal.show("player_elimination", colorEliminated);
    }
}
