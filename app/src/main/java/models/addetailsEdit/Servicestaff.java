package models.addetailsEdit;

import com.google.gson.annotations.SerializedName;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servicestaff {
    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("staff_name")
    @Expose
    private String staffName;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

}