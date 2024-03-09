package ApiBase;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class RequestApi {

    public static RequestSpecification request;
    private static Response response;
    private static String RequestUrl;
    private static File multiPartRequest ;
    private static Map<String, String> headers ;
    private static Map<String, String> formParams;
    private static Map<String, String> queryParams;
    private static Map<String, Object> body ;
    private static JSONObject bodyJson ;
    private static ContentType contentType;

    // you need to call it first on your class to initialize request data
    public static void setRequestURL(String url){
        RequestUrl = url;
        headers = new HashMap<>();
        formParams = new HashMap<>();
        queryParams = new HashMap<>();
        body = new HashMap<>();
        contentType = null;
        bodyJson = new JSONObject();
    }

    //call it after set all the requests to get the response body
    public static Response getResponse(String requestType) {

        request = given();
//        request = given().log().all();

        if (contentType != null) {request.contentType(contentType);}
        if (headers != null) {request.headers(headers);}
        if (formParams != null) {request.formParams(formParams);}
        if (queryParams != null) {request.queryParams(queryParams);}
        if (!body.isEmpty()) {request.body(body.toString());}
        if (!bodyJson.isEmpty()) {request.body(bodyJson.toString());}
        if (multiPartRequest != null) {request.multiPart("file", multiPartRequest);}

        sendRequest(RequestType.valueOf(requestType));

        return response;
    }

    //send the request to the api
    private static void sendRequest(RequestType requestType) {

        switch (requestType) {

            case POST -> response = request.config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT))).when().post(RequestUrl).andReturn();
            case PATCH -> response = request.when().patch(RequestUrl).andReturn();
            case PUT -> response = request.when().put(RequestUrl).andReturn();
            case GET -> response = request.when().get(RequestUrl).andReturn();
            case DELETE -> response = request.when().delete(RequestUrl).andReturn();
            default -> {}
        }
    }

    private enum RequestType{
        POST, GET, PUT, DELETE, PATCH
    }

    //Set the get params
    public static void setQueryParams(String key, String value) {queryParams.put(key, value);}

    //set the form data of the body
    public void setFormParams(String key, String value) {formParams.put(key, value);}

    //set the header of the request {authorization or content type}
    public static void setHeaders(String key, String value) {headers.put(key, value);}

    // set the body request as json
    public static void setBody(String key, Object value) {body.put(key, value);}

    public static void setBodyJson(String key, String value) {bodyJson.put(key, value);}
    // set the file in the form param request
    public static void setMultiPartRequest(File file) {multiPartRequest = file;}

    //set content type
    public static void setContentType(ContentType value) {contentType = value;}

}
