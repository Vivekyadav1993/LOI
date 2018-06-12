package models.businesslist;

/**
 * Created by lenovo on 3/26/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("business_address_id")
    @Expose
    private String businessAddressId;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("open_status")
    @Expose
    private String openStatus;
    @SerializedName("open_time")
    @Expose
    private String openTime;


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessAddressId() {
        return businessAddressId;
    }

    public void setBusinessAddressId(String businessAddressId) {
        this.businessAddressId = businessAddressId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

}