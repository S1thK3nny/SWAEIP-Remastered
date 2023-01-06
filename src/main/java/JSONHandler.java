import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.google.common.io.Files.getFileExtension;

public class JSONHandler {
    ObjectMapper mapper = new ObjectMapper(); // create once, reuse

    public ArrayList readAttacks(ArrayList<Attack> attacks) throws IOException {
        File folder = new File("src/main/java/Attacks/");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null) return null;
        for (File file : listOfFiles) {
            if (file.isFile() && getFileExtension(file.getName()).compareTo("json")==0) { //if file extension is json
                attacks.add(mapper.readValue(new File(file.toString()), Attack.class)); //Add attack to attacks ArrayList
            }
        }
        return attacks;
    }



    public ArrayList readCharacters(ArrayList<Character> characters, ArrayList<Attack> attacks) throws IOException {
        File folder = new File("src/main/java/Characters/");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null || listOfFiles.length<=0) {
            return defaultCharacter();
        }
        int i = 0; //i is representing the current character
        for (File file : listOfFiles) {
            if (file.isFile() && getFileExtension(file.getName()).compareTo("json")==0) { //if file extension is json
                characters.add(mapper.readValue(new File(file.toString()), Character.class)); //Add character to characters ArrayList
                if(attacks == null) {
                    characters.get(i).setAttacks(defaultAttack());
                }
                else {
                    for(int j=0; j<characters.get(i).getAttackList().size(); j++) { //j is representing the attacks that the character is gonna have
                        for(int x=0; x<attacks.size(); x++) { //x is representing the known, public attacks from the Attacks folder

                            if(characters.get(i).getAttackList().get(j).compareTo(attacks.get(x).getName())==0) {
                                characters.get(i).addAttack(attacks.get(x));
                            }

                        }
                    }

                    if(characters.get(i).getAttacks().size()<=0) { //If the character has no attacks
                        characters.get(i).setAttacks(defaultAttack());
                    }

                }
            }
            characters.get(i).generateStats(); //Must be here, as this does NOT work in the no arg constructor
            i++;
        }
        return characters;
    }

    public ArrayList defaultCharacter() { //When there are no files in Characters
        ArrayList<Attack> attListDefault = defaultAttack();
        Character charDefault = new Character("Default", 1, attListDefault);
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(charDefault);
        return characters;
    }

    public ArrayList defaultAttack() { //When there are no files in Attacks
        Attack attDefault = new Attack("Default", "Damage");
        ArrayList<Attack> attListDefault = new ArrayList<>();
        attListDefault.add(attDefault);
        return attListDefault;
    }
}