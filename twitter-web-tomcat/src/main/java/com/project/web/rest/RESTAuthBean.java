package com.project.web.rest;


import com.project.web.model.UserModel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

@ManagedBean(eager = true, name = "restAuthBean")
@ApplicationScoped
public class RESTAuthBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public RESTAuthBean() {

    }

    @PostConstruct
    public void init() {
        System.out.println("RESTAuthBean called");
    }

    public static void main(String args[]) {
        RESTAuthBean obj = new RESTAuthBean();
        UserModel model = new UserModel();
        model.setPassword("aaaaaa");
        model.setUsername("cdawefrkkyyegelos");
        model.setEmail("carlodswefgeyyrk@mail.ru");
        model.setFirstname("Hdafrloks");
        model.setLastname("HarlfweoskLastname");
        UserModel userId = obj.saveUser(model);
        System.out.println("userId.getUserId() " + userId.getUserId());
        UserModel newModel = obj.getUserById(userId.getUserId());
        //  System.out.println("newModel " + newModel.toString());

        UserModel newModel1 = obj.getUserByEmail(newModel.getEmail());
        obj.userLogin(newModel1);

    }

    public UserModel saveUser(UserModel model) {
        HttpClient httpClient = new DefaultHttpClient();
        //  String userId = null;
        try {
            HttpPost request = new HttpPost("http://localhost:9900/auth/user/register");
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
            HttpResponse response = (HttpResponse) httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            // userId = (EntityUtils.toString(entity));

            ObjectMapper mapper = new ObjectMapper();
            model = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException ex) {

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return model;
    }

    public UserModel getUserById(String userId) {
        UserModel userModel = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9900/auth/user/find/" + userId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = client.execute(request);
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
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9900/auth/user/find/email/" + email);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = client.execute(request);
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
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9900/auth/user/find/username/" + username);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = client.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);

        } catch (IOException | ParseException e) {

        }
        return userModel;

    }

    public UserModel userLogin(UserModel model) {
        HttpClient httpClient = new DefaultHttpClient();
        UserModel userModel = null;
        try {
            HttpPost request = new HttpPost("http://localhost:9900/auth/user/login");
            JSONObject json = new JSONObject();
            json.put("password", model.getPassword());
            json.put("email", model.getEmail());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);

            System.out.println("userModel after login " + userModel.toString());

        } catch (IOException | ParseException ex) {

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return userModel;
    }

}
