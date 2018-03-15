package models;

/**
 * Created by teknik on 11/22/2017.
 */

public class ManageSpecPosData {

    private String res_id , spec_id , spec_name , spec_qty , spec_pos;

    public ManageSpecPosData(String _res_id ,String _spec_id ,String _spec_name
            ,String _spec_qty ,String _spec_pos){

        res_id = _res_id;

        spec_id = _spec_id;

        spec_name = _spec_name;

        spec_qty = _spec_qty;

        spec_pos = _spec_pos;




    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getSpec_qty() {
        return spec_qty;
    }

    public void setSpec_qty(String spec_qty) {
        this.spec_qty = spec_qty;
    }

    public String getSpec_pos() {
        return spec_pos;
    }

    public void setSpec_pos(String spec_pos) {
        this.spec_pos = spec_pos;
    }
}
