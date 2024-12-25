package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class prepareStatament_Insert {
    public static void main(String[] args) throws ClassNotFoundException {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password ="kakaji";
        String query ="insert into employee(id,name,job_title,salary) values (?, ? ,? ,?)";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Driver loaded sucessfully");
        }


        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established succesfully");
            Scanner sc=new Scanner(System.in);
            System.out.println("enter id:");
            int id=sc.nextInt();
            sc.nextLine();
            System.out.println("enter name:");
            String name=sc.nextLine();
            System.out.println("enter job_title:");
            String job_title=sc.nextLine();
            System.out.println("enter salary:");
            Double salary=sc.nextDouble();



            PreparedStatement preparedStatement=connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
           preparedStatement.setString(2,name);
           preparedStatement.setString(3,job_title);
           preparedStatement.setDouble(4,salary);
           int rowAffected=preparedStatement.executeUpdate();
           if(rowAffected>0){
               System.out.println("Data inserted successfully");
           }else{
               System.out.println("data not be inserted");
           }


            preparedStatement.close();
            connection.close();
            System.out.println();
            System.out.println("Connection closed Successfully  ");


        }catch (Exception e){
//            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }
}
