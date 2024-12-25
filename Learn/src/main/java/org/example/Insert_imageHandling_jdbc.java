package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_imageHandling_jdbc {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String image_path="D:\\wallpaper\\Darker.png";
        String query="insert into image_table(image_data) values (?)";

        try {
            Connection connection=DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully");

            FileInputStream fileInputStream=new FileInputStream(image_path);
            byte[] imageData=new byte[fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setBytes(1,imageData);
            int rowAffected=preparedStatement.executeUpdate();
            if(rowAffected>0){
                System.out.println("Image inserted Sucessfully");
            }else{
                System.out.println("Image not Inserted");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
