package models.joinPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 7/3/2018.
 */
public class Service {

    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("morning_est_time")
    @Expose
    private String morningEstTime;
    @SerializedName("evening_est_time")
    @Expose
    private String eveningEstTime;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMorningEstTime() {
        return morningEstTime;
    }

    public void setMorningEstTime(String morningEstTime) {
        this.morningEstTime = morningEstTime;
    }

    public String getEveningEstTime() {
        return eveningEstTime;
    }

    public void setEveningEstTime(String eveningEstTime) {
        this.eveningEstTime = eveningEstTime;
    }

}