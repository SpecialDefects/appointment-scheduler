package Model;

/** represents a country **/
public class Country {
    /** country id **/
    int id;
    /** country name **/
    String name;

    /**
     * @param id country id
     * @param name country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return country id
     */
    public int getId() { return this.id; }

    /**
     *
     * @return country name
     */
    public String getName() { return this.name; };

    /**
     *
     * @return country name
     */
    public String toString() { return this.name; };
}
