package Model;

public class Contact {
    int id;
    String name;
    String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String toString() { return this.name; }
}
