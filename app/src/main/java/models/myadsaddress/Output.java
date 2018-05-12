package models.myadsaddress;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("industry_id")
    @Expose
    private String industryId;
    @SerializedName("publish_id")
    @Expose
    private String publishId;
    @SerializedName("publish_type")
    @Expose
    private String publishType;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_phone")
    @Expose
    private String businessPhone;
    @SerializedName("businessaddress")
    @Expose
    private List<Businessaddress> businessaddress = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
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

    public List<Businessaddress> getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(List<Businessaddress> businessaddress) {
        this.businessaddress = businessaddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}