package com.utnphones.utnPhones.session;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SessionManager {

    Map<String, Session> sessionMap = new Hashtable<>();

    int sessionExpiration = 300000;

    public String createSession(Person person) {
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, person, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token) throws UserNotLoggedException {
        Session session = sessionMap.get(token);
        if (session!=null) {
            session.setLastAction(new Date(System.currentTimeMillis()));
        }
        return session;
    }

    public void removeSession(String token) {
        sessionMap.remove(token);
    }

    public void expireSessions() {
        for (String k : sessionMap.keySet()) {
            Session v = sessionMap.get(k);
            if (v.getLastAction().getTime() < System.currentTimeMillis() + (sessionExpiration*1000)) {
                System.out.println("Expiring session " + k);
                sessionMap.remove(k);
            }
        }
    }

  /*  public Person getCurrentUser(String token) throws UserNotLoggedException {
        return getSession(token).getLoggedUser();
    }*/

    public Person getCurrentUser(String token) throws UserNotLoggedException {
        return Optional.ofNullable(getSession(token).getLoggedUser()).orElseThrow(UserNotLoggedException::new);
    }
}
