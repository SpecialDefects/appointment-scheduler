package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

/** represents a logger for login actions **/
public class Log {

    /**
     * records a login attempt associated to username
     * @param username username used to attempt login
     * @throws Exception
     */
    public static void loginAttempt(String username) throws Exception {
        /** create file **/
        try {
            File logFile = new File("./login_activity.txt");
            logFile.createNewFile();
        } catch(Exception e) {
            throw new Exception("Cannot create log file");
        }
        /** write login attempt to file **/
        try {
            FileWriter writer = new FileWriter("./login_activity.txt", true);
            BufferedWriter buffwriter = new BufferedWriter(writer);
            LocalDateTime now = LocalDateTime.now();
            buffwriter.write("[" + now + "] Unsuccessful Log in attempt using Username: " + username + "\n");
            buffwriter.close();
        } catch (Exception e) {
            throw new Exception("Unable to write to log file");
        }
    }

    /**
     * records a successful login associated to username
     * @param username username used to login
     * @throws Exception
     */
    public static void loginSuccess(String username) throws Exception {
        /** create file **/
        try {
            File logFile = new File("./login_activity.txt");
            logFile.createNewFile();
        } catch(Exception e) {
            throw new Exception("Cannot create log file");
        }
        /** write successful user login to file **/
        try {
            FileWriter writer = new FileWriter("./login_activity.txt", true);
            BufferedWriter buffwriter = new BufferedWriter(writer);
            LocalDateTime now = LocalDateTime.now();
            buffwriter.write("[" + now + "] Successful login of Username: " + username + "\n");
            System.out.println("Successful Login recorded");
            buffwriter.close();
        } catch (Exception e) {
            throw new Exception("Unable to write to log file");
        }
    }
}
