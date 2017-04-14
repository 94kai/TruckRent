package com.xk.trucktrade.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xk on 2016/6/12 18:41.
 */
public class TruckBean implements Serializable{
    private String _id;
    private String truckBrith;
    private String length;
    private String width;
    private String hight;
    private String weight;
    private String variety;
    private String truckCardNumber;
    private UserBean master;
    private List<String> truckPhotos;

    public TruckBean() {
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTruckBrith() {
        return truckBrith;
    }

    public void setTruckBrith(String truckBrith) {
        this.truckBrith = truckBrith;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getTruckCardNumber() {
        return truckCardNumber;
    }

    public void setTruckCardNumber(String truckCardNumber) {
        this.truckCardNumber = truckCardNumber;
    }

    public UserBean getMaster() {
        return master;
    }

    public void setMaster(UserBean master) {
        this.master = master;
    }

    public List<String> getTruckPhotos() {
        return truckPhotos;
    }

    public void setTruckPhotos(List<String> truckPhotos) {
        this.truckPhotos = truckPhotos;
    }

    @Override
    public String toString() {
        return "TruckBean{" +
                "_id=" + _id +
                ", truckBrith='" + truckBrith + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", hight=" + hight +
                ", weight=" + weight +
                ", variety=" + variety +
                ", truckCardNumber='" + truckCardNumber + '\'' +
                ", master=" + master +
                ", truckPhotos=" + truckPhotos +
                '}';
    }
}
