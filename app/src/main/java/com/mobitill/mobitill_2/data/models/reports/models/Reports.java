package com.mobitill.mobitill_2.data.models.reports.models;

/**
 * Created by james on 9/2/2016.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Reports {

    @SerializedName("data")
    @Expose
    private List<ReportItem> data = new ArrayList<ReportItem>();

    /**
     *
     * @return
     * The data
     */
    public List<ReportItem> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ReportItem> data) {
        this.data = data;
    }

}
