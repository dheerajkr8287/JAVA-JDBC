package org.example;

import java.sql.*;

import static java.lang.Class.forName;

public class FetchData
{
    public static void main( String[] args ) throws ClassNotFoundException {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String query="select*from employee";
        // now day java automatically loaded The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Drivers loaded Successfully........");

        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection connection=DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establishment Sucessfully");
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String name=resultSet.getString("name");
                String job_title=resultSet.getString("job_title");
                double salary=resultSet.getDouble("salary");
                System.out.println();
                System.out.println("=======================");
                System.out.println("ID is:"+id);
                System.out.println("Name is:"+name);
                System.out.println("Job_title:"+job_title);
                System.out.println("salary is:"+salary);
            }

            resultSet.close();
            statement.close();
            connection.close();
            System.out.println();
            System.out.println("Connection close suceessfully");
        } catch (SQLException e) {
            e.getMessage();
        }


    }
}
