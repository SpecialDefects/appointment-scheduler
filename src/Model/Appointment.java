package Model;

import java.sql.Date;

public class Appointment {
    int id;
    String title;
    String description;
    String location;
    String type;
    Date start;
    Date end;
    int customerId;
    int userId;
    int contactId;

    public Appointment(int id, String title, String description, String location, String type, Date start, Date end, int customerId, int userId, int contactId) {
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

    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getLocation() { return this.location; }
    public String getType() { return this.type; }
    public Date getStart() { return this.start; }
    public Date getEnd() { return this.end; }
    public int getCustomerId() { return this.customerId; }
    public int getUserId() { return this.userId; }
    public int getContactId() { return this.contactId; }
}
