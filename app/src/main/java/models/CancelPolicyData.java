package models;

/**
 * Created by teknik on 11/25/2017.
 */

public class CancelPolicyData {

    private String id , ser_count , refund_amount ;

    public CancelPolicyData(String _id , String _ser_count , String _refund_amount){
        id = _id;

        ser_count = _ser_count;

        refund_amount = _refund_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSer_count() {
        return ser_count;
    }

    public void setSer_count(String ser_count) {
        this.ser_count = ser_count;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }
}
