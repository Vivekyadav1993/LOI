package models;

/**
 * Created by teknik on 11/25/2017.
 */

public class PackageConfigData  {

    private String no_of_days , duration_per_class , no_of_class , package_cost ,cancelled_by_vendor_refund , cancelled_by_vendor_class_in_next_period ,
            cancelled_by_customer_refund , cancelled_by_customer_class_in_next_period ;

    public PackageConfigData(String _no_of_days , String _duration_per_class , String _no_of_class , String _package_cost
            ,String _cancelled_by_vendor_refund , String _cancelled_by_vendor_class_in_next_period ,
                             String _cancelled_by_customer_refund , String _cancelled_by_customer_class_in_next_period){

        no_of_days = _no_of_days;
        duration_per_class = _duration_per_class;
        no_of_class = _no_of_class;
        package_cost = _package_cost;
        cancelled_by_vendor_refund = _cancelled_by_vendor_refund;
        cancelled_by_vendor_class_in_next_period= _cancelled_by_vendor_class_in_next_period;
        cancelled_by_customer_refund= _cancelled_by_customer_refund;
        cancelled_by_customer_class_in_next_period= _cancelled_by_customer_class_in_next_period;





    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getDuration_per_class() {
        return duration_per_class;
    }

    public void setDuration_per_class(String duration_per_class) {
        this.duration_per_class = duration_per_class;
    }

    public String getNo_of_class() {
        return no_of_class;
    }

    public void setNo_of_class(String no_of_class) {
        this.no_of_class = no_of_class;
    }

    public String getPackage_cost() {
        return package_cost;
    }

    public void setPackage_cost(String package_cost) {
        this.package_cost = package_cost;
    }

    public String getCancelled_by_vendor_refund() {
        return cancelled_by_vendor_refund;
    }

    public void setCancelled_by_vendor_refund(String cancelled_by_vendor_refund) {
        this.cancelled_by_vendor_refund = cancelled_by_vendor_refund;
    }

    public String getCancelled_by_vendor_class_in_next_period() {
        return cancelled_by_vendor_class_in_next_period;
    }

    public void setCancelled_by_vendor_class_in_next_period(String cancelled_by_vendor_class_in_next_period) {
        this.cancelled_by_vendor_class_in_next_period = cancelled_by_vendor_class_in_next_period;
    }

    public String getCancelled_by_customer_refund() {
        return cancelled_by_customer_refund;
    }

    public void setCancelled_by_customer_refund(String cancelled_by_customer_refund) {
        this.cancelled_by_customer_refund = cancelled_by_customer_refund;
    }

    public String getCancelled_by_customer_class_in_next_period() {
        return cancelled_by_customer_class_in_next_period;
    }

    public void setCancelled_by_customer_class_in_next_period(String cancelled_by_customer_class_in_next_period) {
        this.cancelled_by_customer_class_in_next_period = cancelled_by_customer_class_in_next_period;
    }
}
