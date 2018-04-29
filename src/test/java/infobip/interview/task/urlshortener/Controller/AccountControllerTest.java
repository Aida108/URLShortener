package infobip.interview.task.urlshortener.Controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountControllerTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifySuccessfulAccountRegistration() {
        given().
                header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountId\" : \"user1\"}").
                when().
                post("/account").
                then().
                body("success", equalTo(true)).
                body("description", equalTo("Your Account is opened.")).
                statusCode(201);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulAccountRegistration_accountIdAlreadyTaken() {
        given().header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountId\" : \"user1\"}").
                when().
                post("/account");

        given().header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountId\" : \"user1\"}").
                when().
                post("/account").
                then().
                body("success", equalTo(false)).
                statusCode(409);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulAccountRegistration_noAccountIdFound() {
        given().header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountI\" : \"user1\"}").
                when().
                post("/account").
                then().
                body("'Error message'", equalTo("AccountId is required")).
                statusCode(400);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulAccountRegistration_emptyAccountIdFound() {
        given().header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountId\" : \"\"}").
                when().
                post("/account").
                then().
                body("'Error message'", equalTo("AccountId is required")).
                statusCode(400);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void verifyUnSuccessfulAccountRegistration_invalidAccountIdValueFound() {
        given().header("Content-Type", "application/json").
                accept(ContentType.JSON).
                body("{ \"AccountId\" : \":user1)\"}").
                when().
                post("/account").
                then().
                body("'Error message'", equalTo("AccountId value is invalid")).
                statusCode(400);
    }
}
