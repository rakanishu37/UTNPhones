package com.resttest.demo.service.integration;

import com.resttest.demo.models.Call;
import com.resttest.demo.models.Login;
import com.resttest.demo.models.Telefonica;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class IntegrationComponent {

    private RestTemplate rest;
    private static String url =
            "http://localhost:8080/backoffice/calls?from=1&quantity=50&dateFrom=2000-01-01&dateTo=2020-06-01";
    private static String urlLogin = "http://localhost:8080/login/";
    HttpHeaders headers = new HttpHeaders();
    @PostConstruct
    private void init() {
        rest = new RestTemplateBuilder()
                .build();
    }

    public List<String> login() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "adrew_ryan_empl");
        personJsonObject.put("password", "123456");
        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        System.out.println("llego");
        return rest.postForEntity(urlLogin,request, String.class).getHeaders().get("Authorization");
    }

    public List<Call> getCallsFromApi() {
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add(HttpHeaders.AUTHORIZATION, "token");

        return rest.getForObject(url, Telefonica.class).getCalls();
    }

    public void getTest(){
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add(HttpHeaders.AUTHORIZATION, "token");
        // System.out.println(rest.getForObject(url, String.class));

        System.out.println(rest.getForObject("http://localhost:8080/backoffice/test", String.class));
    }

}
