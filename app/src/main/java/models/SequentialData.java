package models;

/**
 * Created by teknik on 11/27/2017.
 */

public class SequentialData {

    private String ser_id ;
    int pos ;

    public SequentialData(String _ser_id , int _pos){
        ser_id = _ser_id;

        pos = _pos;


    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
