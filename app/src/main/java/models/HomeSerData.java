package models;

/**
 * Created by teknik on 11/24/2017.
 */

public class HomeSerData {

    private String id ,  location , no_of_radius , travel_time , start_time , end_time
            , select_staff , lat , longi ;

    public HomeSerData(String _id ,String _location , String _no_of_radius ,String _travel_time , String _start_time , String _end_time ,
                       String _select_staff , String _lat , String _longi){

        id = _id;

        location = _location;

        no_of_radius = _no_of_radius;

        travel_time = _travel_time;

        start_time = _start_time;

        end_time = _end_time;

        select_staff = _select_staff;

        lat = _lat;

        longi = _longi;

    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNo_of_radius() {
        return no_of_radius;
    }

    public void setNo_of_radius(String no_of_radius) {
        this.no_of_radius = no_of_radius;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSelect_staff() {
        return select_staff;
    }

    public void setSelect_staff(String select_staff) {
        this.select_staff = select_staff;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
