package com.tw.superParkingBoy;

import com.tw.parkinglot.Parking;
import com.tw.pojo.Car;
import com.tw.pojo.ParkingLot;
import com.tw.pojo.ParkingTicket;
import com.tw.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingTest {
    ArrayList<Car> carList = new ArrayList<>();
    ParkingLot parkingLot;
    ArrayList<User> users = new ArrayList<>();
    User sri;
    User heny;
    User lucy;

    @Before
    public void ready() {
        Car tesla = new Car(1001, "特斯拉");
        Car bmw = new Car(1002, "宝马");
        carList.add(tesla);
        carList.add(bmw);

        sri = new User(100001, "Sri", "男", "桥车", 1006, "比亚迪");
        heny = new User(100002, "heny", "女", "跑车", 1100, "兰博基尼");
        lucy = new User(100004, "lucy", "男", "卡车", 1400, "解放");
    }

    //任务分解1
    @Test
    public void should_return_one_ticket_when_parkingLot_have_one_position() {
        parkingLot = new ParkingLot(101, 3, carList);
        //When 停1辆车
        users.add(sri);
        //Then 返回一张停车票
        List<ParkingTicket> parking = new Parking().parking(parkingLot, users);
        assertEquals(1, parking.size());
    }
}
