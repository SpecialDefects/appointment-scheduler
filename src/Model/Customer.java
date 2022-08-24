package Model;

/** represents a Customer **/
public class Customer {

    /** customer id **/
    private int id;
    /** customer name */
    private String name;
    /** customer address **/
    private String address;
    /** customer postal code **/
    private String postalCode;
    /** customer phone **/
    private String phone;
    /** customer division id **/
    private int divisionId;

    /**
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param divisionId customer division id
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * @return customer id
     */
    public int getId() { return this.id; }

    /**
     * @return customer name
     */
    public String getName() { return this.name; }

    /**
     * @return customer address
     */
    public String getAddress() { return this.address; }

    /**
     * @return customer postal code
     */
    public String getPostalCode() { return this.postalCode; }

    /**
     * @return customer phone number
     */
    public String getPhone() { return this.phone; }

    /**
     * @return customer division id
     */
    public int getDivisionId() { return this.divisionId; }
}
