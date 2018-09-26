package models.joinPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 7/3/2018.
 */
public class JoinPageModel {

    @SerializedName("output")
    @Expose
    private Output output;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("staffwise_booking")
    @Expose
    private String staffwiseBooking;
    @SerializedName("advance_booking_status")
    @Expose
    private String advanceBookingStatus;
    @SerializedName("no_of_days")
    @Expose
    private String noOfDays;
    @SerializedName("no_of_days_advance")
    @Expose
    private Object noOfDaysAdvance;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStaffwiseBooking() {
        return staffwiseBooking;
    }

    public void setStaffwiseBooking(String staffwiseBooking) {
        this.staffwiseBooking = staffwiseBooking;
    }

    public String getAdvanceBookingStatus() {
        return advanceBookingStatus;
    }

    public void setAdvanceBookingStatus(String advanceBookingStatus) {
        this.advanceBookingStatus = advanceBookingStatus;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Object getNoOfDaysAdvance() {
        return noOfDaysAdvance;
    }

    public void setNoOfDaysAdvance(Object noOfDaysAdvance) {
        this.noOfDaysAdvance = noOfDaysAdvance;
    }

}