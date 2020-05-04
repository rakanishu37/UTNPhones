package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.ProvinceDao;
import com.utnphones.utnPhones.domain.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceMySQLDao implements ProvinceDao {
    private Connection connection;

    public ProvinceMySQLDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Province add(Province value) {
        return null;
    }

    @Override
    public Province update(Province value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Province value) {

    }

    @Override
    public Province getById(Integer id) {
        Province province = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from provinces where" +
                    " provinces.id_province = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                province = new Province(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return province;
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
