package com.mobitill.mobitill_2.data.models.products.models.delete;

/**
 * Created by james on 10/6/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ProductDeleteResponseData {

    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("errors")
    @Expose
    private Integer errors;
    @SerializedName("inserted")
    @Expose
    private Integer inserted;
    @SerializedName("replaced")
    @Expose
    private Integer replaced;
    @SerializedName("skipped")
    @Expose
    private Integer skipped;
    @SerializedName("unchanged")
    @Expose
    private Integer unchanged;

    /**
     *
     * @return
     * The deleted
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     *
     * @param deleted
     * The deleted
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     *
     * @return
     * The errors
     */
    public Integer getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    /**
     *
     * @return
     * The inserted
     */
    public Integer getInserted() {
        return inserted;
    }

    /**
     *
     * @param inserted
     * The inserted
     */
    public void setInserted(Integer inserted) {
        this.inserted = inserted;
    }

    /**
     *
     * @return
     * The replaced
     */
    public Integer getReplaced() {
        return replaced;
    }

    /**
     *
     * @param replaced
     * The replaced
     */
    public void setReplaced(Integer replaced) {
        this.replaced = replaced;
    }

    /**
     *
     * @return
     * The skipped
     */
    public Integer getSkipped() {
        return skipped;
    }

    /**
     *
     * @param skipped
     * The skipped
     */
    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    /**
     *
     * @return
     * The unchanged
     */
    public Integer getUnchanged() {
        return unchanged;
    }

    /**
     *
     * @param unchanged
     * The unchanged
     */
    public void setUnchanged(Integer unchanged) {
        this.unchanged = unchanged;
    }

}
