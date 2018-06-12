package models;

import android.support.annotation.NonNull;

/**
 * Created by Vivek on 2/26/2018.
 */

public class ConsumerListData implements Comparable<ConsumerListData> {
    private String id, service_name, customer_name, estimate_time, status, appointment_date, token_id, message;

    public ConsumerListData(String id, String _service_name, String _customer_name, String _estimate_time, String _status,
                            String _appointment_date, String _token_id, String message) {
        this.id = id;
        service_name = _service_name;
        customer_name = _customer_name;
        estimate_time = _estimate_time;
        status = _status;
        appointment_date = _appointment_date;
        token_id = _token_id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(String estimate_time) {
        this.estimate_time = estimate_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    @Override
    public int compareTo(@NonNull ConsumerListData consumerListData) {
        return getEstimate_time().compareTo(consumerListData.getEstimate_time());
    }
}
