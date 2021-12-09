package com.tw.pojo;

/**
 * 停车票类
 */
public class ParkingTicket {

    private int tid;
    private String tName; //对应着用户的名字
    private int cid; //对应车的id
    private int pid; //对应停车场的id

    public ParkingTicket(int tid, String tName, int cid, int pid) {
        this.tid = tid;
        this.tName = tName;
        this.cid = cid;
        this.pid = pid;
    }

    public ParkingTicket() {
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "tid=" + tid +
                ", tName='" + tName + '\'' +
                ", cid=" + cid +
                ", pid=" + pid +
                '}';
    }
}
