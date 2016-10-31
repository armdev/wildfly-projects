package com.project.web.rest;


import com.project.web.model.UserModel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

@ManagedBean(eager = true, name = "userRESTClient")
@ApplicationScoped
public class UserRESTClient implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String REST_PATH = "http://localhost:11000/";
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();  

    public UserRESTClient() 
    {

    }

    @PostConstruct
    public void init() {
        System.out.println("userRESTClient init");
    }

    public UserModel userRegistration(UserModel model) {         
        try {
            HttpPost request = new HttpPost(REST_PATH + "auth/user/register");
            JSONObject json = new JSONObject();
            json.put("firstname", model.getFirstname());
            json.put("lastname", model.getLastname());
            json.put("email", model.getEmail());
            json.put("password", model.getPassword());
            json.put("username", model.getUsername());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) HTTP_CLIENT.execute(request);
            HttpEntity entity = response.getEntity();            
            ObjectMapper mapper = new ObjectMapper();
            model = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException ex) {
        } 
        return model;
    }

    public UserModel getUserById(String userId) {
        UserModel userModel = null;
        try {            
            HttpGet request = new HttpGet(REST_PATH + "auth/user/find/" + userId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = HTTP_CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException e) {
        }
        return userModel;

    }

    public UserModel getUserByEmail(String email) {
        UserModel userModel = null;
        try {            
            HttpGet request = new HttpGet(REST_PATH + "auth/user/find/email/" + email);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = HTTP_CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException e) {
        }
        return userModel;
    }

    public UserModel getUserByUsername(String username) {
        UserModel userModel = null;
        try {
            
            HttpGet request = new HttpGet(REST_PATH + "auth/user/find/username/" + username);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = HTTP_CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);

        } catch (IOException | ParseException e) {

        }
        return userModel;

    }

    public UserModel userLogin(UserModel model) {     
        UserModel userModel = null;
        try {
            HttpPost request = new HttpPost(REST_PATH + "auth/user/login");
            JSONObject json = new JSONObject();
            json.put("password", model.getPassword());
            json.put("email", model.getEmail());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) HTTP_CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);           
        } catch (IOException | ParseException ex) {
        } 
        return userModel;
    }
    
    @PreDestroy
    public void destroy(){
        HTTP_CLIENT.getConnectionManager().shutdown();
     }
        
    

}
