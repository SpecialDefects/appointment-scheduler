package Controller;

import Model.Appointment;
import Model.User;

public interface LoadableController {
    public void load(User user);
    public void load(Appointment appointment);
}
