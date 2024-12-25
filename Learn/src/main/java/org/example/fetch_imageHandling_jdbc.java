package org.example;

import java.io.*;
import java.sql.*;

public class fetch_imageHandling_jdbc {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String folder_path="D:\\wallpaper\\";
        String query="select image_data from image_table where image_id= (?) ";

        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully");

            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,1);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                byte[] image_data=resultSet.getBytes("image_data");
                String image_path=folder_path+"extractImage.jpg";
                OutputStream outputStream=new FileOutputStream(image_path);
                outputStream.write(image_data);
                System.out.println("Image extracted sucessfully");
            }else {
                System.out.println("Image not extracted sucessfully");
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
