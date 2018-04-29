package infobip.interview.task.urlshortener;

import com.jayway.restassured.http.ContentType;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;

import static com.jayway.restassured.RestAssured.given;


public class TestDataGenerator {

    public String openAccountAndGetEncodedCredentials(String accountId) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("AccountId", accountId);

        String password =  given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/account").
                then().
                extract().
                path("password");

        return encodeAccountCredentials(accountId, password);
    }

    public String encodeAccountCredentials(String accountId, String password){
        byte[] credentialsInBytes = (accountId +":"+ password).getBytes();

        return "Basic " + Base64.encodeBase64String(credentialsInBytes);
    }

    public String registerURL(String basicAuth, String redirectType){
        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "http://www.url1.com");
        requestParams.put("redirectType", redirectType);

         return given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                extract().
                path("shortUrl");
    }


}
