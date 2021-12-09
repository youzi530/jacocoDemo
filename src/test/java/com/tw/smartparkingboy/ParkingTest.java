package com.tw.smartparkingboy;

import com.tw.parkinglot.Parking;
import com.tw.pojo.Car;
import com.tw.pojo.ParkingLot;
import com.tw.pojo.ParkingTicket;
import com.tw.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void should_park_in_first_parkingLot_when_fist_parkingLot_has_more_parking_spaces() {
        //Given 有两个停车场，第一个空车位比第二个多
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 20, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 3, carList2);
        //When 停1辆车
        users.add(sri);
        List<ParkingTicket> parking = new Parking().parking(parkingLot1, parkingLot2, users);
        //Then 车停在第一个停车场
        assertEquals(1, parking.size());
        assertEquals(101, parking.get(0).getPid());
    }

    @Test
    public void should_park_in_second_parkingLot_when_second_parkingLot_has_more_parking_spaces() {
        //Given 有两个停车场，第二个空车位比第一个多
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 3, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 20, carList2);
        //When 停1辆车
        users.add(sri);
        List<ParkingTicket> parking = new Parking().parking(parkingLot1, parkingLot2, users);
        //Then 车停在第二个停车场
        assertEquals(1, parking.size());
        assertEquals(102, parking.get(0).getPid());
    }

    //任务分解2
    @Test
    public void should_park_in_different_parkingLot_when_two_parkingLot_has_same_parking_spaces() {
        //Given 有两个停车场，两个停车位一样多且足够
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 3, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 3, carList2);
        //When 按顺序停多辆车
        users.add(sri);
        users.add(heny);
        //Then 车辆按顺序交替停在第一个、第二个停车场
        List<ParkingTicket> parking = new Parking().parking(parkingLot1, parkingLot2, users);
        assertEquals(2, parking.size());
        assertEquals(101, parking.get(0).getPid());
        assertEquals(102, parking.get(1).getPid());
    }

    @Test
    public void should_park_fail_when_two_parkingLot_is_full() {
        //Given 有两个停车场，两个停车场车位都满了
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 2, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 2, carList2);
        //When 停车
        users.add(sri);
        users.add(heny);
        //Then 停车失败
        assertThrows(RuntimeException.class, () -> {
            new Parking().parking(parkingLot1, parkingLot2, users);
        });
    }

    @Test
    public void should_pick_my_car_when_parkingLot_only_park_my_car() {
        //Given 停车场只停了我的车
        ParkingLot parkingLot1 = new ParkingLot(101, 2, new ArrayList<>());
        ParkingLot parkingLot2 = new ParkingLot(102, 3, new ArrayList<>());
        users.add(sri);
        users.add(heny);
        users.add(lucy);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot1, parkingLot2, users);
        //When 用我的停车票取车
        Car car = park.pickUp(parkingLot1, parkingLot2, tickets, tickets.get(0));
        //Then 取车我的车
        assertEquals("比亚迪", car.getCarName());
        assertEquals(1006, car.getCid());
    }

    @Test
    public void should_pick_my_car_when_parkingLot_park_many_cars() {
        //Given 停车场停了多辆车，也有我的车
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 20, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 3, carList2);
        users.add(sri);
        users.add(heny);
        users.add(lucy);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot1, parkingLot2, users);
        //When 用我的停车票取车
        Car car = park.pickUp(parkingLot1, parkingLot2, tickets, tickets.get(0));
        //Then 取出我的车
        assertEquals("比亚迪", car.getCarName());
        assertEquals(1006, car.getCid());
    }

    //任务分解4
    @Test
    public void should_pick_up_failed_when_ticket_is_invalid() {
        //Given 停了我的车的停车场
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 20, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 3, carList2);
        users.add(sri);
        users.add(heny);
        users.add(lucy);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot1, parkingLot2, users);
        //When 用一张无效的票取车
        //Then 取车失败
        assertThrows(RuntimeException.class, () -> {
            park.pickUp(parkingLot1, parkingLot2, tickets, new ParkingTicket(10001, "Sri", 1006, parkingLot.getPid()));
        });
    }

    @Test
    public void should_pick_up_again_failed_when_car_had_pick_up() {
        //Given 一个停了我的车的停车场
        List<Car> carList1 = Stream.of(new Car(1009, "保时捷"), new Car(1025, "比亚迪")).collect(Collectors.toList());
        List<Car> carList2 = Stream.of(new Car(1056, "悍马"), new Car(1087, "野马")).collect(Collectors.toList());
        ParkingLot parkingLot1 = new ParkingLot(101, 20, carList1);
        ParkingLot parkingLot2 = new ParkingLot(102, 3, carList2);
        users.add(sri);
        users.add(heny);
        users.add(lucy);
        Parking park = new Parking();
        List<ParkingTicket> tickets = park.parking(parkingLot1, parkingLot2, users);
        //When 用同一张票取车两次
        Car car1 = park.pickUp(parkingLot1, parkingLot2, tickets, tickets.get(0));
        //Then 第二次取车失败
        ParkingTicket ticket = new ParkingTicket(100101, "Sri", 1006, parkingLot1.getPid());
        assertThrows(RuntimeException.class, () -> {
            Car car2 = park.pickUp(parkingLot1, parkingLot2, tickets, ticket);
        });
    }
}
