package models;

/**
 * Created by lenovo on 4/20/2018.
 */

public class ServiceList {

    public String service_name;
    public String service_id;
    public String address_idd;
    public String address_name;

    public String getAddress_id() {
        return address_idd;
    }

    public void setAddress_id(String address_id) {
        this.address_idd = address_id;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
}
