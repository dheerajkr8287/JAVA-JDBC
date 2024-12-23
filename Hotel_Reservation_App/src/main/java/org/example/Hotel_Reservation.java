package org.example;

import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.sasl.ScramSha1SaslClient;

import java.sql.*;
import java.util.Scanner;

public class Hotel_Reservation {
    private static final  String url="jdbc:mysql://localhost:3306/hotel_db";
    private  static  final String username="root";
    private static  final String password="kakaji";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }


        try{
            Connection connection=DriverManager.getConnection(url,username,password);
            System.out.println("Connection setup Successfully");
            Statement statement=connection.createStatement();





            while (true){
                System.out.println();
                System.out.println("============HOTEL-MANAGEMENT-SYSTEM==========");
                Scanner sc=new Scanner(System.in);
                System.out.println("1: Reservation a room");
                System.out.println("2: View  Reservations");
                System.out.println("3: get Room Number");
                System.out.println("4: Update reservations");
                System.out.println("5: Delete Reservation ");
                System.out.println("0: Exit");
                System.out.println("Choose an Options:");
                int choice=sc.nextInt();

                switch (choice){
                    case 1:
                        reserveRoom(connection, sc,statement);
                        break;
                    case 2:
                        viewReserveRooms(connection,sc,statement);
                        break;
                    case 3:
                        getRoomNumber(connection,sc,statement);
                        break;
                    case 4:
                        updateReservation(connection,sc,statement);
                        break;

                    case 5:
                        deleteReservation(connection,sc,statement);
                        break;

                    case 0:
                        exits();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice .put correctly");


                }

            }

        }
        catch (SQLException e){
            System.out.println("Database error: " + e.getMessage());


        }catch (Exception e){
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }




    }

    private static void exits() throws InterruptedException {
        System.out.print("Existing from system ");
        int i=6;
        while (i!=0){
            System.out.print(".");
            Thread.sleep(300);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

    private static void deleteReservation(Connection connection, Scanner sc, Statement statement) {
        try{
            System.out.println("Enter the reservation id to delete:");
            int reservation_id=sc.nextInt();

            if (!reservationExists( connection, reservation_id,   statement)){
                System.out.println("Reservation not found for the given ID");
                return;
            }

            String sql="delete from reservation where reservation_id="+reservation_id;
            int affectedRow=statement.executeUpdate(sql);
            if (affectedRow > 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Reservation deletion failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }


    }

    private static void updateReservation(Connection connection, Scanner sc, Statement statement) {
        try{
            System.out.println("Enter the reservation  ID for any Upadation ");
            int reservation_id=sc.nextInt();
            sc.nextLine();//consume the newline character

            if(!reservationExists(connection,reservation_id,statement)){
                System.out.println("Reservation not found for the given ID");
                return;
            }

            //display the update options
            System.out.println("What would you like to update ");
            System.out.println("1. guest_name");
            System.out.println("2. room_number");
            System.out.println("3. contact_number");
            int updateChoice=sc.nextInt();

            sc.nextLine(); // Consume the newline character

            String sql;
            int affectedRow;
            switch (updateChoice){
                case 1:
                    System.out.println("Enter the new guest_name");
                    String newGuestName=sc.next();
                    sql="update reservation set guest_name='"+newGuestName+"' where reservation_id="+reservation_id;
                    break;
                case 2 :
                    System.out.println("Enter the new room_number:");
                    int newRoomNumber=sc.nextInt();
                    if (newRoomNumber <= 0) {
                        System.out.println("Invalid room number.");
                        return;
                    }
                    sql="update reservation set room_number="+newRoomNumber+" where reservation_id="+reservation_id;
                    break;
                case 3:
                    System.out.println("Enter the new contact_number :");
                    String newContactNumber=sc.next();
                    sql="update reservation set contact_number='"+newContactNumber+"' where reservation_id="+reservation_id;

                    break;
                default:
                    System.out.println("invalid choice please select 1,2,3.");
                    return;

            }
            affectedRow=statement.executeUpdate(sql);
            if (affectedRow>0){
                System.out.println("Reservation updated successfully!");

            }else{
                System.out.println("Failed to update the reservation.");
            }

        }catch (SQLException e){
            System.out.println("ERROR Updating data:"+e.getMessage());
        }
    }

    private static boolean reservationExists(Connection connection, int reservation_id, Statement statement) {
        try{
            String sql="select reservation_id from reservation where reservation_id=" + reservation_id;            ResultSet resultSet=statement.executeQuery(sql);
            return resultSet.next();// if there a result ,the reservation exists
        }catch (SQLException e){
            System.out.println("Error checking reservation existence: " + e.getMessage());
            return false;
        }

    }

    private static void getRoomNumber(Connection connection, Scanner sc, Statement statement) {


        try{
            System.out.println("enter the reservation id:");
            int reservationId= sc.nextInt();
            sc.nextLine(); // Consume the newline character

            System.out.println("enter guest name:");
            String guestName = sc.nextLine();

            String sql = "SELECT room_number FROM reservation WHERE reservation_id = " + reservationId +
                    " AND guest_name = '" + guestName + "'";
            ResultSet resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                int roomNumber=resultSet.getInt("room_number");
                System.out.println("Room number for Reservation ID: "+reservationId+"and Guest:"+guestName+"is:-->"+roomNumber);
            }else {
                System.out.println("Reservation not found for your given ID and guest name:");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReserveRooms(Connection connection, Scanner sc, Statement statement) {
        try {
            String sql="select reservation_id,guest_name,room_number,contact_number from reservation";
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                int reservation_id=resultSet.getInt("reservation_id");
                String guest_name=resultSet.getString("guest_name");
                int room_number=resultSet.getInt("room_number");
                String contact_number=resultSet.getString("contact_number");

                System.out.println("Reservation_id: "+reservation_id+"  Guest_name: "+guest_name+"   ReservationRoomNumber: "+room_number+"   GuestContactNumber: "+contact_number);
                System.out.println("----------------------------");

            }
        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection,Scanner sc,Statement statement) {
        System.out.println("Enter the guest name:");
        String guestName=sc.next();
        System.out.println("Enter the room number:");
        int roomNumber=sc.nextInt();
        System.out.println("Enter the phone number:");
        String contactNumber=sc.next();
        if (guestName.isEmpty() || contactNumber.isEmpty()) {
            System.out.println("Guest name and contact number cannot be empty.");
            return;
        }

        String sql="insert into reservation (guest_name,room_number,contact_number) values ('"+guestName+"','"+roomNumber+"','"+contactNumber+"')";
        try{
            int rowAffected=statement.executeUpdate(sql);
            if(rowAffected>0){
                System.out.println("Reservation Successful");
            }else{
                System.out.println("Reservation not Successful");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}