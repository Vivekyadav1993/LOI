package models;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Comparator;

/**
 * Created by Vivek on 2/26/2018.
 */

public class ConsumerListData implements Comparable<ConsumerListData> {
    private String id, service_name, customer_name, estimate_time, status, appointment_date, token_id, message,book_position,sub_date,service_id,atpremise_all;

    public ConsumerListData(String id, String _service_name, String _customer_name, String _estimate_time, String _status,
                            String _appointment_date, String _token_id, String message,String book_position,String sub_date,String service_id,
                            String atpremise_all) {
        this.id = id;
        service_name = _service_name;
        customer_name = _customer_name;
        estimate_time = _estimate_time;
        status = _status;
        appointment_date = _appointment_date;
        token_id = _token_id;
        this.message = message;
        this.book_position=book_position;
        this.sub_date=sub_date;
        this.service_id=service_id;
        this.atpremise_all=atpremise_all;
    }

    public String getAtpremise_all() {
        return atpremise_all;
    }

    public void setAtpremise_all(String atpremise_all) {
        this.atpremise_all = atpremise_all;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getSub_date() {
        return sub_date;
    }

    public void setSub_date(String sub_date) {
        this.sub_date = sub_date;
    }

    public String getBook_position() {
        return book_position;
    }

    public void setBook_position(String book_position) {
        this.book_position = book_position;
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


            return Integer.valueOf(getBook_position()).compareTo(Integer.valueOf(consumerListData.getBook_position()));

    }

  /*  @Override
    public int compare(ConsumerListData consumerListData, ConsumerListData t1) {

        int flag = Integer.parseInt(consumerListData.getAppointment_date())- Integer.parseInt(t1.getAppointment_date());
        Log.d("CLD","flag_main"+false);
        if(flag==0)
            Log.d("CLD","flag"+false);
        flag = consumerListData.getEstimate_time().compareTo(t1.getEstimate_time());
        return flag;

    }*/
}
