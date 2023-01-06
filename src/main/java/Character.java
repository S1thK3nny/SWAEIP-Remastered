import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Character implements Cloneable {
    private String name;
    private int tier;
    private int health = 1000;
    private ArrayList<String> attackList;
    private ArrayList<Attack> attacks = new ArrayList<>();
    public PropertyChangeSupport healthChange;

    //@ConstructorProperties({"name", "tier", "attackList"})
    public Character(String name, int tier, ArrayList<Attack> attacks) {
        this.name = name;
        this.tier = tier;
        this.attacks = attacks;
        healthChange = new PropertyChangeSupport(this);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public Character() {
        healthChange = new PropertyChangeSupport(this);
    } //Needed, otherwise Jackson Databind will cry. Sad!

    public void generateStats() {
        int percentage = (int)(Math.random() * (10-5+1) ) + 5; //Generates a number between (and including) 5 and 10
        percentage *= tier;
        health += health/100*percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        int oldValue = this.health;
        this.health = health;
        healthChange.firePropertyChange("health", oldValue, this.health);
    }

    public ArrayList<String> getAttackList() {
        return attackList;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", tier=" + tier +
                ", health=" + health +
                ", attacks=" + attacks +
                '}';
    }
}