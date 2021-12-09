package com.tw.pojo;

import java.util.List;

/**
 * 停车场类
 */
public class ParkingLot {

    private Integer pid;
    private Integer pNumber;
    private List<Car> carList;

    public ParkingLot(Integer pid, Integer pNumber, List<Car> carList) {
        this.pid = pid;
        this.pNumber = pNumber;
        this.carList = carList;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getpNumber() {
        return pNumber;
    }

    public void setpNumber(Integer pNumber) {
        this.pNumber = pNumber;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "pid=" + pid +
                ", pNumber=" + pNumber +
                ", carList=" + carList +
                '}';
    }
}
