package Model;

public class Customer {
    private String name;
    private String address;
    private String postalCode;
    private String phone;

    public Customer(String name, String address, String postalCode, String phone) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

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
}
