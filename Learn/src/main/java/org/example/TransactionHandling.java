package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/jdbc";
        String username="root";
        String password="kakaji";
        String withDrawQuery="update accounts set balance=balance-? where account_number=?";
        String depositQuery="update accounts set balance=balance+? where account_number =?";



        try{

            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully");

            connection.setAutoCommit(false);

            try{
                PreparedStatement withdrawStatament=connection.prepareStatement(withDrawQuery);
                PreparedStatement deposistStatement=connection.prepareStatement(depositQuery);
                withdrawStatament.setDouble(1,500.00);
                withdrawStatament.setString(2,"account123");
                deposistStatement.setDouble(1,500.00);
                deposistStatement.setString(2,"account456 ");
                int rowAffectedWithdraw=withdrawStatament.executeUpdate();
                int rowAffectedDeposit=deposistStatement.executeUpdate();
                if(rowAffectedWithdraw>0 && rowAffectedDeposit>0){
                    connection.commit();
                    System.out.println("Transaction successful!");
                }else{
                    connection.rollback();
                    System.out.println("Transactions failed!");
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
