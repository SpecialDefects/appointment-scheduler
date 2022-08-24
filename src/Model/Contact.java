package Model;

/**
 * represents a Contact
 */
public class Contact {
    /** contact id **/
    int id;
    /** contact name **/
    String name;
    /** contact email **/
    String email;

    /**
     *
     * @param id contact id
     * @param name contact name
     * @param email contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return contact id
     */
    public int getId() { return this.id; }

    /**
     *
     * @return contact name
     */
    public String getName() { return this.name; }

    /**
     *
     * @return contact email
     */
    public String getEmail() { return this.email; }

    /**
     *
     * @return contact name
     */
    public String toString() { return this.name; }
}
