package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteData {
    public static void main(String[] args) {


        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String query="delete from employee where id=4;";

        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establishment Sucessfully....");
            Statement statement= connection.createStatement();
            int rows=statement.executeUpdate(query);
            if(rows>0){
                System.out.println("Delete data sucessful:"+rows);

            }
            else {
                System.out.println("Delete failed");
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
