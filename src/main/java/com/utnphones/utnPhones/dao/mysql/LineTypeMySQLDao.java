package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.LineTypeDao;
import com.utnphones.utnPhones.domain.LineType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@AllArgsConstructor
public class LineTypeMySQLDao implements LineTypeDao {
    private Connection connection;

    @Override
    public LineType add(LineType value) {
        return null;
    }

    @Override
    public LineType update(LineType value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(LineType value) {

    }

    @Override
    public LineType getById(Integer id) {
        LineType lineType = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_ALL_LINE_TYPES);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                lineType = new LineType(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineType;
    }

    @Override
    public List<LineType> getAll() {
        return null;
    }
}
