package com.tw.parkinglot;

import com.tw.pojo.Car;
import com.tw.pojo.ParkingLot;
import com.tw.pojo.ParkingTicket;
import com.tw.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    //任务分解1：
    @Test
    public void should_parking_one_and_return_parkingTicket_when_parkingLot_have_one_position() {
        //Given 有1个空车位的停车场
        parkingLot = new ParkingLot(101, 3, carList);
        //When 停1辆车
        users.add(sri);
        //Then 返回一张停车票
        assertEquals(1, new Parking().parking(parkingLot, users).size());
    }

    @Test
    public void should_return_many_tickets_when_parkingLot_have_many_positions() {
        //Given 有多个个空车位的停车场
        parkingLot = new ParkingLot(101, 10, carList);
        //When 连续停多辆车
        users.add(sri);
        users.add(heny);
        users.add(lucy);
        //Then 返回不同的停车票
        assertEquals(3, new Parking().parking(parkingLot, users).size());
        assertEquals("Sri", new Parking().parking(parkingLot, users).get(0).gettName());
    }

    @Test
    public void should_parking_failed_when_no_position_to_park() {
        //Given 一个没有空车位的停车场
        parkingLot = new ParkingLot(101, 2, carList);
        //When 停车
        users.add(sri);
        //Then 停车失败
        assertThrows(RuntimeException.class, () -> {
            new Parking().parking(parkingLot, users);
        });
    }

    //任务分解2：
    @Test
    public void should_pick_up_my_car_when_parkinglot_only_have_my_car() {
        //Given 一个只停了我的车的停车场
        parkingLot = new ParkingLot(101, 3, new ArrayList<>());
        users.add(sri);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot, users);
        //When 用停车票取车
        Car car = park.pickUp(parkingLot, tickets, tickets.get(0));
        //Then 取出我的车
        assertEquals("比亚迪", car.getCarName());
        assertEquals(1006, car.getCid());
    }

    @Test
    public void should_pick_up_my_car_when_parkinglot_have_many_cars() {
        //Given 一个停了多个车且包括我的车的停车场
        parkingLot = new ParkingLot(101, 10, carList);
        users.add(sri);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot, users);
        //When 用我的停车停车票取车
        Car car = park.pickUp(parkingLot, tickets, tickets.get(0));
        //Then 取出我的车
        assertEquals("比亚迪", car.getCarName());
        assertEquals(1006, car.getCid());
    }

    //任务分解3：
    @Test
    public void should_pick_up_failed_when_ticket_is_invalid() {
        //Given 一个停了我的车的停车场
        users.add(sri);
        parkingLot = new ParkingLot(101, 10, carList);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot, users);
        //When 用一张无效的票取车
        //Then 取车失败
        assertThrows(RuntimeException.class, () -> {
            park.pickUp(parkingLot, tickets, new ParkingTicket(10001, "Sri", 1006,parkingLot.getPid()));
        });
    }

    @Test
    public void should_pick_up_again_failed_when_car_had_pick_up() {
        //Given 一个停了我的车的停车场
        users.add(sri);
        parkingLot = new ParkingLot(101, 10, carList);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot, users);
        //When 用同一张票取车两次
        Car car1 = park.pickUp(parkingLot, tickets, tickets.get(0));
        //Then 第二次取车失败
        ParkingTicket ticket = new ParkingTicket(100101,"Sri",1006, parkingLot.getPid());
        assertThrows(RuntimeException.class, () -> {
            Car car2 = park.pickUp(parkingLot, tickets, ticket);
        });
    }
}
