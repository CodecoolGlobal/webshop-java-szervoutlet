package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.DatabaseDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC extends DatabaseDao implements UserDao {


    private static UserDaoJDBC mInstance;

    private UserDaoJDBC() {
    }

    public static UserDaoJDBC getInstance() {
        if (mInstance == null) {
            mInstance = new UserDaoJDBC();
        }
        return mInstance;
    }

    @Override
    public void add(String name, String email, String password, int phoneNumber, String shippingAddress, String billingAddress) {
        String query = String.format("INSERT INTO users(email, password, phone_number, billing_address, shipping_address, name) VALUES('%s', '%s', '%s', '%s', '%s', '%s');",email,password,phoneNumber,shippingAddress, billingAddress, name);
        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String email, String password)
    {
        List<User> users = getAll();
        users.removeIf(user -> !user.getEmail().equals(email) || !user.getPassword().equals(password));
        if(users.size() > 0){
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void remove(final int id) {

    }

    @Override
    public List<User> getAll() {
        String query = String.format("SELECT * FROM users;");

        List<User> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                User actualUser = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("phone_number"),
                        resultSet.getString("billing_address"),
                        resultSet.getString("shipping_address"));
                resultList.add(actualUser);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public User getByName(final String userName) {
        return null;
    }
}
