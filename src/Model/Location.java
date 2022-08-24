package Model;
/** represents a location with a name and number of appointments **/
public class Location {
    String name;
    int numberOfAppointments;

    /**
     * @param name location name
     * @param numberOfAppointments number of appointments scheduled at location
     */
    public Location(String name, int numberOfAppointments) {
        this.name = name;
        this.numberOfAppointments = numberOfAppointments;
    }

    /**
     * @return location name
     */
    public String getName() { return this.name; }

    /**
     * @return number of appointments at location
     */
    public int getNumberOfAppointments() { return this.numberOfAppointments; }

}
