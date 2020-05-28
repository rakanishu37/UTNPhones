package com.utnphones.utnPhones.session;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Employee;
import com.utnphones.utnPhones.domain.Person;


import java.util.Date;

public class Session {

    String token;
    Person loggedUser;
    Date lastAction;

    public Session(String token, Person loggedUser, Date lastAction) {
        this.token = token;
        this.loggedUser = loggedUser;
        this.lastAction = lastAction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Client getLoggedUser() {
        return (Client)loggedUser;
    }

    public Employee getLoggedEmployee() {
        return (Employee) loggedUser;
    }

    public void setLoggedUser(Client loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}
