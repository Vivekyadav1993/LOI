package models.mycustomer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCustomer {

    @SerializedName("output")
    @Expose
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

}