package models.addetailsEdit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_duration")
    @Expose
    private String serviceDuration;
    @SerializedName("service_buffertime")
    @Expose
    private String serviceBuffertime;
    @SerializedName("servicestaff")
    @Expose
    private List<Servicestaff> servicestaff = null;

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

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public String getServiceBuffertime() {
        return serviceBuffertime;
    }

    public void setServiceBuffertime(String serviceBuffertime) {
        this.serviceBuffertime = serviceBuffertime;
    }

    public List<Servicestaff> getServicestaff() {
        return servicestaff;
    }

    public void setServicestaff(List<Servicestaff> servicestaff) {
        this.servicestaff = servicestaff;
    }

}