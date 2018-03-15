package models;

/**
 * Created by teknik on 11/3/2017.
 */

public class GroupData {


    private String group_id , group_name , group_recip_count ;

    public GroupData(String _group_id , String _group_name , String _group_recip_name){

        group_id = _group_id;

        group_name = _group_name;

        group_recip_count = _group_recip_name;

    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_recip_count() {
        return group_recip_count;
    }

    public void setGroup_recip_count(String group_recip_count) {
        this.group_recip_count = group_recip_count;
    }
}
