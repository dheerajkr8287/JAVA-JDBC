package org.example;

import java.lang.invoke.SwitchPoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static java.lang.Class.forName;

public class JDBCApplication {
    public static void main(String[] args) {
        //load and register the driver

        String url="jdbc:mysql://localhost:3306/spark";
        String username="root";
        String password="kakaji";

        try{
            //load and register -- but now days this is not manadotory
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection(url,username,password);
            Statement statement= connection.createStatement();
            //ResultSet resultSet= statement.executeQuery("select*from students");

            // 1: USER INPUT --> fetch data
            //2:user input: --> insert data

            Scanner sc=new Scanner(System.in);
            System.out.println("enter 1 for fetch data \nenter 2 for insert data\nenter 3 for batch  update\nenter 4 delete data");
            int choice=sc.nextInt();
            switch(choice) {     //fetching the data from the database
                case 1:
                    ResultSet fetchData = statement.executeQuery("select*from students");
                    Operations.fetchData(fetchData);
                    break;
                // insert the data into the database
                case 2:
                    ResultSet MaxResultSet = statement.executeQuery("select MAX(st_id) as max_st_id from students");
                    // this logic is used because of there is no primary key is used in st_id so this logic is used for autoincrement

                    int max_id = 0;
                    while (MaxResultSet.next()) {
                        max_id = MaxResultSet.getInt("max_st_id");
                        System.out.println("Max Student id:" + max_id);
                    }
                    max_id++;

                    System.out.println("Enter the name:");
                    String name = sc.next();
                    System.out.println("Enter the email");
                    String email = sc.next();
                    System.out.println("enter the phoneNo");
                    String phoneNo = sc.next();

                    int rowCount = statement.executeUpdate("insert into students values(" + max_id + ",'" + name + "','" + email + "','" + phoneNo + "')");
                    if (rowCount > 0) {
                        System.out.println("Data inserted");
                    } else {
                        System.out.println("data inserted fail");
                        String[] bulkQueries = new String[10];
                    }
                    break;
                case 3:   // batch data insert
                    //Batch processing:
                   /*
                   Batch processing in JDBC refers to executing multiple SQL statements as a group in a single transaction. Instead of sending each SQL query separately to the database, you group several queries into a "batch" and execute them together. This reduces
                   the number of network trips between the application and the database, improving performance and efficiency

                   Why Use Batch Processing?
->Performance Optimization: By reducing the number of interactions with the database server, batch processing minimizes network overhead and latency.
->Atomicity: Ensures all operations in the batch succeed together or fail together, maintaining database integrity.
->Convenience: Simplifies the execution of repetitive tasks like inserting or updating multiple rows.
->Error Handling: Provides mechanisms to manage and debug errors in the batch.

                    */

                    System.out.println("Enter the no of students details you want to add  with the using  batch");
                    int count = sc.nextInt();

                    //find the max ID for each new entry

                    ResultSet batchMaxResultSet = statement.executeQuery("select MAX(st_id)  as max_st_id from students");
                    int Batch_Max_Id = 0;
                    if(batchMaxResultSet.next()) {
                        Batch_Max_Id = batchMaxResultSet.getInt("max_st_id");

                    }

                    //prepare the batch
                    // Loop to collect student details and add them to the batch

                    String batchName = "";
                    String batchEmail = "";
                    String batchPhoneNo = "";
                    for (int i = 0; i < count; i++) {
                        System.out.println("Enter the details for student " + (i + 1) + ":");
                        System.out.println("Enter name:");
                        batchName = sc.next();
                        System.out.println("Enter email:");
                        batchEmail = sc.next();
                        System.out.println("Enter the phoneNo:");
                        batchPhoneNo = sc.next();

                        // Increment the ID manually for each new student

                        Batch_Max_Id++;
                        //add query to the batch
                        String batchQuery = "insert into students values(" + Batch_Max_Id + ",'" + batchName + "','" + batchEmail + "','" + batchPhoneNo + "')";
                        statement.addBatch(batchQuery);
                    }






                    //execute the batch
                    try {
                        int[] batchResult = statement.executeBatch();
                        System.out.println(batchResult.length + " records inserted successfully via batch process.");
                    } catch (Exception e) {
                        System.out.println("Error occurred during batch insertion: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("enter the id for delete record:" );
                    int id=sc.nextInt();
                    int row=statement.executeUpdate("delete from students where st_id="+id);
                    if(row>0){
                        System.out.println("data deleted :"+id);
                    }else {
                        System.out.println("data deletion failied");
                    }
                    break;

                default:
                    System.out.println("enter the valid input");
                    break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
