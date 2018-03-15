package models;

/**
 * Created by teknik on 11/25/2017.
 */

public class DiscountPolicyData {

    private String id , class_count , period_count , day_count ,discount_amount ;

    public DiscountPolicyData(String _id , String _class_count , String _period_count ,
                              String _day_count ,String _discount_amount) {

        id = _id;

        class_count = _class_count;

        period_count= _period_count;

        day_count = _day_count;

        discount_amount = _discount_amount;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_count() {
        return class_count;
    }

    public void setClass_count(String class_count) {
        this.class_count = class_count;
    }

    public String getPeriod_count() {
        return period_count;
    }

    public void setPeriod_count(String period_count) {
        this.period_count = period_count;
    }

    public String getDay_count() {
        return day_count;
    }

    public void setDay_count(String day_count) {
        this.day_count = day_count;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }
}
