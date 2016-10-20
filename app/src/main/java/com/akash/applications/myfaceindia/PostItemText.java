package com.akash.applications.myfaceindia;

/**
 * Created by Akash on 19-10-2016.
 */
public class PostItemText {
    String status,datetime;

    public PostItemText(String status, String datetime) {
        super();
        this.status = status;
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
