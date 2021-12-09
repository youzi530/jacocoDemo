package com.tw.parkinglot;

import com.tw.pojo.Car;
import com.tw.pojo.ParkingLot;
import com.tw.pojo.ParkingTicket;
import com.tw.pojo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parking {

    /**
     * 一个停车场的停车方法
     *
     * @param parkingLot 停车场
     * @param users      待停车的用户
     * @return 票
     */
    public List<ParkingTicket> parking(ParkingLot parkingLot, List<User> users) {
        int usersNum = users.size(); //当前等待停车的用户数量
        int prakNum = parkingLot.getpNumber() - parkingLot.getCarList().size(); //当前停车场剩余车位数 = 停车场的车位数-停车场已有车数量
        ArrayList<ParkingTicket> ticketList = new ArrayList<>();
        if (parkingLot.getCarList().size() >= parkingLot.getpNumber()) {
            throw new RuntimeException("停车场已满，无法停车");
        } else {
            for (int i = 0; i < usersNum; i++) {
                if (prakNum > 0) {
                    ParkingTicket parkingTicket = new ParkingTicket(100 + users.get(i).getUid(), users.get(i).getName(), users.get(i).getCid(), parkingLot.getPid());
                    ticketList.add(parkingTicket);
                    prakNum--;

                    //停车后就需要往停车场新增一辆车的信息，以模拟取车的时候业务
                    List<Car> carList = parkingLot.getCarList();
                    carList.add(new Car(users.get(i).getCid(), users.get(i).getcName()));
                    parkingLot.setCarList(carList);
                }
            }
            return ticketList;
        }
    }

    /**
     * 两个停车场的停车方法
     *
     * @param parkingLot1 停车场1
     * @param parkingLot2 停车场2
     * @param users       待停车的用户
     * @return 票
     */
    public List<ParkingTicket> parking(ParkingLot parkingLot1, ParkingLot parkingLot2, List<User> users) {
        int usersNum = users.size(); //当前等待停车的用户数量
        int prakNum1 = parkingLot1.getpNumber() - parkingLot1.getCarList().size(); //当前停车场1剩余车位数 = 停车场的车位数-停车场已有车数量
        int prakNum2 = parkingLot2.getpNumber() - parkingLot2.getCarList().size(); //当前停车场2剩余车位数 = 停车场的车位数-停车场已有车数量

        ArrayList<ParkingTicket> ticketList = new ArrayList<>();

        if (prakNum1 > prakNum2 && prakNum1 >= usersNum) { //第一个空车位比第二个多
            return parking(parkingLot1, users);
        } else if (prakNum2 > prakNum1 && prakNum2 >= usersNum) { //第二个空车位比第一个多
            return parking(parkingLot2, users);
        } else { //有两个停车场，两个停车位一样多且足够
            boolean flag;
            for (int i = 0; i < usersNum; i++) {
                flag = true; //增加标识位，去解决bug
                if (parkingLot2.getCarList().size() >= parkingLot2.getpNumber()) {
                    throw new RuntimeException("停车场已满，无法停车");
                }
                if (prakNum1 > 0) { //当第一个停车场的剩余车位数大于零，就停到第一个停车场中
                    ParkingTicket parkingTicket = new ParkingTicket(100 + users.get(i).getUid(), users.get(i).getName(), users.get(i).getCid(), parkingLot1.getPid());
                    ticketList.add(parkingTicket);
                    prakNum1--;

                    //停车后就需要往停车场新增一辆车的信息，以模拟取车的时候业务
                    List<Car> carList = parkingLot1.getCarList();
                    carList.add(new Car(users.get(i).getCid(), users.get(i).getcName()));
                    parkingLot1.setCarList(carList);
                    if (prakNum1 == 0) {
                        flag = false;
                    }
                }
                if (flag == true && prakNum1 == 0 && prakNum2 > 0) { //当第一个停车场的剩余车位数为于零，就停到第二个停车场中
                    ParkingTicket parkingTicket = new ParkingTicket(100 + users.get(i).getUid(), users.get(i).getName(), users.get(i).getCid(), parkingLot2.getPid());
                    ticketList.add(parkingTicket);
                    prakNum2--;

                    //停车后就需要往停车场新增一辆车的信息，以模拟取车的时候业务
                    List<Car> carList = parkingLot2.getCarList();
                    carList.add(new Car(users.get(i).getCid(), users.get(i).getcName()));
                    parkingLot2.setCarList(carList);
                }
            }
            return ticketList;
        }
    }

    //错误逻辑：不能用用户保存的cid去和车的cid进行比较，而应该是车票和票池去比较
    public Car pickUp(ParkingLot parkingLot, List<ParkingTicket> originTickets, ParkingTicket ticket, User user) {
        List<Car> carList = parkingLot.getCarList();
        Iterator<Car> iterator = carList.iterator();
        if (originTickets.isEmpty() || !originTickets.contains(ticket)) {
            throw new RuntimeException("车票无效，取车失败！");
        }
        while (iterator.hasNext()) {
            Car car = iterator.next();
            if (car.getCid().equals(user.getCid())) {
                carList.remove(car);
                break;
            }
        }
        originTickets.remove(ticket);
        return new Car(user.getCid(), user.getcName());
    }

    /**
     * 一个停车场的取车方法
     *
     * @param parkingLot    停车场
     * @param originTickets 当前停车场存在的车的原始票池
     * @param ticket        取车需要的票
     * @return 返回你的车
     * 重构逻辑：
     * 1、根据票池来去和票做判断
     * 2、换一个数据结构，使用map<>,key为票，然后value是车，这样就能克服
     * 3、进行方法重构，省去用户的加入
     */
    public Car pickUp(ParkingLot parkingLot, List<ParkingTicket> originTickets, ParkingTicket ticket) {
        List<Car> carList = parkingLot.getCarList();
        if (originTickets.isEmpty() || !originTickets.contains(ticket)) {
            throw new RuntimeException("车票无效，取车失败！");
        }
        // car id = ticket id
        Car car1 = carList.stream().filter(car -> car.getCid().equals(ticket.getCid())).findFirst().get();
        ParkingTicket parkingTicket = originTickets.stream().filter(tickets -> tickets.getCid() == ticket.getCid()).findFirst().get();
        originTickets.remove(ticket);
        return car1;
    }

    /**
     * 两个停车场的取车方法
     *
     * @param parkingLot1   停车场1
     * @param parkingLot2   停车场2
     * @param originTickets 当前停车场存在的车的原始票池
     * @param ticket        取车需要的票
     * @return 返回你的车
     */
    public Car pickUp(ParkingLot parkingLot1, ParkingLot parkingLot2, List<ParkingTicket> originTickets, ParkingTicket ticket) {
        List<Car> carList1 = parkingLot1.getCarList();
        List<Car> carList2 = parkingLot2.getCarList();

        if (originTickets.isEmpty() || !originTickets.contains(ticket)) {
            throw new RuntimeException("车票无效，取车失败！");
        }
        // car id = ticket id ??
        boolean present1 = carList1.stream().filter(car -> car.getCid().equals(ticket.getCid())).findAny().isPresent();
        boolean present2 = carList2.stream().filter(car -> car.getCid().equals(ticket.getCid())).findAny().isPresent();

        ParkingTicket parkingTicket = originTickets.stream().filter(tickets -> tickets.getCid() == ticket.getCid()).findFirst().get();
        originTickets.remove(parkingTicket);
        return present1 == true ? carList1.stream().filter(car -> car.getCid().equals(ticket.getCid())).findFirst().get()
                : carList2.stream().filter(car -> car.getCid().equals(ticket.getCid())).findFirst().get();
    }
}
