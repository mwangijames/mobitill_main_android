package com.mobitill.mobitill_2.data.models.clients.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Clients {

    @SerializedName("data")
    @Expose
    private List<Client> data = new ArrayList<Client>();

    /**
     *
     * @return
     * The data
     */
    public List<Client> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Client> data) {
        this.data = data;
    }

}
