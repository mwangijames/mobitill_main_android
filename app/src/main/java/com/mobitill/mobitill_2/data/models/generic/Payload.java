package com.mobitill.mobitill_2.data.models.generic;

/**
 * Created by james on 11/2/2016.
 */

public class Payload {

    private String model;
    private String action;
    private String payload;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isEmpty(){
        return (model == null || "".equals(model)) ||
                (action == null || "".equals(action)) ||
                (payload == null || "".equals(payload));
    }
}
