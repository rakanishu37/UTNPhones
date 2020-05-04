package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.ClientDao;
import com.utnphones.utnPhones.domain.Client;
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
public class ClientMySQLDao implements ClientDao {
    private Connection connection;
    private PhoneLineMySQLDao phoneLineMySQLDao;
    private CityMySQLDao cityMySQLDao;


    @Override
    public Client add(Client value) {
        return null;
    }

    @Override
    public Client update(Client value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Client value) {

    }

    @Override
    public Client getById(Integer id) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_ALL_CLIENTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client cLient = Client.builder().id(resultSet.getInt(1))
                        .city(this.cityMySQLDao.getById(resultSet.getInt(2)))
                        .firstname(resultSet.getString(3))
                        .surname(resultSet.getString(4))
                        .DNI(resultSet.getString(5))
                        .username(resultSet.getString(6))
                        .password(resultSet.getString(7))
                        .phoneLines(new ArrayList<>()).build();
                cLient.setPhoneLines(this.phoneLineMySQLDao.getByPerson(cLient));
                clientList.add(cLient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }
}
