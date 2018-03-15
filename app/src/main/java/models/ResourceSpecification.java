package models;

/**
 * Created by teknik on 11/14/2017.
 */

public class ResourceSpecification  {

    private String res_name , res_qty , res_pos ;

    public ResourceSpecification(String _res_name , String _res_qty )  {

        res_name = _res_name;

        res_qty = _res_qty ;


    }


    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_qty() {
        return res_qty;
    }

    public void setRes_qty(String res_qty) {
        this.res_qty = res_qty;
    }

    public String getRes_pos() {
        return res_pos;
    }

    public void setRes_pos(String res_pos) {
        this.res_pos = res_pos;
    }
}
