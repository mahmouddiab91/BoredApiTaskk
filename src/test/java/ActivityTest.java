import APIs.Activity;
import io.restassured.response.Response;
import org.testng.annotations.Test;


public class ActivityTest {
    @Test
    public static void ActivityIdeaHappyScenario() {

        Activity act = new Activity();
        Response rsp = act.Actvty();
        rsp.body().prettyPrint();
        rsp.then().statusCode(200);
    }
}

