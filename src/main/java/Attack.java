public class Attack {
    private String name;
    private String type;
    private double attackSpeed;

    public Attack(String name, String type, double attackSpeed) {
        this.name = name;
        this.type = type;
        this.attackSpeed = attackSpeed;
    }

    public Attack() {} //Needed, otherwise Jackson Databind will cry. Sad!

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    //The higher the speed, the better!
    public double getAttackSpeed() {
        return attackSpeed;
    }

    public String toString() {
        return name;
    }

}
