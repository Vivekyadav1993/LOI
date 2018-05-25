package models.mycustomer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("staff_name")
    @Expose
    private String staffName;
    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;
    @SerializedName("estimate_time")
    @Expose
    private String estimateTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chk_status")
    @Expose
    private String chkStatus;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getChkStatus() {
        return chkStatus;
    }

    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus;
    }

}