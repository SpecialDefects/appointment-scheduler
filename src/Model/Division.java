package Model;

/**
 * represents a Division
 */
public class Division {
    /** division id **/
    int id;
    /** division name **/
    String name;
    /** division's country id **/
    int countryId;

    /**
     * @param id division id
     * @param name division name
     * @param countryId division country id
     */
    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * @return division id
     */
    public int getId() { return this.id; }

    /**
     * @return division name
     */
    public String getName() { return this.name; }

    /**
     * @return division country id
     */
    public int getCountryId() { return this.countryId; }

    /**
     * @return division name
     */
    public String toString() { return this.name; }
}
