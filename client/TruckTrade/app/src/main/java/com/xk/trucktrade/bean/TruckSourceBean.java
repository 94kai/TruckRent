package com.xk.trucktrade.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xk on 2016/6/12 18:41.
 */
public class TruckSourceBean implements Serializable{
    private String _id;
    private String load_date;
    private String start_place;
    private String stop_place;
    private String start_place_point;
    private String introcd;
    private String publish_date;
    private String state;
    private String truck_id;
    private TruckBean truckBean;
    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public TruckBean getTruckBean() {
        return truckBean;
    }

    public void setTruckBean(TruckBean truckBean) {
        this.truckBean = truckBean;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLoad_date() {
        return load_date;
    }

    public void setLoad_date(String load_date) {
        this.load_date = load_date;
    }

    public String getStart_place() {
        return start_place;
    }

    public void setStart_place(String start_place) {
        this.start_place = start_place;
    }

    public String getStop_place() {
        return stop_place;
    }

    public void setStop_place(String stop_place) {
        this.stop_place = stop_place;
    }

    public String getStart_place_point() {
        return start_place_point;
    }

    public void setStart_place_point(String start_place_point) {
        this.start_place_point = start_place_point;
    }

    public String getIntrocd() {
        return introcd;
    }

    public void setIntrocd(String introcd) {
        this.introcd = introcd;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(String truck_id) {
        this.truck_id = truck_id;
    }

    @Override
    public String toString() {
        return "TruckSourceBean{" +
                "_id='" + _id + '\'' +
                ", load_date='" + load_date + '\'' +
                ", start_place='" + start_place + '\'' +
                ", stop_place='" + stop_place + '\'' +
                ", start_place_point='" + start_place_point + '\'' +
                ", introcd='" + introcd + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", state='" + state + '\'' +
                ", truck_id='" + truck_id + '\'' +
                ", truckBean=" + truckBean +
                ", userBean=" + userBean +
                '}';
    }


}
