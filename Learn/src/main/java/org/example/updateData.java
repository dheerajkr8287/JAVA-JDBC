package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class updateData {
    public static void main(String[] args) {


        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String query="UPDATE employee " +
                "SET job_title='clerk', salary=40000.0 " +
                "WHERE id=3";

        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establishment Sucessfully....");
            Statement statement= connection.createStatement();
            int rows=statement.executeUpdate(query);
            if(rows>0){
                System.out.println("update data sucessful:"+rows);

            }
            else {
                System.out.println("update failed");
            }
            statement.close();
            connection.close();
            System.out.println();
            System.out.println("Connection closed sucessfully");

        }
        catch (Exception e){
            e.getMessage();

        }
    }
}
