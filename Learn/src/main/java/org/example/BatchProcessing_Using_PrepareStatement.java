package org.example;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessing_Using_PrepareStatement {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establishment sucessful");
            connection.setAutoCommit(false);
            String query="Insert into employee(id ,name,job_title,salary) values (?, ? ,?, ?) ";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            Scanner sc=new Scanner(System.in);
            while (true){
                System.out.print("id:");
                int id=sc.nextInt();
                System.out.print("name:");
                String name=sc.next();
                System.out.print("job_title:");
                String job_title=sc.next();
                System.out.print("salary:");
                double salary=sc.nextDouble();

                preparedStatement.setInt(1,id);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,job_title);
                preparedStatement.setDouble(4,salary);

                preparedStatement.addBatch();
                System.out.println("add more value Y/N:");
                String decision = sc.next();
                if (decision.equalsIgnoreCase("N")) {
                    break;
                }


            }
            int[] batchResult=preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Batch executed successfully. Rows inserted: " + batchResult.length);






        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
