package com.tw.pojo;

/**
 * 汽车类
 */
public class Car {

    private Integer cid;
    private String carName;

    public Car(Integer cid, String carName) {
        this.cid = cid;
        this.carName = carName;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    @Override
    public String toString() {
        return "Car{" +
                "cid=" + cid +
                ", carName='" + carName + '\'' +
                '}';
    }
}
