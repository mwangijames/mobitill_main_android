package com.mobitill.mobitill_2.data.models.clients.models;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ClientsParams {

    @SerializedName("fetch")
    @Expose
    private ClientsFetch fetch;

    /**
     *
     * @return
     * The fetch
     */
    public ClientsFetch getFetch() {
        return fetch;
    }

    /**
     *
     * @param fetch
     * The fetch
     */
    public void setFetch(ClientsFetch fetch) {
        this.fetch = fetch;
    }

}
