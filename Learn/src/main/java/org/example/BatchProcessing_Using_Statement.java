package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessing_Using_Statement {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        try {
           Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establishment sucessful");
            connection.setAutoCommit(false);
            Statement statement=connection.createStatement();
            statement.addBatch("insert into employee(id,name,job_title,salary) values (6,'knwe','DB',4442.0)");
            statement.addBatch("insert into employee(id,name,job_title,salary) values (7,'alex','cloud',5542.0)");
            statement.addBatch("insert into employee(id,name,job_title,salary) values (8,'lpes','Devops',9942.0)");
            statement.addBatch("insert into employee(id,name,job_title,salary) values (9,'naval','helper',4400.0)");
            int[] batchResult=statement.executeBatch();
            connection.commit();
            System.out.println("Batch Executed successfully");






        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
