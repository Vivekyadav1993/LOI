package models;

import java.util.ArrayList;

/**
 * Created by teknik on 11/24/2017.
 */

public class ServiceData {

    private String add_id , ser_id , name , duration , buffer_time , cost ,
            service_type ,max_people_in_group , home_service ;


    private ArrayList<HomeSerData> data = new ArrayList<>();


    public ServiceData(String _add_id ,String _ser_id , String _name , String _duration , String _buffer_time
            , String _cost , String _service_type , String _max_people_in_group ,
                       String _home_service , ArrayList<HomeSerData> _data){

        add_id = _add_id;

        ser_id = _ser_id;


        name = _name;

        duration = _duration;

        buffer_time = _buffer_time;

        cost = _cost ;

        service_type = _service_type;

        max_people_in_group = _max_people_in_group;

        home_service = _home_service;

        data = _data;



    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getAdd_id() {

        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBuffer_time() {
        return buffer_time;
    }

    public void setBuffer_time(String buffer_time) {
        this.buffer_time = buffer_time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getMax_people_in_group() {
        return max_people_in_group;
    }

    public void setMax_people_in_group(String max_people_in_group) {
        this.max_people_in_group = max_people_in_group;
    }

    public String getHome_service() {
        return home_service;
    }

    public void setHome_service(String home_service) {
        this.home_service = home_service;
    }

    public ArrayList<HomeSerData> getData() {
        return data;
    }

    public void setData(ArrayList<HomeSerData> data) {
        this.data = data;
    }
}
