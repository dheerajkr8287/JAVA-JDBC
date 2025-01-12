package org.example;

import com.mysql.cj.protocol.Resultset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class  sparkByFetchImage {
    private  static String url="jdbc:mysql://localhost:3306/jdbc";
    private  static String username="root";
    private static  String password="kakaji";
    private static Connection connection;
    private  static PreparedStatement preparedStatement;
    private  static ResultSet resultSet;

    public static void main(String[] args) {
        try{

            connection= DriverManager.getConnection(url,username,password);
            preparedStatement=connection.prepareStatement("select image from blobDemo where id=?");
            preparedStatement.setInt(1,2);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                //stream
                InputStream is=resultSet.getBinaryStream("image");
                //location for add
                FileOutputStream fis=new FileOutputStream("D:\\JAVA-JDBC\\Learn\\src\\main\\java\\org\\example\\dheeraj.jpeg");
                byte[] buffer=new byte[1024];
                while(is.read(buffer)>0){
                    fis.write(buffer);
                }

                fis.close();
                is.close();
                resultSet.close();
                preparedStatement.close();
                connection.close();

                System.out.println("Data fetched successfully");
            }else{
                System.out.println("No image fetching....");
            }


        } catch (Exception e) {

            e.printStackTrace();        }

    }
}
