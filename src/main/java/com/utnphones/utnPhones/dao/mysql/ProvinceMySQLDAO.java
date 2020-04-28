package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.domain.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceMySQLDAO {
    private Connection connection;

    public ProvinceMySQLDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Province> getAll(){
        List<Province> provinceList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from provinces");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                provinceList.add(new Province(resultSet.getInt(1), resultSet.getString(2)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinceList;
    }
}
