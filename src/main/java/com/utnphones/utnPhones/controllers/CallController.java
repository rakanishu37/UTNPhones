package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calls")
public class CallController {
    private CallService callService;
    private SessionManager sessionManager;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/")
    public List<Call> getAll(){
        return this.callService.getAll();
    }

    @PostMapping("/")
    public Call create(@RequestBody Call call){
        return this.callService.create(call);
    }

    /*Consulta de llamadas del usuario logueado por rango de fechas
    token para tener la sesion que contiene adentro el objeto Person
    ResponseEntity<Map<String,List<Call>>(@RequestBody DateCallRequestDto datesDto, el token)
     */
    /*@GetMapping("")
    String = numero de la linea
    public ResponseEntity<Map<String,List<Call>> getMessages(@RequestHeader("Authorization") String sessionToken) {
        Client currentUser = sessionManager.getCurrentUser(sessionToken);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Message> messages = messageController.getMessages(currentUser.getUserId());
        // solo las REALIZADAS 
        //si todos estan las lineas no tienen ni una llamada REALIZADA tirar un No Content
        return (messages.size() > 0) ? ResponseEntity.ok(messages) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
