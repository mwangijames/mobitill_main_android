package com.mobitill.mobitill_2.data.models.reports.models;


        import javax.annotation.Generated;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ReportItem {

    @SerializedName("Vehicle Reg")
    @Expose
    private String vehicleReg;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("cashier")
    @Expose
    private String cashier;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("terminalName")
    @Expose
    private String terminalName;
    @SerializedName("terminalid")
    @Expose
    private String terminalid;
    @SerializedName("tickno")
    @Expose
    private String tickno;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     *
     * @return
     * The vehicleReg
     */
    public String getVehicleReg() {
        return vehicleReg;
    }

    /**
     *
     * @param vehicleReg
     * The Vehicle Reg
     */
    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    /**
     *
     * @return
     * The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *
     * @param appid
     * The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     *
     * @return
     * The cashier
     */
    public String getCashier() {
        return cashier;
    }

    /**
     *
     * @param cashier
     * The cashier
     */
    public void setCashier(String cashier) {
        this.cashier = cashier;
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
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     * The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     * The productid
     */
    public String getProductid() {
        return productid;
    }

    /**
     *
     * @param productid
     * The productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     *
     * @return
     * The productname
     */
    public String getProductname() {
        return productname;
    }

    /**
     *
     * @param productname
     * The productname
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     *
     * @return
     * The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The terminalName
     */
    public String getTerminalName() {
        return terminalName;
    }

    /**
     *
     * @param terminalName
     * The terminalName
     */
    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    /**
     *
     * @return
     * The terminalid
     */
    public String getTerminalid() {
        return terminalid;
    }

    /**
     *
     * @param terminalid
     * The terminalid
     */
    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    /**
     *
     * @return
     * The tickno
     */
    public String getTickno() {
        return tickno;
    }

    /**
     *
     * @param tickno
     * The tickno
     */
    public void setTickno(String tickno) {
        this.tickno = tickno;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

}