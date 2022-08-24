package Controller;

import Model.Appointment;
import Model.Customer;

/**
 * represents a controller than can load external data
 */
public interface LoadableController {
    public void load(Appointment appointment);
    void load(Customer customer);
}
