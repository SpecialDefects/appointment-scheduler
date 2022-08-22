package Model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Appointment {
    int id;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime start;
    LocalDateTime end;
    int customerId;
    int userId;
    int contactId;

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
    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getLocation() { return this.location; }
    public String getType() { return this.type; }
    public String getStart() { return this.start.toString(); }
    public String getEnd() { return this.end.toString(); }
    public int getCustomerId() { return this.customerId; }
    public int getUserId() { return this.userId; }
    public int getContactId() { return this.contactId; }
}
