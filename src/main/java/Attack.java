public class Attack {
    private String name;
    private String type;

    public Attack(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Attack() {} //Needed, otherwise Jackson Databind will cry. Sad!

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return name;
    }
}
