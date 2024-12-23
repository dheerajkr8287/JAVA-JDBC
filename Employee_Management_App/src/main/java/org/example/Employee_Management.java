package org.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Employee_Management {

    //jdbc basic credentials
    private static  final String JDBC_URL="jdbc:mysql://localhost:3306/spark";
    private static final  String USER_NAME="root";
    private static  final String PASSWORD="kakaji";


    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static  ResultSet resultSet;
    static Scanner sc=new Scanner(System.in);


    public static void main( String[] args ) {
        while (true){
            //making database connectivity
            try {
                connection= DriverManager.getConnection(JDBC_URL,USER_NAME,PASSWORD);


                System.out.println("******-------WELCOME TO EMPLOYEE MANAGEMENT SYSTEM -------********");

                System.out.println(" WE HAVE THESE  OPTION TO WORK IN EMPLOYEE MANAGEMENT SYSTEM ");
                System.out.println("enter 1 for read data \nenter 2 for insert data\nenter 3 for create Table\nenter 4 delete data\nEnter 5 Update data\nEnter 6 Batch Insert\nEnter 7 Exit\n");
                int choice=sc.nextInt();
                switch(choice) {
                    case 1:
                        //fetch data into table
                        readDataIntoDB();
                        break;
                    case 2:
                        //insert data into table
                        insertDataIntoDB();
                        break;
                    case 3:
                        //create table
                        createEmployeeTable();
                        break;
                    case 4:
                        deleteDataToDB();
                        break;
                    case 5:
                        updateDataToDB();
                        break;

                    case 6:
                        batchInsertDataToDB();

                        break;

                    case 7:
                        System.out.println("Exiting the system. Goodbye!");
                        closeResources();
                        System.exit(0);


                    default:
                        System.out.println("enter the valid input");
                        break;
                }





            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Clear the invalid input
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }



        }
    }


    private static void batchInsertDataToDB() {
        try {
            System.out.println("Enter the number of employees you want to add in a batch:");
            int count = sc.nextInt();

            String batchQuery = "INSERT INTO Employee (name, age, email) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(batchQuery);

            for (int i = 0; i < count; i++) {
                System.out.println("Enter details for Employee " + (i + 1) + ":");
                System.out.print("Enter name: ");
                String name = sc.next();
                System.out.print("Enter age: ");
                int age = sc.nextInt();
                System.out.print("Enter email: ");
                String email = sc.next();

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, email);
                preparedStatement.addBatch();
            }

            int[] batchResult = preparedStatement.executeBatch();
            System.out.println(batchResult.length + " records inserted successfully via batch process.");

        }

        catch (SQLException e) {
            System.out.println("Error during batch insert: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private static void updateDataToDB()  {
        try {

            System.out.println("Enter the Employee ID for any Upadation ");
            int id=sc.nextInt();

            //display the update options
            System.out.println("What would you like to update ");
            System.out.println("1. Name");
            System.out.println("2. age");
            System.out.println("3. email");
            String updateSqlQuery=null;
            int updateChoice=sc.nextInt();
            switch (updateChoice){
                case 1:
                    System.out.println("Enter the new name:");
                    String newName=sc.next();
                    updateSqlQuery="update employee set name= ? where id=? ";
                    preparedStatement=connection.prepareStatement(updateSqlQuery);
                    preparedStatement.setString(1,newName);
                    preparedStatement.setInt(2,id);
                    break;
                case 2:
                    System.out.println("Enter the new age:");
                    int newAge=sc.nextInt();
                    updateSqlQuery="update employee set age=? where id=?";
                    preparedStatement=connection.prepareStatement(updateSqlQuery);
                    preparedStatement.setInt(1,newAge);
                    preparedStatement.setInt(2,id);
                    break;
                case 3:
                    System.out.println("Enter the new email:");
                    String newEmail=sc.next();
                    updateSqlQuery="update employee set email=? where id=?";
                    preparedStatement=connection.prepareStatement(updateSqlQuery);
                    preparedStatement.setString(1,newEmail);
                    preparedStatement.setInt(2,id);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    return;
            }

            //execute the updated query
            int rowAffected=preparedStatement.executeUpdate();
            if(rowAffected>0){

                System.out.println("Employee record updated successfully.");
            } else {
                System.out.println("No record found with the given ID.");
            }


        }

        catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        } finally {
            closeResources();
        }



    }

    private static void deleteDataToDB()  {
        try {


            System.out.println("enter the id for delete record:");
            int id = sc.nextInt();
            String deleteData = "delete from Employee where id=" + id;
            preparedStatement = connection.prepareStatement(deleteData);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("data deleted :" + id);
            } else {
                System.out.println("data deletion failied");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        } finally {
            closeResources();
        }

    }

    private static void readDataIntoDB()   {
        try {
            String fetchData = "select*from Employee";
            preparedStatement = connection.prepareStatement(fetchData); // Initialize preparedStatement
            resultSet = preparedStatement.executeQuery(fetchData);
            System.out.println("*************Employee Details *************8");
            while (resultSet.next()) {
                System.out.println("Employee Id:" + resultSet.getInt("id"));
                System.out.println("Employee name:" + resultSet.getString("name"));
                System.out.println("Employee age:" + resultSet.getInt("age"));
                System.out.println("Employee email:" + resultSet.getString("email"));
                System.out.println("************----------------------********************");

            }
        } catch (SQLException e) {
            System.out.println("Error reading data: " + e.getMessage());
        } finally {
            closeResources();
        }


        System.out.println("Data read sucessfully");
    }


    private static void insertDataIntoDB() {

        try{
            System.out.println("Enter the name:");
            String name=sc.next();
            System.out.println("Enter the age:");
            int age=sc.nextInt();
            System.out.println("Enter the email:");
            String  email=sc.next();
            String insertSql="insert into Employee(name,age,email) values (?,?,?)";
            preparedStatement=connection.prepareStatement(insertSql);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();
            System.out.println("data inserted in database sucessfully");
        }
        catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        } finally {
            closeResources();
        }


    }


    private static void createEmployeeTable() {
        try {
            String createTable = "create table IF NOT EXISTS Employee( id int auto_increment primary key," +
                    "name varchar(100),"
                    + "age int ,"
                    + "email varchar(100)"
                    + ")";

            preparedStatement = connection.prepareStatement(createTable);
            preparedStatement.execute();
            System.out.println("Table create Sucessfully...");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        } finally {
            closeResources();
        }
    }
    private static void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}

