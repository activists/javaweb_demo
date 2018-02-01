package com.fengfw.land.service;

import java.sql.*;

public class UserJDBC {

    private String url="jdbc:mysql://localhost:3306/test";
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;

    public UserJDBC(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类,加载驱动失败！");
            e.printStackTrace();
        }
    }

//    public static void main(String args[]){
//        UserJDBC userJDBC=new UserJDBC();
//        boolean verfy=userJDBC.getVerfy("ffw1","123456");
//        int money=userJDBC.getMoney("ffw1","123456");
//        System.out.println("money:"+money);
//
//    }

    public boolean getVerfy(String username,String password){
        try{
            connection=DriverManager.getConnection(url);
            preparedStatement=connection.prepareStatement("select password from test.user where username=?");
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getString(1).equals(password)){
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean getModify(String username,String password,String newPassword){
        if(getVerfy(username,password)){
            try{
                connection=DriverManager.getConnection(url);
                preparedStatement=connection.prepareStatement("update test.user set password=? where username=?");
                preparedStatement.setString(1,newPassword);
                preparedStatement.setString(2,username);
                boolean result=preparedStatement.execute();
                if(!result){
                    return true;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                if(preparedStatement!=null){
                    try{
                        preparedStatement.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
                if(connection!=null){
                    try{
                        connection.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    public int getMoney(String username,String password) {
        int money = -1;
        if (getVerfy(username, password)) {
            try {
                connection = DriverManager.getConnection(url);
                preparedStatement = connection.prepareStatement("select money from test.user where username=?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    money = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return money;
    }

    public boolean transferMoney(String username,String password,int money) {
        if (getVerfy(username, password)) {
            try {
                connection = DriverManager.getConnection(url);
                preparedStatement = connection.prepareStatement("update user set money=money-? where username=?");
                preparedStatement.setInt(1, money);
                preparedStatement.setString(2,username);
                boolean result=preparedStatement.execute();
                if(!result){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return false;
    }
}
