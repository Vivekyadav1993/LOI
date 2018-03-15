package models;

import java.util.ArrayList;

/**
 * Created by teknik on 11/7/2017.
 */

public class BusinessData {

    private String business_id , publish_type , publish_id , Business_name , business_phone , business_industry_id , regularevent_flag;

    private ArrayList<BusinessHourData> adderess_data = new ArrayList<>();

    public BusinessData(){

    }

     public BusinessData(String _business_id ,String _publish_type , String _publish_id , String _Business_name , String _business_phone , String _business_industry_id ,String _regularevent_flag, ArrayList<BusinessHourData> _adderess_data){

         business_id = _business_id;
         publish_type = _publish_type;
         publish_id = _publish_id;
         Business_name = _Business_name;
         business_phone = _business_phone;
         business_industry_id = _business_industry_id;
         regularevent_flag = _regularevent_flag;
         adderess_data = _adderess_data;

     }

    public String getRegularevent_flag() {
        return regularevent_flag;
    }

    public void setRegularevent_flag(String regularevent_flag) {
        this.regularevent_flag = regularevent_flag;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getPublish_type() {
        return publish_type;
    }

    public void setPublish_type(String publish_type) {
        this.publish_type = publish_type;
    }

    public String getPublish_id() {
        return publish_id;
    }

    public void setPublish_id(String publish_id) {
        this.publish_id = publish_id;
    }

    public String getBusiness_name() {
        return Business_name;
    }

    public void setBusiness_name(String business_name) {
        Business_name = business_name;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getBusiness_industry_id() {
        return business_industry_id;
    }

    public void setBusiness_industry_id(String business_industry_id) {
        this.business_industry_id = business_industry_id;
    }

    public ArrayList<BusinessHourData> getAdderess_data() {
        return adderess_data;
    }

    public void setAdderess_data(ArrayList<BusinessHourData> adderess_data) {
        this.adderess_data = adderess_data;
    }
}
