package models;

/**
 * Created by teknik on 11/7/2017.
 */

public class BusinessHourData {

    private String full_address , address_id , date_start , date_end , mon_start_time , mon_end_time
            , tue_start_time , tue_end_time , wed_start_time , wed_end_time , thr_start_time , thr_end_time
            ,fri_start_time , fri_end_time , sat_start_time , sat_end_time , sun_start_time , sun_end_time ;

    public  BusinessHourData(){

    }
      public BusinessHourData(String _full_address , String _address_id , String _date_start , String _date_end , String _mon_start_time , String _mon_end_time
              , String _tue_start_time , String _tue_end_time , String _wed_start_time , String _wed_end_time , String _thr_start_time , String _thr_end_time
              ,String _fri_start_time , String _fri_end_time , String _sat_start_time , String _sat_end_time , String _sun_start_time , String _sun_end_time) {


          this.full_address  = _full_address;

          this.address_id = _address_id;

          this.date_start = _date_start;
          this.date_end = _date_end;
          this.mon_start_time  = _mon_start_time;
          this.mon_end_time = _mon_end_time;
          this.tue_start_time = _tue_start_time;
          this.tue_end_time = _tue_end_time;
          this.wed_start_time  = _wed_start_time;
          this.wed_end_time  = _wed_end_time;
          this.thr_start_time = _thr_start_time;
          this.thr_end_time = _thr_end_time;
          this.fri_start_time = _fri_start_time;
          this.fri_end_time  = _fri_end_time;
          this.sat_start_time = _sat_start_time;
          this.sat_end_time = _sat_end_time ;
          this.sun_start_time = _sun_start_time ;
          this.sun_end_time   = _sun_end_time;


      }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getMon_start_time() {
        return mon_start_time;
    }

    public void setMon_start_time(String mon_start_time) {
        this.mon_start_time = mon_start_time;
    }

    public String getMon_end_time() {
        return mon_end_time;
    }

    public void setMon_end_time(String mon_end_time) {
        this.mon_end_time = mon_end_time;
    }

    public String getTue_start_time() {
        return tue_start_time;
    }

    public void setTue_start_time(String tue_start_time) {
        this.tue_start_time = tue_start_time;
    }

    public String getTue_end_time() {
        return tue_end_time;
    }

    public void setTue_end_time(String tue_end_time) {
        this.tue_end_time = tue_end_time;
    }

    public String getWed_start_time() {
        return wed_start_time;
    }

    public void setWed_start_time(String wed_start_time) {
        this.wed_start_time = wed_start_time;
    }

    public String getWed_end_time() {
        return wed_end_time;
    }

    public void setWed_end_time(String wed_end_time) {
        this.wed_end_time = wed_end_time;
    }

    public String getThr_start_time() {
        return thr_start_time;
    }

    public void setThr_start_time(String thr_start_time) {
        this.thr_start_time = thr_start_time;
    }

    public String getThr_end_time() {
        return thr_end_time;
    }

    public void setThr_end_time(String thr_end_time) {
        this.thr_end_time = thr_end_time;
    }

    public String getFri_start_time() {
        return fri_start_time;
    }

    public void setFri_start_time(String fri_start_time) {
        this.fri_start_time = fri_start_time;
    }

    public String getFri_end_time() {
        return fri_end_time;
    }

    public void setFri_end_time(String fri_end_time) {
        this.fri_end_time = fri_end_time;
    }

    public String getSat_start_time() {
        return sat_start_time;
    }

    public void setSat_start_time(String sat_start_time) {
        this.sat_start_time = sat_start_time;
    }

    public String getSat_end_time() {
        return sat_end_time;
    }

    public void setSat_end_time(String sat_end_time) {
        this.sat_end_time = sat_end_time;
    }

    public String getSun_start_time() {
        return sun_start_time;
    }

    public void setSun_start_time(String sun_start_time) {
        this.sun_start_time = sun_start_time;
    }

    public String getSun_end_time() {
        return sun_end_time;
    }

    public void setSun_end_time(String sun_end_time) {
        this.sun_end_time = sun_end_time;
    }
}
