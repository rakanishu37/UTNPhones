package com.resttest.demo.service;

import com.resttest.demo.models.Call;
import com.resttest.demo.service.integration.IntegrationComponent;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class IntegrationService {
    @Autowired
    private IntegrationComponent integrationComponent;

    public List<Call> getCalls() {
        return integrationComponent.getCallsFromApi();
    }
    public List<String> login() throws JSONException {
        System.out.println(this.integrationComponent.login());
        return null;
    }

    public void test(){

            this.integrationComponent.getTest();

    }
}
