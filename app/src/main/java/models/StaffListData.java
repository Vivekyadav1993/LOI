package models;

import android.support.annotation.NonNull;

/**
 * Created by ROINET on 2/21/2018.
 */

public class StaffListData implements Comparable<StaffListData> {

    private String staff_id, staff_name, hold_id, staff_status, service_name, customer_name, estimate_time, status, appointment_date, token_id, staff_service_started;

    public StaffListData(String _staff_id, String _staff_name, String hold_id, String staff_status, String _service_name, String _customer_name, String _estimate_time, String _status, String _appointment_date, String _token_id, String staff_service_started) {

        this.staff_id = _staff_id;
        this.staff_name = _staff_name;
        this.hold_id = hold_id;
        this.staff_status = staff_status;
        this.service_name = _service_name;
        this.customer_name = _customer_name;
        this.estimate_time = _estimate_time;
        this.status = _status;
        this.appointment_date = _appointment_date;
        this.token_id = _token_id;
        this.staff_service_started = staff_service_started;
    }

    public String getHold_id() {
        return hold_id;
    }

    public void setHold_id(String hold_id) {
        this.hold_id = hold_id;
    }

    public String getStaff_status() {
        return staff_status;
    }

    public void setStaff_status(String staff_status) {
        this.staff_status = staff_status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
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

    public String getStaff_service_started() {
        return staff_service_started;
    }

    public void setStaff_service_started(String staff_service_started) {
        this.staff_service_started = staff_service_started;
    }

    @Override
    public int compareTo(@NonNull StaffListData staffListData) {

        return getCustomer_name().compareTo(staffListData.getCustomer_name());

    }
}
