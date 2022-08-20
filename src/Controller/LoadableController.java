package Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;

public interface LoadableController {
    public void load(Appointment appointment);
    void load(Customer customer);
}
