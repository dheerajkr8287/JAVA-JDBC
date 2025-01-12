package org.example;

import com.mysql.cj.protocol.Resultset;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class sparkbyImageInsert {
    private  static String url="jdbc:mysql://localhost:3306/jdbc";
    private  static String username="root";
    private static  String password="kakaji";
    private static Connection connection;
    private  static PreparedStatement preparedStatement;
    private  static Resultset resultset;

    public static void main(String[] args) {
        try{

            connection= DriverManager.getConnection(url,username,password);
            File f=new File("D:\\JAVA-JDBC\\Learn\\src\\main\\java\\org\\example\\image.jpeg");
            FileInputStream fis=new FileInputStream(f);
            preparedStatement=connection.prepareStatement("insert into blobDemo values (?,?)");

            preparedStatement.setInt(1,2);
            preparedStatement.setBinaryStream(2,fis,(int)f.length());
            preparedStatement.executeUpdate();
            System.out.println("Data Inserted ....");
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {

e.printStackTrace();        }

    }
}
