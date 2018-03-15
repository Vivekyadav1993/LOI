package models;

/**
 * Created by teknik on 11/9/2017.
 */

public class StaffData {

    private String admin_flag , only_me_flag ,address_id, staff_id ,  first_name, last_name, email, phone, address, start_date, end_date,

   all_break1_from, all_break1_to,all_break2_from, all_break2_to, all_break3_from, all_break3_to,
            all_break4_from, all_break4_to, all_break5_from, all_break5_to ,

    mon_from_time
            , mon_to_time, mon_break1_from, mon_break1_to,mon_break2_from, mon_break2_to, mon_break3_from, mon_break3_to,
            mon_break4_from, mon_break4_to, mon_break5_from, mon_break5_to, tue_from_time, tue_to_time, tue_break1_from,
            tue_break1_to, tue_break2_from, tue_break2_to, tue_break3_from, tue_break3_to, tue_break4_from, tue_break4_to,
            tue_break5_from, tue_break5_to, wed_from_time, wed_to_time, wed_break1_from, wed_break1_to, wed_break2_from,
            wed_break2_to, wed_break3_from, wed_break3_to, wed_break4_from, wed_break4_to, wed_break5_from, wed_break5_to,
            thru_from_time, thru_to_time, thru_break1_from, thru_break1_to, thru_break2_from, thru_break2_to, thru_break3_from
            ,thru_break3_to, thru_break4_from, thru_break4_to, thru_break5_from, thru_break5_to, fri_from_time, fri_to_time,
            fri_break1_from, fri_break1_to, fri_break2_from, fri_break2_to, fri_break3_from,fri_break3_to, fri_break4_from,
            fri_break4_to, fri_break5_from, fri_break5_to, sat_from_time, sat_to_time, sat_break1_from, sat_break1_to,
            sat_break2_from, sat_break2_to, sat_break3_from, sat_break3_to, sat_break4_from, sat_break4_to, sat_break5_from,
            sat_break5_to, sun_from_time, sun_to_time, sun_break1_from, sun_break1_to, sun_break2_from, sun_break2_to,
            sun_break3_from, sun_break3_to, sun_break4_from, sun_break4_to, sun_break5_from, sun_break5_to ;


    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public StaffData (String _admin_flag , String _only_me_flag ,
                      String _address_id, String _staff_id,
                      String _first_name, String _last_name,
                      String _email, String _phone, String _address,
                      String _start_date, String _end_date

                       , String _all_break1_from, String _all_break1_to, String _all_break2_from, String _all_break2_to,
                      String _all_break3_from, String _all_break3_to,
                      String _all_break4_from, String _all_break4_to, String _all_break5_from, String _all_break5_to ,


                      String _mon_from_time, String _mon_to_time, String _mon_break1_from,
                      String _mon_break1_to, String _mon_break2_from, String _mon_break2_to, String _mon_break3_from, String _mon_break3_to,
                      String _mon_break4_from, String _mon_break4_to, String _mon_break5_from, String _mon_break5_to,
                      String _tue_from_time,
                      String _tue_to_time, String _tue_break1_from, String _tue_break1_to, String _tue_break2_from,
                      String _tue_break2_to, String _tue_break3_from, String _tue_break3_to, String _tue_break4_from,
                      String _tue_break4_to, String _tue_break5_from, String _tue_break5_to, String _wed_from_time, String _wed_to_time,
                      String _wed_break1_from, String _wed_break1_to, String _wed_break2_from, String _wed_break2_to,
                      String _wed_break3_from, String _wed_break3_to, String _wed_break4_from, String _wed_break4_to,
                      String _wed_break5_from, String _wed_break5_to, String _thru_from_time, String _thru_to_time,
                      String _thru_break1_from, String _thru_break1_to, String _thru_break2_from, String _thru_break2_to,
                      String _thru_break3_from, String _thru_break3_to, String _thru_break4_from, String _thru_break4_to,
                      String _thru_break5_from, String _thru_break5_to, String _fri_from_time, String _fri_to_time,
                      String _fri_break1_from, String _fri_break1_to, String _fri_break2_from, String _fri_break2_to,
                      String _fri_break3_from, String _fri_break3_to, String _fri_break4_from, String _fri_break4_to,
                      String _fri_break5_from, String _fri_break5_to, String _sat_from_time, String _sat_to_time,
                      String _sat_break1_from, String _sat_break1_to, String _sat_break2_from, String _sat_break2_to,
                      String _sat_break3_from, String _sat_break3_to, String _sat_break4_from, String _sat_break4_to,
                      String _sat_break5_from, String _sat_break5_to, String _sun_from_time, String _sun_to_time,
                      String _sun_break1_from, String _sun_break1_to, String _sun_break2_from, String _sun_break2_to,
                      String _sun_break3_from, String _sun_break3_to, String _sun_break4_from, String _sun_break4_to,
                      String _sun_break5_from, String _sun_break5_to){

        admin_flag = _admin_flag;  only_me_flag = _only_me_flag;

        address_id = _address_id;

        staff_id  = _staff_id;


        first_name = _first_name; last_name = _last_name; email = _email; phone = _phone; address = _address;

        start_date = _start_date; end_date =_end_date;

        all_break1_from = _all_break1_from;
        all_break1_to = _all_break1_to;
        all_break2_from = _all_break2_from;
        all_break2_to = _all_break2_to;
        all_break3_from = _all_break3_from;
        all_break3_to = _all_break3_to;
        all_break4_from = _all_break4_from;
        all_break4_to = _all_break4_to;
        all_break5_from = _all_break5_from;
        all_break5_to = _all_break5_to;


        mon_from_time = _mon_from_time;mon_to_time = _mon_to_time;mon_break1_from = _mon_break1_from;mon_break1_to = _mon_break1_to;
        mon_break2_from = _mon_break2_from;mon_break2_to = _mon_break2_to;mon_break3_from  = _mon_break3_from;mon_break3_to = _mon_break3_to;
        mon_break4_from = _mon_break4_from;mon_break4_to = _mon_break4_to;mon_break5_from = _mon_break5_from;mon_break5_to  =  _mon_break5_to;

        tue_from_time = _tue_from_time;tue_to_time  = _tue_to_time;tue_break1_from  = _tue_break1_from;tue_break1_to = _tue_break1_to;
        tue_break2_from = _tue_break2_from;tue_break2_to = _tue_break2_to;tue_break3_from = _tue_break3_from;tue_break3_to = _tue_break3_to;
        tue_break4_from = _tue_break4_from;tue_break4_to = _tue_break4_to;tue_break5_from = _tue_break5_from;tue_break5_to = _tue_break5_to;

        wed_from_time = _wed_from_time;wed_to_time = _wed_to_time;wed_break1_from = _wed_break1_from;wed_break1_to = _wed_break1_to;wed_break2_from = _wed_break2_from;
        wed_break2_to = _wed_break2_to;wed_break3_from = _wed_break3_from;wed_break3_to = _wed_break3_to;wed_break4_from = _wed_break4_from;
        wed_break4_to = _wed_break4_to;wed_break5_from = _wed_break5_from;wed_break5_to = _wed_break5_to;

        thru_from_time = _thru_from_time;thru_to_time =_thru_to_time;thru_break1_from = _thru_break1_from;thru_break1_to  = _thru_break1_to;
        thru_break2_from = _thru_break2_from;thru_break2_to = _thru_break2_to;thru_break3_from = _thru_break3_from;thru_break3_to = _thru_break3_to;
        thru_break4_from = _thru_break4_from;thru_break4_to = _thru_break4_to;thru_break5_from = _thru_break5_from;thru_break5_to = _thru_break5_to;

        fri_from_time = _fri_from_time;fri_to_time = _fri_to_time;fri_break1_from = _fri_break1_from;fri_break1_to = _fri_break1_to;
        fri_break2_from = _fri_break2_from;
        fri_break2_to = _fri_break2_to;fri_break3_from = _fri_break3_from;fri_break3_to = _fri_break3_to;
        fri_break4_from = _fri_break4_from;fri_break4_to = _fri_break4_to;
        fri_break5_from = _fri_break5_from;fri_break5_to = _fri_break5_to;

        sat_from_time = _sat_from_time;sat_to_time = _sat_to_time;sat_break1_from = _sat_break1_from;sat_break1_to = _sat_break1_to;sat_break2_from = _sat_break2_from;
        sat_break2_to = _sat_break2_to;sat_break3_from = _sat_break3_from;sat_break3_to = _sat_break3_to;sat_break4_from = _sat_break4_from;sat_break4_to = _sat_break4_to;
        sat_break5_from = _sat_break5_from;sat_break5_to = _sat_break5_to;

        sun_from_time = _sun_from_time;sun_to_time =_sun_to_time;sun_break1_from = _sun_break1_from;sun_break1_to = _sun_break1_to;
        sun_break2_from = _sun_break2_from;sun_break2_to = _sun_break2_to;sun_break3_from = _sun_break3_from;sun_break3_to = _sun_break3_to;
        sun_break4_from = _sun_break4_from;sun_break4_to = _sun_break4_to;sun_break5_from = _sun_break5_from;sun_break5_to = _sun_break5_to;



    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMon_from_time() {
        return mon_from_time;
    }

    public void setMon_from_time(String mon_from_time) {
        this.mon_from_time = mon_from_time;
    }

    public String getMon_to_time() {
        return mon_to_time;
    }

    public void setMon_to_time(String mon_to_time) {
        this.mon_to_time = mon_to_time;
    }

    public String getMon_break1_from() {
        return mon_break1_from;
    }

    public void setMon_break1_from(String mon_break1_from) {
        this.mon_break1_from = mon_break1_from;
    }

    public String getMon_break1_to() {
        return mon_break1_to;
    }

    public void setMon_break1_to(String mon_break1_to) {
        this.mon_break1_to = mon_break1_to;
    }

    public String getMon_break2_from() {
        return mon_break2_from;
    }

    public void setMon_break2_from(String mon_break2_from) {
        this.mon_break2_from = mon_break2_from;
    }

    public String getMon_break2_to() {
        return mon_break2_to;
    }

    public void setMon_break2_to(String mon_break2_to) {
        this.mon_break2_to = mon_break2_to;
    }

    public String getMon_break3_from() {
        return mon_break3_from;
    }

    public void setMon_break3_from(String mon_break3_from) {
        this.mon_break3_from = mon_break3_from;
    }

    public String getMon_break3_to() {
        return mon_break3_to;
    }

    public void setMon_break3_to(String mon_break3_to) {
        this.mon_break3_to = mon_break3_to;
    }

    public String getMon_break4_from() {
        return mon_break4_from;
    }

    public void setMon_break4_from(String mon_break4_from) {
        this.mon_break4_from = mon_break4_from;
    }

    public String getMon_break4_to() {
        return mon_break4_to;
    }

    public void setMon_break4_to(String mon_break4_to) {
        this.mon_break4_to = mon_break4_to;
    }

    public String getMon_break5_from() {
        return mon_break5_from;
    }

    public void setMon_break5_from(String mon_break5_from) {
        this.mon_break5_from = mon_break5_from;
    }

    public String getMon_break5_to() {
        return mon_break5_to;
    }

    public void setMon_break5_to(String mon_break5_to) {
        this.mon_break5_to = mon_break5_to;
    }

    public String getTue_from_time() {
        return tue_from_time;
    }

    public void setTue_from_time(String tue_from_time) {
        this.tue_from_time = tue_from_time;
    }

    public String getTue_to_time() {
        return tue_to_time;
    }

    public void setTue_to_time(String tue_to_time) {
        this.tue_to_time = tue_to_time;
    }

    public String getTue_break1_from() {
        return tue_break1_from;
    }

    public void setTue_break1_from(String tue_break1_from) {
        this.tue_break1_from = tue_break1_from;
    }

    public String getTue_break1_to() {
        return tue_break1_to;
    }

    public void setTue_break1_to(String tue_break1_to) {
        this.tue_break1_to = tue_break1_to;
    }

    public String getTue_break2_from() {
        return tue_break2_from;
    }

    public void setTue_break2_from(String tue_break2_from) {
        this.tue_break2_from = tue_break2_from;
    }

    public String getTue_break2_to() {
        return tue_break2_to;
    }

    public void setTue_break2_to(String tue_break2_to) {
        this.tue_break2_to = tue_break2_to;
    }

    public String getTue_break3_from() {
        return tue_break3_from;
    }

    public void setTue_break3_from(String tue_break3_from) {
        this.tue_break3_from = tue_break3_from;
    }

    public String getTue_break3_to() {
        return tue_break3_to;
    }

    public void setTue_break3_to(String tue_break3_to) {
        this.tue_break3_to = tue_break3_to;
    }

    public String getTue_break4_from() {
        return tue_break4_from;
    }

    public void setTue_break4_from(String tue_break4_from) {
        this.tue_break4_from = tue_break4_from;
    }

    public String getTue_break4_to() {
        return tue_break4_to;
    }

    public void setTue_break4_to(String tue_break4_to) {
        this.tue_break4_to = tue_break4_to;
    }

    public String getTue_break5_from() {
        return tue_break5_from;
    }

    public void setTue_break5_from(String tue_break5_from) {
        this.tue_break5_from = tue_break5_from;
    }

    public String getTue_break5_to() {
        return tue_break5_to;
    }

    public void setTue_break5_to(String tue_break5_to) {
        this.tue_break5_to = tue_break5_to;
    }

    public String getWed_from_time() {
        return wed_from_time;
    }

    public void setWed_from_time(String wed_from_time) {
        this.wed_from_time = wed_from_time;
    }

    public String getWed_to_time() {
        return wed_to_time;
    }

    public void setWed_to_time(String wed_to_time) {
        this.wed_to_time = wed_to_time;
    }

    public String getWed_break1_from() {
        return wed_break1_from;
    }

    public void setWed_break1_from(String wed_break1_from) {
        this.wed_break1_from = wed_break1_from;
    }

    public String getWed_break1_to() {
        return wed_break1_to;
    }

    public void setWed_break1_to(String wed_break1_to) {
        this.wed_break1_to = wed_break1_to;
    }

    public String getWed_break2_from() {
        return wed_break2_from;
    }

    public void setWed_break2_from(String wed_break2_from) {
        this.wed_break2_from = wed_break2_from;
    }

    public String getWed_break2_to() {
        return wed_break2_to;
    }

    public void setWed_break2_to(String wed_break2_to) {
        this.wed_break2_to = wed_break2_to;
    }

    public String getWed_break3_from() {
        return wed_break3_from;
    }

    public void setWed_break3_from(String wed_break3_from) {
        this.wed_break3_from = wed_break3_from;
    }

    public String getWed_break3_to() {
        return wed_break3_to;
    }

    public void setWed_break3_to(String wed_break3_to) {
        this.wed_break3_to = wed_break3_to;
    }

    public String getWed_break4_from() {
        return wed_break4_from;
    }

    public void setWed_break4_from(String wed_break4_from) {
        this.wed_break4_from = wed_break4_from;
    }

    public String getWed_break4_to() {
        return wed_break4_to;
    }

    public void setWed_break4_to(String wed_break4_to) {
        this.wed_break4_to = wed_break4_to;
    }

    public String getWed_break5_from() {
        return wed_break5_from;
    }

    public void setWed_break5_from(String wed_break5_from) {
        this.wed_break5_from = wed_break5_from;
    }

    public String getWed_break5_to() {
        return wed_break5_to;
    }

    public void setWed_break5_to(String wed_break5_to) {
        this.wed_break5_to = wed_break5_to;
    }

    public String getThru_from_time() {
        return thru_from_time;
    }

    public void setThru_from_time(String thru_from_time) {
        this.thru_from_time = thru_from_time;
    }

    public String getThru_to_time() {
        return thru_to_time;
    }

    public void setThru_to_time(String thru_to_time) {
        this.thru_to_time = thru_to_time;
    }

    public String getThru_break1_from() {
        return thru_break1_from;
    }

    public void setThru_break1_from(String thru_break1_from) {
        this.thru_break1_from = thru_break1_from;
    }

    public String getThru_break1_to() {
        return thru_break1_to;
    }

    public void setThru_break1_to(String thru_break1_to) {
        this.thru_break1_to = thru_break1_to;
    }

    public String getThru_break2_from() {
        return thru_break2_from;
    }

    public void setThru_break2_from(String thru_break2_from) {
        this.thru_break2_from = thru_break2_from;
    }

    public String getThru_break2_to() {
        return thru_break2_to;
    }

    public void setThru_break2_to(String thru_break2_to) {
        this.thru_break2_to = thru_break2_to;
    }

    public String getThru_break3_from() {
        return thru_break3_from;
    }

    public void setThru_break3_from(String thru_break3_from) {
        this.thru_break3_from = thru_break3_from;
    }

    public String getThru_break3_to() {
        return thru_break3_to;
    }

    public void setThru_break3_to(String thru_break3_to) {
        this.thru_break3_to = thru_break3_to;
    }

    public String getThru_break4_from() {
        return thru_break4_from;
    }

    public void setThru_break4_from(String thru_break4_from) {
        this.thru_break4_from = thru_break4_from;
    }

    public String getThru_break4_to() {
        return thru_break4_to;
    }

    public void setThru_break4_to(String thru_break4_to) {
        this.thru_break4_to = thru_break4_to;
    }

    public String getThru_break5_from() {
        return thru_break5_from;
    }

    public void setThru_break5_from(String thru_break5_from) {
        this.thru_break5_from = thru_break5_from;
    }

    public String getThru_break5_to() {
        return thru_break5_to;
    }

    public void setThru_break5_to(String thru_break5_to) {
        this.thru_break5_to = thru_break5_to;
    }

    public String getFri_from_time() {
        return fri_from_time;
    }

    public void setFri_from_time(String fri_from_time) {
        this.fri_from_time = fri_from_time;
    }

    public String getFri_to_time() {
        return fri_to_time;
    }

    public void setFri_to_time(String fri_to_time) {
        this.fri_to_time = fri_to_time;
    }

    public String getFri_break1_from() {
        return fri_break1_from;
    }

    public void setFri_break1_from(String fri_break1_from) {
        this.fri_break1_from = fri_break1_from;
    }

    public String getFri_break1_to() {
        return fri_break1_to;
    }

    public void setFri_break1_to(String fri_break1_to) {
        this.fri_break1_to = fri_break1_to;
    }

    public String getFri_break2_from() {
        return fri_break2_from;
    }

    public void setFri_break2_from(String fri_break2_from) {
        this.fri_break2_from = fri_break2_from;
    }

    public String getFri_break2_to() {
        return fri_break2_to;
    }

    public void setFri_break2_to(String fri_break2_to) {
        this.fri_break2_to = fri_break2_to;
    }

    public String getFri_break3_from() {
        return fri_break3_from;
    }

    public void setFri_break3_from(String fri_break3_from) {
        this.fri_break3_from = fri_break3_from;
    }

    public String getFri_break3_to() {
        return fri_break3_to;
    }

    public void setFri_break3_to(String fri_break3_to) {
        this.fri_break3_to = fri_break3_to;
    }

    public String getFri_break4_from() {
        return fri_break4_from;
    }

    public void setFri_break4_from(String fri_break4_from) {
        this.fri_break4_from = fri_break4_from;
    }

    public String getFri_break4_to() {
        return fri_break4_to;
    }

    public void setFri_break4_to(String fri_break4_to) {
        this.fri_break4_to = fri_break4_to;
    }

    public String getFri_break5_from() {
        return fri_break5_from;
    }

    public void setFri_break5_from(String fri_break5_from) {
        this.fri_break5_from = fri_break5_from;
    }

    public String getFri_break5_to() {
        return fri_break5_to;
    }

    public void setFri_break5_to(String fri_break5_to) {
        this.fri_break5_to = fri_break5_to;
    }

    public String getSat_from_time() {
        return sat_from_time;
    }

    public void setSat_from_time(String sat_from_time) {
        this.sat_from_time = sat_from_time;
    }

    public String getSat_to_time() {
        return sat_to_time;
    }

    public void setSat_to_time(String sat_to_time) {
        this.sat_to_time = sat_to_time;
    }

    public String getSat_break1_from() {
        return sat_break1_from;
    }

    public void setSat_break1_from(String sat_break1_from) {
        this.sat_break1_from = sat_break1_from;
    }

    public String getSat_break1_to() {
        return sat_break1_to;
    }

    public void setSat_break1_to(String sat_break1_to) {
        this.sat_break1_to = sat_break1_to;
    }

    public String getSat_break2_from() {
        return sat_break2_from;
    }

    public void setSat_break2_from(String sat_break2_from) {
        this.sat_break2_from = sat_break2_from;
    }

    public String getSat_break2_to() {
        return sat_break2_to;
    }

    public void setSat_break2_to(String sat_break2_to) {
        this.sat_break2_to = sat_break2_to;
    }

    public String getSat_break3_from() {
        return sat_break3_from;
    }

    public void setSat_break3_from(String sat_break3_from) {
        this.sat_break3_from = sat_break3_from;
    }

    public String getSat_break3_to() {
        return sat_break3_to;
    }

    public void setSat_break3_to(String sat_break3_to) {
        this.sat_break3_to = sat_break3_to;
    }

    public String getSat_break4_from() {
        return sat_break4_from;
    }

    public void setSat_break4_from(String sat_break4_from) {
        this.sat_break4_from = sat_break4_from;
    }

    public String getSat_break4_to() {
        return sat_break4_to;
    }

    public void setSat_break4_to(String sat_break4_to) {
        this.sat_break4_to = sat_break4_to;
    }

    public String getSat_break5_from() {
        return sat_break5_from;
    }

    public void setSat_break5_from(String sat_break5_from) {
        this.sat_break5_from = sat_break5_from;
    }

    public String getSat_break5_to() {
        return sat_break5_to;
    }

    public void setSat_break5_to(String sat_break5_to) {
        this.sat_break5_to = sat_break5_to;
    }

    public String getSun_from_time() {
        return sun_from_time;
    }

    public void setSun_from_time(String sun_from_time) {
        this.sun_from_time = sun_from_time;
    }

    public String getSun_to_time() {
        return sun_to_time;
    }

    public void setSun_to_time(String sun_to_time) {
        this.sun_to_time = sun_to_time;
    }

    public String getSun_break1_from() {
        return sun_break1_from;
    }

    public void setSun_break1_from(String sun_break1_from) {
        this.sun_break1_from = sun_break1_from;
    }

    public String getSun_break1_to() {
        return sun_break1_to;
    }

    public void setSun_break1_to(String sun_break1_to) {
        this.sun_break1_to = sun_break1_to;
    }

    public String getSun_break2_from() {
        return sun_break2_from;
    }

    public void setSun_break2_from(String sun_break2_from) {
        this.sun_break2_from = sun_break2_from;
    }

    public String getSun_break2_to() {
        return sun_break2_to;
    }

    public void setSun_break2_to(String sun_break2_to) {
        this.sun_break2_to = sun_break2_to;
    }

    public String getSun_break3_from() {
        return sun_break3_from;
    }

    public void setSun_break3_from(String sun_break3_from) {
        this.sun_break3_from = sun_break3_from;
    }

    public String getSun_break3_to() {
        return sun_break3_to;
    }

    public void setSun_break3_to(String sun_break3_to) {
        this.sun_break3_to = sun_break3_to;
    }

    public String getSun_break4_from() {
        return sun_break4_from;
    }

    public void setSun_break4_from(String sun_break4_from) {
        this.sun_break4_from = sun_break4_from;
    }

    public String getSun_break4_to() {
        return sun_break4_to;
    }

    public void setSun_break4_to(String sun_break4_to) {
        this.sun_break4_to = sun_break4_to;
    }

    public String getSun_break5_from() {
        return sun_break5_from;
    }

    public void setSun_break5_from(String sun_break5_from) {
        this.sun_break5_from = sun_break5_from;
    }

    public String getSun_break5_to() {
        return sun_break5_to;
    }

    public void setSun_break5_to(String sun_break5_to) {
        this.sun_break5_to = sun_break5_to;
    }



    public String getAll_break1_from() {
        return all_break1_from;
    }

    public void setAll_break1_from(String all_break1_from) {
        this.all_break1_from = all_break1_from;
    }

    public String getAll_break1_to() {
        return all_break1_to;
    }

    public void setAll_break1_to(String all_break1_to) {
        this.all_break1_to = all_break1_to;
    }

    public String getAll_break2_from() {
        return all_break2_from;
    }

    public void setAll_break2_from(String all_break2_from) {
        this.all_break2_from = all_break2_from;
    }

    public String getAll_break2_to() {
        return all_break2_to;
    }

    public void setAll_break2_to(String all_break2_to) {
        this.all_break2_to = all_break2_to;
    }

    public String getAll_break3_from() {
        return all_break3_from;
    }

    public void setAll_break3_from(String all_break3_from) {
        this.all_break3_from = all_break3_from;
    }

    public String getAll_break3_to() {
        return all_break3_to;
    }

    public void setAll_break3_to(String all_break3_to) {
        this.all_break3_to = all_break3_to;
    }

    public String getAll_break4_from() {
        return all_break4_from;
    }

    public void setAll_break4_from(String all_break4_from) {
        this.all_break4_from = all_break4_from;
    }

    public String getAll_break4_to() {
        return all_break4_to;
    }

    public void setAll_break4_to(String all_break4_to) {
        this.all_break4_to = all_break4_to;
    }

    public String getAll_break5_from() {
        return all_break5_from;
    }

    public void setAll_break5_from(String all_break5_from) {
        this.all_break5_from = all_break5_from;
    }

    public String getAll_break5_to() {
        return all_break5_to;
    }

    public void setAll_break5_to(String all_break5_to) {
        this.all_break5_to = all_break5_to;
    }

    public String getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(String admin_flag) {
        this.admin_flag = admin_flag;
    }

    public String getOnly_me_flag() {
        return only_me_flag;
    }

    public void setOnly_me_flag(String only_me_flag) {
        this.only_me_flag = only_me_flag;
    }
}
