package org.example;
/*
A PreparedStatement in JDBC is a feature used to execute precompiled SQL queries with dynamic parameters. It helps improve performance and security, particularly by preventing SQL injection attacks.
 - they are used to execute sql queries with placeholder for parameter
 - these placeholder are then filled with specfic values when the query is excuted
 - advantages:
 --->protection against sql injection
 ---->improve performance
 ----->code Readability and Maintainablity
 ---->Automatic DataType handling
 ---->portability


 Statement vs PreparedStatement in JDBC:

Statement: Executes static SQL queries. Vulnerable to SQL injection and slower due to query recompilation.
PreparedStatement: Executes precompiled, parameterized SQL queries. Safer, faster, and ideal for dynamic queries and batch processing.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class prepareStatement_Fetch {
    public static void main(String[] args) throws ClassNotFoundException {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password ="kakaji";
        String query ="select* from employee where name= ? and job_title=?";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Driver loaded sucessfully");
        }


        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established succesfully");

            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,"kaka");
            preparedStatement.setString(2,"SE");

            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String job_title=resultSet.getString("job_title");
                double salary=resultSet.getDouble("salary");

                System.out.println("ID:"+id);
                System.out.println("NAME:"+name);
                System.out.println("JOB_TITLE:"+job_title);
                System.out.println("Salary:"+salary);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            System.out.println();
            System.out.println("Connection closed Successfully  ");


        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}
