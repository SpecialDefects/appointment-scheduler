package Model;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Log {

    public static void loginAttempt(String username) throws Exception {
        try {
            File logFile = new File("./login_activity.txt");
            logFile.createNewFile();
        } catch(Exception e) {
            throw new Exception("Cannot create log file");
        }
        try {
            FileWriter writer = new FileWriter("./login_activity.txt");
            LocalDateTime now = LocalDateTime.now();
            writer.write("[" + now + "] Unsuccessful Log in attempt using Username: " + username + "\n");
            writer.close();
            System.out.println("Attempt logged");
        } catch (Exception e) {
            throw new Exception("Unable to write to log file");
        }
    }

    public static void loginSuccess(String username) throws Exception {
        try {
            File logFile = new File("../login_activity.txt");
            logFile.createNewFile();
        } catch(Exception e) {
            throw new Exception("Cannot create log file");
        }
        try {
            FileWriter writer = new FileWriter("../login_activity.txt");
            LocalDateTime now = LocalDateTime.now();
            writer.write("[" + now + "] Successful login of Username: " + username + "\n");
            writer.close();
        } catch (Exception e) {
            throw new Exception("Unable to write to log file");
        }
    }
}
