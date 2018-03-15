package models;

/**
 * Created by teknik on 11/27/2017.
 */

public class CategoryData  {

    private String id , name ;

    public CategoryData(String _id , String _name)
    {

        id = _id;

        name = _name;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
