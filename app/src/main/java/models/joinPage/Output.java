package models.joinPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovo on 7/3/2018.
 */

public class Output {

    @SerializedName("address")
    @Expose
    private List<Address> address = null;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

}