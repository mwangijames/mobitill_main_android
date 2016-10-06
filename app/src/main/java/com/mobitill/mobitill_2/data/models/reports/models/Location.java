package com.mobitill.mobitill_2.data.models.reports.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Location {

    @SerializedName("$reql_type$")
    @Expose
    private String $reqlType$;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = new ArrayList<Double>();
    @SerializedName("type")
    @Expose
    private String type;

    /**
     *
     * @return
     * The $reqlType$
     */
    public String get$reqlType$() {
        return $reqlType$;
    }

    /**
     *
     * @param $reqlType$
     * The $reql_type$
     */
    public void set$reqlType$(String $reqlType$) {
        this.$reqlType$ = $reqlType$;
    }

    /**
     *
     * @return
     * The coordinates
     */
    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @param coordinates
     * The coordinates
     */
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

}