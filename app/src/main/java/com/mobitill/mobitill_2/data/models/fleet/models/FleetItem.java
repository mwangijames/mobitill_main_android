package com.mobitill.mobitill_2.data.models.fleet.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FleetItem {

    @SerializedName("fleetno")
    @Expose
    private String fleetno;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("plateno")
    @Expose
    private String plateno;

    /**
     *
     * @return
     * The fleetno
     */
    public String getFleetno() {
        return fleetno;
    }

    /**
     *
     * @param fleetno
     * The fleetno
     */
    public void setFleetno(String fleetno) {
        this.fleetno = fleetno;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The plateno
     */
    public String getPlateno() {
        return plateno;
    }

    /**
     *
     * @param plateno
     * The plateno
     */
    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

}