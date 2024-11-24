package br.com.gabriel.todolist.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gabriel.todolist.model.User;
import br.com.gabriel.todolist.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuthTask extends OncePerRequestFilter {

    @Autowired
    private IUserRepository repository;

    private String[] authCredentials(String header) {
        String authEncode = header.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncode);

        String authString = new String(authDecode);

        return authString.split(":");
    }

    private boolean authorizeUser(String passwordRequest, String passwordUser) {
        boolean result = false;

        result = BCrypt.verifyer().verify(passwordRequest.toCharArray(), passwordUser).verified;

        return result;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String servletPath = request.getServletPath();

       if(servletPath.equals("/tasks/")) {
           String authorization = request.getHeader("Authorization");

           String[] credentials = authCredentials(authorization);

           String userName = credentials[0];
           String userPassword = credentials[1];

           User user = this.repository.findByUserName(userName);

           if(user == null) {
               response.sendError(401);
           } else {

               boolean passwordVerify = authorizeUser(userPassword, user.getPassword());

               if(passwordVerify) {
                   request.setAttribute("idUser", user.getId());
                   filterChain.doFilter(request, response);
               } else {
                   response.sendError(401);
               }
           }
       } else {

           filterChain.doFilter(request, response);
       }
    }
}

