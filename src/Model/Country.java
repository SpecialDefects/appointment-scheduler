package Model;

public class Country {
    int id;
    String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; };

    public String toString() { return this.name; };
}
