package models;

/**
 * Created by lenovo on 5/21/2018.
 */

public class CancelCustomerSelection {

    private String booking_id ,status;


    public CancelCustomerSelection() {

    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
