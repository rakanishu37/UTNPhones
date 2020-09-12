package com.utnphones.utnPhones.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.utnphones.utnPhones.utils.Constants.USER_TYPE_EMPLOYEE;

@Service
public class BackOfficeSessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);

        if (null != session){
            if(USER_TYPE_EMPLOYEE.equals(session.getLoggedUser().getUserType().getName())) {
                filterChain.doFilter(request, response);
            }
            else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}