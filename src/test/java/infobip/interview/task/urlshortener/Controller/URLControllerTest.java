package infobip.interview.task.urlshortener.Controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

import infobip.interview.task.urlshortener.TestDataGenerator;
import org.json.simple.JSONObject;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class URLControllerTest {

    @LocalServerPort
    private int port;
    private TestDataGenerator testDataGenerator;

    @Before
    public void setUp() {
        RestAssured.port = port;
        testDataGenerator = new TestDataGenerator();
    }


    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyDefaultRedirectTypeURLRegistration() {

        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "https://www.url1.com");

        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("shortUrl", equalTo("http://localhost:8080/P7Cu")).
                statusCode(201);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifySuccessfulURLRegistration() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "http://www.url2.com");
        requestParams.put("redirectType", "301");

        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("shortUrl", equalTo("http://localhost:8080/P7Cu")).
                statusCode(201);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulURLRegistration_invalidURLValueFound() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "www.google");
        requestParams.put("redirectType", "301");
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("'Error message'", equalTo("Request data not valid")).
                statusCode(400);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulURLRegistration_noURLFound() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");
        JSONObject requestParams = new JSONObject();
        requestParams.put("redirectType", "301");
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("'Error message'", equalTo("Request data not valid")).
                statusCode(400);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnAuthorizedURLRegistration_invalidCredentials() {
        String basicAuth = testDataGenerator.encodeAccountCredentials("user1", "password1");

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "https://www.url3.com");
        requestParams.put("redirectType", "301");
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("'Error message'", equalTo("Invalid account credentials")).
                statusCode(401);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnAuthorizedURLRegistration_noHeaderFound() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "https://www.url3.com");
        requestParams.put("redirectType", "301");
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                statusCode(404);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnAuthorizedURLRegistration_invalidRedirectTypeFound() {
        String basicAuth = testDataGenerator.encodeAccountCredentials("user1", "password1");

        JSONObject requestParams = new JSONObject();
        requestParams.put("url", "https://www.url3.com");
        requestParams.put("redirectType", "307");
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                body(requestParams.toJSONString()).
                when().
                post("/register").
                then().
                body("'Error message'", equalTo("Request data not valid")).
                statusCode(400);
    }
}
