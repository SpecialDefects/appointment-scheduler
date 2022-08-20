package Model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    public int getId() { return this.id; }
    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getDivisionId() { return this.divisionId; }
}
