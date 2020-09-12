package com.utnphones.utnPhones.session;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

@Component
public class SessionManager {

    Map<String, Session> sessionMap = new Hashtable<>();

    long sessionExpiration = 30000000000000000L;

    public String createSession(Person person) {
        //TODO dejar como antes
        //String token = UUID.randomUUID().toString();
        String token = "token";
        sessionMap.put(token, new Session(token, person, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token) {
        if(token == null){
            return null;
        }else {
            Session session = sessionMap.get(token);

            if (session != null) {
                session.setLastAction(new Date(System.currentTimeMillis()));
            }
            return session;
        }
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

    public Person getCurrentUser(String token) throws UserNotLoggedException{
        return Optional.ofNullable(getSession(token))
                .map(Session::getLoggedUser)
                .orElseThrow(UserNotLoggedException::new);
    }
}
