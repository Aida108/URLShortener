package infobip.interview.task.urlshortener.Controller;

import com.jayway.restassured.RestAssured;

import com.jayway.restassured.http.ContentType;
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

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticControllerTest {
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
    public void verifySuccessfulURLStatisticReturn() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");
        testDataGenerator.registerURL(basicAuth, "301");

        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                when().
                get("/statistic/{AccountId}", "user1").
                then().
                body("'http://www.url1.com:301'", equalTo(0)).
                statusCode(200);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyInvalidRequestForURLStatisticReturn_accountIdInUrlAndAuthHeaderDontMatch() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");

        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                when().
                get("/statistic/{AccountId}", "user2").
                then().
                statusCode(403);
    }

    @Test
    public void verifyUnAuthorizedURLStatisticReturn_invalidCredentials() {
        String basicAuth = testDataGenerator.encodeAccountCredentials("user1", "password1");

        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                header("Authorization", basicAuth).
                when().
                get("/statistic/{AccountId}", "user1").
                then().
                body("'Error message'", equalTo("Invalid account credentials")).
                statusCode(401);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnAuthorizedURLStatisticReturn_noHeader() {
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                when().
                get("/statistic/{AccountId}", "user1").
                then().
                statusCode(404);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulURLStatisticReturn_requestParamAccountIdAndAuthAccountId_DontMatch() {
        String basicAuth = testDataGenerator.openAccountAndGetEncodedCredentials("user1");

        given().
                header("Content-Type", "application/json").
                header("Authorization", basicAuth).
                when().
                get("/statistic/{AccountId}", "user2").
                then().
                statusCode(403);
    }

}
