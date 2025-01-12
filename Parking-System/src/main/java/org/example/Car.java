package org.example;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private String licensePlate;
    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}

class parkingSpot{
private  int spotNumber;
private  boolean available;
private  Car car;

    public parkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.available = true;
        this.car = null;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public Car getCar() {
        return car;
    }


    public void occupy(Car car){
        this.car=car;
        this.available=false;

    }

    public void vaccant(){
        this.car=null;
        this.available=true;
    }
}

class  parkingLot{
    private  static final String url="jdbc:mysql://localhost:3306/parkingSystem";
    private  static  final String username="root";
    private  static  final String password="kakaji";
    private  Connection connection;


    public parkingLot(){
        try {
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }


    public boolean parkCar(Car car){
        try{
            String sql="select spotNumber from ParkingSpot where available=true limit 1";

            PreparedStatement checkSpot=connection.prepareStatement(sql);
            String sql2="update parkingSpot set available=false ,licensePlate=? where spotNumber=? ";
            PreparedStatement updateSpot=connection.prepareStatement(sql2);


            ResultSet resultSet=checkSpot.executeQuery();
            if(resultSet.next())
            {
                int spotNumber=resultSet.getInt("spotNumber");


                //for updation
                updateSpot.setString(1,car.getLicensePlate());
                updateSpot.setInt(2,spotNumber);
                updateSpot.executeUpdate();
                System.out.println("Car with license plate " + car.getLicensePlate() +
                        " parked at spot number " + spotNumber);
                return true;

            }else{
                System.out.println("Sorry, parking is full.");
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return  false;
        }
    }


    public boolean removeCar(String licensePlate){
        try {
            PreparedStatement checkCar=connection.prepareStatement("select spotNumber from parkingSpot where licensePlate=?");
            PreparedStatement updateSpot=connection.prepareStatement("update parkingSpot set available=true ,licensePlate=null where spotNumber=?");
            checkCar.setString(1,licensePlate);
            ResultSet resultSet=checkCar.executeQuery();
            if(resultSet.next()){
                int spotNumber=resultSet.getInt("spotNumber");
                updateSpot.setInt(1,spotNumber);
                updateSpot.executeUpdate();
                System.out.println("Car with license plate " + licensePlate +
                        " removed from spot number " + spotNumber);
                return true;
            }else{
                System.out.println("Car with license plate " + licensePlate + " not found.");
                return false;
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

//    private List<parkingSpot> spot;
//
//    public parkingLot(int capicity) {
//        this.spot=new ArrayList<>();
//        for (int i=0;i<capicity;i++){
//            spot.add(new parkingSpot(i));
//        }
//    }
//
//    public boolean parkCar(Car car){
//        for (parkingSpot spot1:spot){
//            if(spot1.isAvailable()) {
//                spot1.occupy(car);
//                System.out.println("Car with the Number :" + car.getLicensePlate() + "  parked at a spot Number:" + spot1.getSpotNumber());
//                return true;
//            }
//        }
//        System.out.println("Sorry Parking spot is Full");
//        return  false;
//    }
//
//    public boolean removeCar(String licensePlate){
//        for(parkingSpot spot2:spot){
//            if(!spot2.isAvailable() && spot2.getCar().getLicensePlate().equalsIgnoreCase(licensePlate)){
//                spot2.vaccant();
//                System.out.println("Car with Number :"+licensePlate+" removed from parking and spot num: "+spot2.getSpotNumber());
//                return  true;
//
//            }
//        }
//        System.out.println("Car with Number "+licensePlate+"Not Found");
//        return  true;
//    }


}

class  Test{

    public static void main(String[] args) {


        parkingLot pl=new parkingLot();
        Car car1=new Car("UP149978");
        Car car2=new Car("UP166389");
        Car car3=new Car("DL446678");
        Car car4=new Car("MP233566");
        Car car5=new Car("UK149977");
        Car car6=new Car("TN229900");
        pl.parkCar(car1);
//        pl.parkCar(car2);
//        pl.parkCar(car3);
//        pl.parkCar(car4);
//        pl.parkCar(car5);

        pl.removeCar("UP149978");
//        pl.parkCar(car6);

    }
}

