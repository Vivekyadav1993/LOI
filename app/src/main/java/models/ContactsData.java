package models;

/**
 * Created by teknik on 10/13/2017.
 */

public class ContactsData {

    private String name , number  ;
    private boolean selected;

    public ContactsData(String _name , String _number , boolean _selected){
        name = _name;

        number = _number;

        selected = _selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
