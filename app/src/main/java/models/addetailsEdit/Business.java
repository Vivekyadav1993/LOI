package models.addetailsEdit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 3/23/2018.
 */

public class Business {


    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_phone")
    @Expose
    private String businessPhone;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("mon_from_time")
    @Expose
    private String monFromTime;
    @SerializedName("mon_to_time")
    @Expose
    private String monToTime;
    @SerializedName("tue_from_time")
    @Expose
    private String tueFromTime;
    @SerializedName("tue_to_time")
    @Expose
    private String tueToTime;
    @SerializedName("wed_from_time")
    @Expose
    private String wedFromTime;
    @SerializedName("wed_to_time")
    @Expose
    private String wedToTime;
    @SerializedName("thru_from_time")
    @Expose
    private String thruFromTime;
    @SerializedName("thru_to_time")
    @Expose
    private String thruToTime;
    @SerializedName("fri_from_time")
    @Expose
    private String friFromTime;
    @SerializedName("fri_to_time")
    @Expose
    private String friToTime;
    @SerializedName("sat_from_time")
    @Expose
    private String satFromTime;
    @SerializedName("sat_to_time")
    @Expose
    private String satToTime;
    @SerializedName("sun_from_time")
    @Expose
    private String sunFromTime;
    @SerializedName("sun_to_time")
    @Expose
    private String sunToTime;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonFromTime() {
        return monFromTime;
    }

    public void setMonFromTime(String monFromTime) {
        this.monFromTime = monFromTime;
    }

    public String getMonToTime() {
        return monToTime;
    }

    public void setMonToTime(String monToTime) {
        this.monToTime = monToTime;
    }

    public String getTueFromTime() {
        return tueFromTime;
    }

    public void setTueFromTime(String tueFromTime) {
        this.tueFromTime = tueFromTime;
    }

    public String getTueToTime() {
        return tueToTime;
    }

    public void setTueToTime(String tueToTime) {
        this.tueToTime = tueToTime;
    }

    public String getWedFromTime() {
        return wedFromTime;
    }

    public void setWedFromTime(String wedFromTime) {
        this.wedFromTime = wedFromTime;
    }

    public String getWedToTime() {
        return wedToTime;
    }

    public void setWedToTime(String wedToTime) {
        this.wedToTime = wedToTime;
    }

    public String getThruFromTime() {
        return thruFromTime;
    }

    public void setThruFromTime(String thruFromTime) {
        this.thruFromTime = thruFromTime;
    }

    public String getThruToTime() {
        return thruToTime;
    }

    public void setThruToTime(String thruToTime) {
        this.thruToTime = thruToTime;
    }

    public String getFriFromTime() {
        return friFromTime;
    }

    public void setFriFromTime(String friFromTime) {
        this.friFromTime = friFromTime;
    }

    public String getFriToTime() {
        return friToTime;
    }

    public void setFriToTime(String friToTime) {
        this.friToTime = friToTime;
    }

    public String getSatFromTime() {
        return satFromTime;
    }

    public void setSatFromTime(String satFromTime) {
        this.satFromTime = satFromTime;
    }

    public String getSatToTime() {
        return satToTime;
    }

    public void setSatToTime(String satToTime) {
        this.satToTime = satToTime;
    }

    public String getSunFromTime() {
        return sunFromTime;
    }

    public void setSunFromTime(String sunFromTime) {
        this.sunFromTime = sunFromTime;
    }

    public String getSunToTime() {
        return sunToTime;
    }

    public void setSunToTime(String sunToTime) {
        this.sunToTime = sunToTime;
    }

}
