package models;

/**
 * Created by teknik on 11/24/2017.
 */

public class StaffSelecData {

    private String ser_id , staff_id , staff_name , select ;

    public StaffSelecData(String _ser_id , String _staff_id , String _staff_name , String _select){
        ser_id = _ser_id;
        staff_id = _staff_id;
        staff_name = _staff_name;
        select = _select;




    }


    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
