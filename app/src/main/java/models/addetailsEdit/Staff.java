package models.addetailsEdit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {

    private String staffId;

    private String staffName;

    public Staff(String staff_id, String staff_name) {
        this.staffId=staff_id;
        this.staffName=staff_name;
    }

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