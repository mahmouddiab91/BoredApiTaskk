package APIs;

import ApiBase.RequestApi;
import URLs.URLs;
import io.restassured.response.Response;

public class Activity extends RequestApi {
    public Response Actvty() {

        URLs getBaseUrl = new URLs();
        String baseUrl = getBaseUrl.getURL();
        String endPoint = "api/activity";
        String url = baseUrl + endPoint;
        setRequestURL(url);

        return getResponse("GET");
    }
}
