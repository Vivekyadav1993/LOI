package models;

/**
 * Created by teknik on 11/22/2017.
 */

public class SpecQTYDATA {

    private String id ;

    private int qty  ;

    public SpecQTYDATA(String _id , int _qty)
    {

        id = _id;

        qty = _qty;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
