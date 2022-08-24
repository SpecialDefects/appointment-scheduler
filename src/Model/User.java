package Model;

import java.sql.Timestamp;
import java.sql.Date;

/** Represents a user from database **/
public class User {
    private Integer Id;
    private String userName;
    private Date created;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    /**
     * @param userID users id
     * @param userName users username
     * @param created date user was created
     * @param createdBy name of user who created user
     * @param lastUpdated date when user was last updated
     * @param lastUpdatedBy name of user who performed update
     */
    public User(Integer userID, String userName, Date created, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.Id = userID;
        this.userName = userName;
        this.created = created;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return user id
     */
    public Integer getId() {
        return this.Id;
    }

    /**
     * @return username
     */
    public String getUserName() { return this.userName; }

    /**
     * @return date user was created
     */
    public Date getCreated() { return this.created; }

    /**
     * @return id of user that created this user
     */
    public String getCreatedBy() { return this.createdBy; }

    /**
     * @return timestamp of last update
     */
    public Timestamp getLastUpdated() { return this.lastUpdated; }

    /**
     * @return id of last user that updated this user
     */
    public String getLastUpdatedBy() { return this.lastUpdatedBy; }
}
