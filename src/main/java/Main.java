import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Attack> attacks = new ArrayList<>();
        ArrayList<Character> characters = new ArrayList<>();
        JSONHandler json = new JSONHandler();
        attacks = json.readAttacks(attacks);
        characters = json.readCharacters(characters, attacks);


//        for(int i=0; i<characters.size(); i++) {
//            System.out.println(characters.get(i).getName());
//            for(int j=0; j<characters.get(i).getAttacks().size(); j++) {
//                System.out.println(characters.get(i).getAttacks().get(j).getName() + " " + characters.get(i).getAttacks().get(j).getType());
//            }
//            System.out.println(" ");
//        }

        Character player1 = new Character();
        Character player2 = new Character();
        SelectWindow sWP1 = new SelectWindow(characters, player1, player2, "player 1", true);
    }
}