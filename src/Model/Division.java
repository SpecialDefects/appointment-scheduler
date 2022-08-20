package Model;

public class Division {
    int id;
    String name;
    int countryId;

    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getCountryId() { return this.countryId; }
    public String toString() { return this.name; }
}
