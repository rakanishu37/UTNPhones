package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.CallDao;
import com.utnphones.utnPhones.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CallMySQLDao implements CallDao {
    private Connection connection;
    private PhoneLineMySQLDao phoneLineMySQLDao;

    @Override
    public Call add(Call value) {
        return null;
    }

    @Override
    public Call update(Call value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Call value) {

    }

    @Override
    public Call getById(Integer id) {
        return null;
    }

    @Override
    public List<Call> getAll() {
        List<Call> callList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_ALL_CALLS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                callList.add(Call.builder()
                        .id(resultSet.getInt(1))
                        .phoneFrom(this.phoneLineMySQLDao.getById(resultSet.getInt(2)))
                        .phoneTo(this.phoneLineMySQLDao.getById(resultSet.getInt(3)))
                        .invoice(null)
                        .fare(resultSet.getFloat(5))
                        .duration(resultSet.getInt(6))
                        .totalPrice(resultSet.getFloat(7))
                       /* .date(resultSet.getTimestamp(8).toInstant())*/.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return callList;
    }
}
