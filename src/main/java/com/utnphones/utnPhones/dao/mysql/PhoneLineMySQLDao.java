package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.PhoneLineDao;
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
public class PhoneLineMySQLDao implements PhoneLineDao {
    private Connection connection;



    @Override
    public PhoneLine add(PhoneLine value) {
        return null;
    }

    @Override
    public PhoneLine update(PhoneLine value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(PhoneLine value) {

    }

    public List<PhoneLine> getByPerson(Person person){
        List<PhoneLine> phoneLineList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_PHONE_LINE_BY_PERSON);
            preparedStatement.setInt(1, person.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phoneLineList.add(new PhoneLine(resultSet.getInt(1), new LineType(1, "mobile"), person,
                        resultSet.getString(4), LineStatus.valueOf(resultSet.getString(5))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneLineList;
    }

    @Override
    public PhoneLine getById(Integer id){
        PhoneLine phoneLine = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_PHONE_LINE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                phoneLine = new PhoneLine(resultSet.getInt(1), new LineType(1, "mobile"), null,
                        resultSet.getString(4), LineStatus.valueOf(resultSet.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneLine;
    }

    @Override
    public List<PhoneLine> getAll() {

        List<PhoneLine> phoneLineList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_ALL_PHONE_LINES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phoneLineList.add(new PhoneLine(resultSet.getInt(1), new LineType(1, "mobile"), null,
                        resultSet.getString(4), LineStatus.valueOf(resultSet.getString(5))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneLineList;
    }
}
