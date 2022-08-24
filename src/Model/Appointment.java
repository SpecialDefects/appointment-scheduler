package Model;

import java.time.LocalDateTime;

/** representation of an appointment **/
public class Appointment {
    /** appointment id **/
    int id;
    /** appointment title **/
    String title;
    /** appointment description **/
    String description;
    /** appointment location **/
    String location;
    /** appointment type **/
    String type;
    /** appointment start time **/
    LocalDateTime start;
    /** appointment end time **/
    LocalDateTime end;
    /** customer id of custom involved with appointment **/
    int customerId;
    /** user id of user involved with appointment **/
    int userId;
    /** contact id of contact involved with appointment **/
    int contactId;

    /**
     *
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param customerId appointment customer id
     * @param userId appointment user id
     * @param contactId appointment contact id
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     *
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param customerId appointment customer id
     * @param userId appointment user id
     * @param contactId appointment contact id
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     *
     * @return appointment id
     */
    public int getId() { return this.id; }

    /**
     *
     * @return appointment title
     */
    public String getTitle() { return this.title; }

    /**
     *
     * @return appointment description
     */
    public String getDescription() { return this.description; }

    /**
     *
     * @return appointment location
     */
    public String getLocation() { return this.location; }

    /**
     *
     * @return appointment type
     */
    public String getType() { return this.type; }

    /**
     *
     * @return appointment start time
     */
    public String getStart() { return this.start.toString(); }

    /**
     *
     * @return appointment end time
     */
    public String getEnd() { return this.end.toString(); }

    /**
     *
     * @return appointment customerId
     */
    public int getCustomerId() { return this.customerId; }

    /**
     *
     * @return appointment userId
     */
    public int getUserId() { return this.userId; }

    /**
     *
     * @return appointment contactId
     */
    public int getContactId() { return this.contactId; }
}
